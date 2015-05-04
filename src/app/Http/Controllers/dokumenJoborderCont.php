<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;

use App\models\Joborder;
use App\models\Customers;
use App\models\Filling;
use App\models\Pegawai;
use App\models\Validasi_rules;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class dokumenJoborderCont extends Controller1 {
	public function index()
	{
		$fillings = Filling::all();
		$kodelist = array();
		foreach ($fillings as $filling) {
			if (Joborder::where('kode','like',$filling->huruf.'%')->count() == 0) {
				$kodee = $filling->huruf;
				for ($i = 1; $i < $filling->digit; $i++) {
					$kodee .= '0';
				}
				$kodee .= '1';
				$aturanValidasi = '';
				$isTheFirst = true;
				foreach ($filling->validasi_rules as $validasi_here) {
					if ($isTheFirst) {
						$aturanValidasi .= $validasi_here->id;
						$isTheFirst = false;
					} else {
						$aturanValidasi .= ','. $validasi_here->id;
					}
				}				
				$dataarray = (object) array('id' => $filling->id, 'kode' => $kodee, 'warna' => $filling->warna, 'aturanValidasi' => $aturanValidasi);
				array_push($kodelist, $dataarray);
			}
		}
		$pegawais = Pegawai::orderBy('nama')->get();
		$joborders = Joborder::orderBy('kode')->orderBy('id','desc')->get();
		$customers = Customers::orderBy('nama_perusahaan')->get();
		$exportirs = Customers::whereRaw("LOWER(jenis_customer) = 'exportir'")->orderBy('nama_perusahaan')->get();
		$jenis_kegiatan = Joborder::select('jenis_kegiatan')->groupBy('jenis_kegiatan')->orderBy('jenis_kegiatan')->get();
		$validasi_rules = Validasi_rules::orderBy('urutan')->get();
		return view('dokumen-joborder', array(
			'kodelist' => $kodelist,
			'joborders' => $joborders,
			'customers' => $customers,
			'exportirs' => $exportirs,
			'jenis_kegiatan' => $jenis_kegiatan,
			'pegawais' => $pegawais,
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
			if (!empty(Input::get('id'))) {
				$filling = Filling::find(Input::get('id'));
			}

			$filling->warna = Input::get('warna');
			$filling->huruf = Input::get('huruf');
			$filling->digit = Input::get('digit');

			if (empty(Input::get('id'))) {
				$filling->save();
			} else {
				$filling->update();
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