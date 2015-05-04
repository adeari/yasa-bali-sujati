<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\models\Validasi_rules;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class masterValidasiCont extends Controller1 {
	public function index()
	{
		$validasi_rules = Validasi_rules::orderBy('urutan')->get();
		return view('master-validasi', array(
			'validasi_rules' => $validasi_rules,
			));
	}
	public function add()
	{
		$validator = Validator::make(
			 [
		        'urutan' => Input::get('urutan'),
		        'aturan' => Input::get('aturan'),
		    ],
		    [
		        'urutan' => 'required',
		        'aturan' => 'required',
		    ]
		);

		$msg = "Aturan validasi tersimpan";
		if ($validator->fails()) {
			$msg = $validator->messages();
		} else {
			$validasi_rules = new Validasi_rules();
			if (!empty(Input::get('id'))) {
				$validasi_rules = Validasi_rules::find(Input::get('id'));
			}

			$validasi_rules->urutan = Input::get('urutan');
			$validasi_rules->aturan = Input::get('aturan');

			if (empty(Input::get('id'))) {
				$validasi_rules->save();
			} else {
				$validasi_rules->update();
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-validasi');
	}

	public function del($id) {
		$success = 0;
		$msg = '';
		if (Validasi_rules::find($id)->where('isdeleted','=',false)->count() > 0) {
			$msg = 'Tidak bida dihapus karena data ini di pakai di data filling atau job order';
		} else {
			Validasi_rules::find($id)->where('isdeleted','=',true)->delete();
			$success = 1;
		}
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}
}