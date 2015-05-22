<?php namespace App\Http\Controllers;

use App\models\Filling_validasi_rules;
use App\models\Filling;
use App\models\Joborder_validasi_rules;
use App\models\Joborder_pegawai;
use App\models\Validasi_rules;
use App\models\Joborder;
use App\models\Customers;
use App\models\Pegawai;
use App\User;


abstract class Controller1 extends Controller {
	public function __construct()
	{
		$this->middleware('auth');
	}
	
	function cekIsAturanCanbeDeleted($validasi_id) {
		$candeleted = true;

		if ($candeleted) {
			if (Filling_validasi_rules::where('validasi_rules_id','=',$validasi_id)->count() > 0) {
				$candeleted = false;
			}
		}

		if ($candeleted) {
			if (Joborder_validasi_rules::where('validasi_rules_id','=',$validasi_id)->count() > 0) {
				$candeleted = false;
			}
		}

		$validasi_rules = Validasi_rules::find($validasi_id);
		$validasi_rules->isdeleted = $candeleted;
		$validasi_rules->update();

	}

	function cekIsPegawaiCanbeDeleted($pegawai_id) {
		$candeleted = true;

		if ($candeleted) {
			if (Joborder_pegawai::where('pegawai_id', '=', $pegawai_id)->count() > 0) {
				$candeleted = false;
			}
		}

		$pegawai = Pegawai::find($pegawai_id);
		$pegawai->isdeleted = $candeleted;
		$pegawai->update();
	}

	function cekisFillingCanbeDeleted($filling_id) {
		$candeleted = true;
		if ($candeleted) {
			if (Joborder::where('filling', '=', $filling_id)->count() > 0) {
				$candeleted = false;
			}
		}

		$filling = Filling::find($filling_id);
		$filling->isdeleted = $candeleted;
		$filling->update();
	}

	function cekIsUSerCanbeDeleted($user_id) {
		$candeleted = true;

		if ($candeleted) {
			if (Joborder::whereRaw("created_by = $user_id OR updated_by = $user_id")->count() > 0) {
				$candeleted = false;
			}
		}

		$user = User::find($user_id);
		$user->isdeleted = $candeleted;
		$user->update();
	}

	function checkisCustomerCandeleted($id) {
		if (!is_null($id)) {
			if (!empty($id)) {
				$candeleted = true;

				if ($candeleted) {
					if (Joborder::whereRaw("customer = $id OR exportir = $id")->count() > 0) {
						$candeleted = false;
					}
				}

				$customer = Customers::find($id);
				$customer->isdeleted = $candeleted;
				$customer->update();
			}}
	}

	function setDateTime($str) {
		$tgl = '';
		if (!empty($str)) {
			$tgl = substr($str, 6,4).'-'.substr($str, 3,2).'-'.substr($str, 0,2).' '.substr($str, 11);
		}
		return $tgl;
	}
	
}
