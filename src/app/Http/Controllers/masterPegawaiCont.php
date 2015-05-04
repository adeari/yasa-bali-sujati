<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\models\Pegawai;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class masterPegawaiCont extends Controller1 {
	public function index()
	{
		$divisis = Pegawai::select('divisi')->groupBy('divisi')->get();
		$pegawais = Pegawai::orderBy('nama')->get();
		return view('master-pegawai', array(
			'divisis' => $divisis,
			'pegawais' => $pegawais,
			));
	}
	public function add()
	{
		$divisiIn = '';
		if (!empty(Input::get('divisi'))) {
			$divisiIn = Input::get('divisi');
		} else if (!empty(Input::get('divisi1'))) {
			$divisiIn = Input::get('divisi1');
		}
		$validator = Validator::make(
			 [
		        'nama' => Input::get('nama'),
		        'alamat' => Input::get('alamat'),
		        'telepon' => Input::get('telepon'),
		        'divisi' => $divisiIn,
		    ],
		    [
		        'nama' => 'required',
		        'alamat' => 'required',
		        'telepon' => 'required',
		        'divisi' => 'required',
		    ]
		);

		$msg = "Pegawai tersimpan";
		if ($validator->fails()) {
			$msg = $validator->messages();
		} else {
			$pegawai = new Pegawai();
			if (!empty(Input::get('id'))) {
				$pegawai = Pegawai::find(Input::get('id'));
			}

			$pegawai->nama = Input::get('nama');
			$pegawai->alamat = Input::get('alamat');
			$pegawai->telepon = Input::get('telepon');
			$pegawai->divisi = $divisiIn;

			if (empty(Input::get('id'))) {
				$pegawai->save();
			} else {
				$pegawai->update();
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-pegawai');
	}

	public function del($id) {
		$success = 1;
		$msg = '';
		Pegawai::find($id)->delete();
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}
}