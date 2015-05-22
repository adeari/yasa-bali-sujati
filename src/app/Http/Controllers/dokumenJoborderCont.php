<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;

use App\models\Joborder;
use App\models\Customers;
use App\models\Filling;
use App\models\Pegawai;
use App\models\Validasi_rules;
use App\models\Joborder_validasi_rules;
use App\models\Joborder_pegawai;
use Auth;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class dokumenJoborderCont extends Controller1 {

	function getNewKode($filling) {
		$kodee = $filling->huruf;
		$kodee11 = str_replace("_", "\_", $kodee);

		if (Joborder::whereRaw("kode like '$kodee11%'")->count() == 0) {

			for ($i = 1; $i < $filling->digit; $i++) {
				$kodee .= '0';
			}
			$kodee .= '1';
		} else {
			$joborderid = Joborder::select('kode')->whereRaw("kode like '$kodee11%'")->orderBy('kode', 'desc')->first()->kode;
			$numberCode = floatval(str_replace($kodee, '', $joborderid)) + 1;
			$numberCode = ''.$numberCode;
			for ($i = strlen($numberCode); $i < $filling->digit; $i++) {
				$numberCode = '0'.$numberCode;
			}
			$kodee =  $kodee.$numberCode;
		}
		return $kodee;
	}

	public function index($status = null)
	{
		$pegawais = Pegawai::orderBy('nama')->get();
		$joborders = Joborder::with('customer1','exportir1','validasi_rules','pegawai','filling1');
		if (!is_null($status)) {
			$joborders = $joborders->where('status', '=', ucwords($status));
		}
		$joborders = $joborders->orderBy('kode')->orderBy('id','desc')->get();
		$customers = Customers::orderBy('nama_perusahaan')->get();
		$exportirs = Customers::whereRaw("LOWER(jenis_customer) in ('exportir', 'importir')")->orderBy('nama_perusahaan')->get();
		$jenis_kegiatan = Joborder::select('jenis_kegiatan')->groupBy('jenis_kegiatan')->orderBy('jenis_kegiatan')->get();
		$partais = Joborder::select('partai')->whereRaw("LOWER(partai) NOT IN ('20 feet', '40 feet', 'lcl')")
		->groupBy('partai')->orderBy('partai')->get();
		$validasi_rules = Validasi_rules::orderBy('urutan')->get();

		$jenis_customer = Customers::select('jenis_customer')
		->whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir', 'rekanan')")
		->groupBy('jenis_customer')
		->get();

		return view('dokumen-joborder', array(
			'joborders' => $joborders,
			'customers' => $customers,
			'exportirs' => $exportirs,
			'jenis_kegiatan' => $jenis_kegiatan,
			'pegawais' => $pegawais,
			'validasi_rules' => $validasi_rules,
			'statusSelected' => $status,
			'jenis_customer' => $jenis_customer,
			'partais' => $partais,
		));
	}
	public function add()
	{
		$datasyncin = Input::get('validasi_rules');
		$countDataSyncin = count($datasyncin);
		$datasync = array();

		$inputPegawai 		= Input::get('pegawaiinputval');
		$countInputPegawai 	= count($inputPegawai);
		$dataInputPegawai 	= array();

		$jenisKegiatan = Input::get('jenis_kegiatan');
		if (empty($jenisKegiatan)) {
			$jenisKegiatan = Input::get('jenis_kegiatan1');
		}
		$pegawaiSelected = '';
		$validator = Validator::make(
			 [
		        'kode' => Input::get('kode'),
		        'jenis_kegiatan' => $jenisKegiatan,
		        'tgl_pelaksanaan' => Input::get('tgl_pelaksanaan'),
		    ],
		    [
		        'kode' => 'required',
		        'jenis_kegiatan' => 'required',
		        'tgl_pelaksanaan' => 'required|date_format:d/m/Y H:i|regex:/[0-9]{2}\/[0-9]{2}\/[0-9]{4} [0-9]{2}:[0-9]{2}/',
		    ]
		);

		$msg = "Job order tersimpan";
		$exportirBefore = null;
		$customerBefore = null;
		if ($validator->fails()) {
			$msg = $validator->messages();
			if ($msg->has('tgl_pelaksanaan')) {
				$msg .= "<br>Tanggal tidak sesuai";
			}
		} else {
			$jobOrder = new Joborder();
			if (empty(Input::get('id'))) {
				$jobOrder->status = 'Kosong';
			} else {
				$datasync[0] = null;
				$jobOrder = Joborder::find(Input::get('id'));
				$i = 0;
				foreach ($jobOrder->validasi_rules as $validasi_rule) {
					$datasync[$i] = $validasi_rule->id;
					$i++;
				}

				$dataInputPegawai[0] = null;
				$i=0;
				foreach ($jobOrder->pegawai as $pegawai) {
					$dataInputPegawai[$i] = $pegawai->id;
					$i++;
				}

				$customerBefore = $jobOrder->customer;
				if (!is_null($jobOrder->exportir)) {
					if (!empty($jobOrder->exportir)) {
						$exportirBefore = $jobOrder->exportir;
					}
				}
				
			}


			
			$jobOrder->customer = Input::get('customer');
			if (!empty(Input::get('customer1'))) {
				$customerAdd = new Customers();
				$customerAdd->nama_perusahaan = Input::get('customer1');
				$customerAdd->jenis_customer = Input::get('jenis_customer');
				$customerAdd->save();
				$jobOrder->customer = $customerAdd->id;
			}
			if (!empty(Input::get('exportir'))) {
				$jobOrder->exportir = Input::get('exportir');
				if (!empty(Input::get('shipper1'))) {
					$customerAdd = new Customers();
					$customerAdd->nama_perusahaan = Input::get('shipper1');
					$customerAdd->jenis_customer = Input::get('jenis_shipper');
					$customerAdd->save();
					$jobOrder->exportir = $customerAdd->id;
				}
			}
			$jobOrder->jenis_kegiatan = $jenisKegiatan;
			$jobOrder->tgl_pelaksanaan = $this->setDateTime(Input::get('tgl_pelaksanaan'));
			$jobOrder->catatan = Input::get('catatan');
			$updated_byBefore = $jobOrder->updated_by;
			$jobOrder->updated_by = Auth::user()->id;
			$fillingBefore = $jobOrder->filling;
			$jobOrder->filling = Input::get('kode');
			$jobOrder->t4_pelaksanaan = Input::get('t4_pelaksanaan');
			$jobOrder->komoditi = Input::get('komoditi');
			if (empty(Input::get('partai1'))) {
				$jobOrder->partai = Input::get('partai');
			} else {
				$jobOrder->partai = Input::get('partai1');
			}
			$jobOrder->destinasi = Input::get('destinasi');


			if ($countDataSyncin > 0) {
				$filling = Filling::find($jobOrder->filling);
				$arrayValidasi_rules = array();

				if (!is_null($filling)) {
					$countFilling = 0;
					$countExist = 0;
					foreach ($filling->validasi_rules as $validasi_rules) {
						if (in_array($validasi_rules->id, $datasyncin)) {
							$countExist++;
						}
						$countFilling++;
					}

					switch ($countExist) {
						case $countFilling:
							$jobOrder->status = 'Lengkap';
							break;
						case 0:
							$jobOrder->status = 'Kosong';
							break;
						
						default:
							$jobOrder->status = 'Sebagian';
							break;
					}
				} else {
					$jobOrder->status = 'Lengkap';
				}
			} else {
				$jobOrder->status = 'Kosong';
			}

			if (empty(Input::get('id'))) {
				$jobOrder->created_by = Auth::user()->id;
				$jobOrder->kode = $this->getNewKode(Filling::find($jobOrder->filling));
				$jobOrder->save();
				$this->cekIsUSerCanbeDeleted($jobOrder->created_by);
			} else {
				$jobOrder->update();
				$this->checkisCustomerCandeleted($customerBefore);
				$this->checkisCustomerCandeleted($exportirBefore);
				$this->cekIsUSerCanbeDeleted($updated_byBefore);
				$this->cekisFillingCanbeDeleted($fillingBefore);
			}

			if ($countInputPegawai > 0) {
				$jobOrder->pegawai()->sync($inputPegawai);
				foreach ($inputPegawai as $inputPegawai1) {
					$this->cekIsPegawaiCanbeDeleted($inputPegawai1);
				}
			} else {
				$jobOrder->pegawai()->detach();
			}
			
			if (count(Input::get('pegawaiinputval'))>0) {
				$jobOrder->pegawai()->sync(Input::get('pegawaiinputval'));
			}


			if ($countDataSyncin > 0) {
				$jobOrder->validasi_rules()->sync($datasyncin);
				foreach ($datasyncin as $datasync1) {
					$this->cekIsAturanCanbeDeleted($datasync1);
				}
			} else {
				$jobOrder->validasi_rules()->detach();
			}
			
			if (count($datasync) > 0) {
				if (!is_null($datasync[0])) {
					foreach ($datasync as $datasync1) {
						$this->cekIsAturanCanbeDeleted($datasync1);
					}
				}
			}
			if (count($dataInputPegawai) > 0) {
				if (!is_null($dataInputPegawai[0])) {
					foreach ($dataInputPegawai as $dataInputPegawai1) {
						$this->cekIsPegawaiCanbeDeleted($dataInputPegawai1);
					}
				}
			}

			$this->checkisCustomerCandeleted($jobOrder->customer);
			$this->checkisCustomerCandeleted($jobOrder->exportir);

			$this->cekIsUSerCanbeDeleted($jobOrder->updated_by);

			$this->cekisFillingCanbeDeleted($jobOrder->filling);
		}
		Session::flash('msg', $msg);
		return redirect('dokumen-joborder');
	}

	public function del($id) {
		$success = 1;
		$msg = '';

		$Joborder_validasi_rulesData = Joborder_validasi_rules::where('joborder_id', '=', $id);
		$Joborder_validasi_ruless = $Joborder_validasi_rulesData->get();
		$Joborder_validasi_rulesData->delete();

		$Joborder_pegawaiData = Joborder_pegawai::where('joborder_id', '=', $id);
		$Joborder_pegawais = $Joborder_pegawaiData->get();
		$Joborder_pegawaiData->delete();

		$jobOrder = Joborder::find($id);
		$customerBefore = $jobOrder->customer;
		$exportirBefore = $jobOrder->exportir;
		$created_byBefore = $jobOrder->created_by;
		$updated_byBefore = $jobOrder->updated_by;
		$fillingBefore = $jobOrder->filling;
		$jobOrder->delete();

		foreach ($Joborder_validasi_ruless as $Joborder_validasi_rules) {
			$this->cekIsAturanCanbeDeleted($Joborder_validasi_rules->validasi_rules_id);
		}

		foreach ($Joborder_pegawais as $Joborder_pegawai) {
			$this->cekIsPegawaiCanbeDeleted($Joborder_pegawai->pegawai_id);
		}

		$this->checkisCustomerCandeleted($customerBefore);
		$this->checkisCustomerCandeleted($exportirBefore);

		$this->cekIsUSerCanbeDeleted($created_byBefore);
		$this->cekIsUSerCanbeDeleted($updated_byBefore);

		$this->cekisFillingCanbeDeleted($fillingBefore);

		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}

	public function viewOptionCode() {
		$fillings = Filling::all();
		$kodelist = array();

		foreach ($fillings as $filling) {
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

			$dataarray = array('id' => $filling->id, 'kode' => $this->getNewKode($filling) , 'warna' => $filling->warna, 'aturanValidasi' => $aturanValidasi);
			array_push($kodelist, $dataarray);
		}
		return response()->json($kodelist);
	}

	public function viewKode($kode) {
		$joborder = Joborder::with('customer1', 'exportir1', 'validasi_rules', 'pegawai','filling1')
					->where('kode', '=', $kode)->first();

		$rulesnotValid = array();

			$rulesselected = array();
			$validasi_ruless = $joborder->validasi_rules()->orderBy('urutan')->get();

			foreach ($validasi_ruless as $validasi_rules) {
				array_push($rulesselected, $validasi_rules->id);
			}

			if (count($rulesselected) > 0) {
				$filling = Filling::find($joborder->filling);

				if (!is_null($filling)) {
					$validasi_defaults = $filling->validasi_rules()->orderBy('urutan')->get();
					foreach ($validasi_defaults as $validasi_default) {

						if (!in_array($validasi_default->id, $rulesselected)) {
							array_push($rulesnotValid, $validasi_default);
						}
					}
				}
				if (count($rulesnotValid) > 0) {
					$rulesnotValid = (object) $rulesnotValid;
				}
			}
		return view('dokumen-joborder-detail',array(
					'joborder' => $joborder,
					'validasi_ruless' => $validasi_ruless,
					'rulesnotValid' => $rulesnotValid,
				));
	}

	public function view1Customer() {
		if (!empty(Input::get('id'))) {
			$customer = Customers::find(Input::get('id'));
			$dataCustomer = array();
			$dataCustomer['alamat'] = $customer->alamat;
			$dataCustomer['id'] = $customer->id;
			$dataCustomer['id1'] = Input::get('id');
			return response()->json($dataCustomer);
		}
	}
}