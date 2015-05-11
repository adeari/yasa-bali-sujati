<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\models\Filling;
use App\models\Filling_validasi_rules;
use App\models\Validasi_rules;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class masterFillingCont extends Controller1 {
	public function index()
	{
		$fillings = Filling::orderBy('huruf')->get();
		$validasi_rules = Validasi_rules::orderBy('urutan')->get();
		return view('master-filling', array(
			'fillings' => $fillings,
			'validasi_rules' => $validasi_rules,
			));
	}
	public function add()
	{
		$datasync = array();
		$validator = Validator::make(
			 [
		        'warna' => Input::get('warna'),
		        'huruf' => Input::get('huruf'),
		        'digit' => Input::get('digit'),
		    ],
		    [
		        'warna' => 'required',
		        'huruf' => 'required',
		        'digit' => 'required',
		    ]
		);

		$msg = "Filling tersimpan";
		if ($validator->fails()) {
			$msg = $validator->messages();
		} else {
			$filling = new Filling();
			$datasync[0] = null;
			if (!empty(Input::get('id'))) {
				$filling = Filling::find(Input::get('id'));
				$i = 0;
				foreach ($filling->validasi_rules as $validasii_rule) {
					$datasync[$i] = $validasii_rule->id;
					$i++;
				}
			}
			$filling->warna = Input::get('warna');
			$filling->huruf = Input::get('huruf');
			$filling->digit = Input::get('digit');


			if (empty(Input::get('id'))) {
				$filling->save();
			} else {
				$filling->update();
			}
			$datasyncin = Input::get('validasi_rules');
			if (count($datasyncin) > 0) {
				$filling->validasi_rules()->sync($datasyncin);
				foreach ($datasyncin as $datasync1) {
					$this->cekIsAturanCanbeDeleted($datasync1);
				}
			} else {
				$filling->validasi_rules()->detach();
			}
			if (count($datasync) > 0) {
				if (!is_null($datasync[0])) {
					foreach ($datasync as $datasync1) {
						$this->cekIsAturanCanbeDeleted($datasync1);
					}
				}
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-filling');
	}

	public function del($id) {
		$success = 0;
		$msg = '';
		$fillingData = Filling_validasi_rules::where('filling_id', '=', $id);
		$fillings = $fillingData->get();
		$fillingData->delete();

		if (Filling::where('isdeleted','=',false)->whereid($id)->count() > 0) {
			$msg = 'Tidak bida dihapus karena data ini di pakai di job order';
		} else {
			Filling::where('isdeleted','=',true)->whereid($id)->delete();
			$success = 1;
		}
		foreach ($fillings as $filling) {
			$this->cekIsAturanCanbeDeleted($filling->validasi_rules_id);
		}
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}
}