<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\models\Filling;
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
			if (count(Input::get('validasi_rules'))>0) {
				$datasyncin = Input::get('validasi_rules');
				$filling->validasi_rules()->sync($datasyncin);
				foreach ($datasyncin as $datasync1) {
					$this->cekIsAturanCanbeDeleted($datasync1);
				}
			}
			if (!is_null($datasync[0])) {
				foreach ($datasync as $datasync1) {
					$this->cekIsAturanCanbeDeleted($datasync1);
				}
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-filling');
	}

	public function del($id) {
		$success = 1;
		$msg = '';
		Filling::find($id)->delete();
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}
}