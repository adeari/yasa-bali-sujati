<?php namespace App\Http\Controllers;

use App\models\Filling_validasi_rules;
use App\models\Validasi_rules;


abstract class Controller1 extends Controller {
	public function __construct()
	{
		$this->middleware('auth');
	}
	function cekIsAturanCanbeDeleted($validasi_id) {
		$candeleted = true;
		if ($candeleted && Filling_validasi_rules::where('validasi_rules_id','=',$validasi_id)->count()>0 ) {
			$candeleted = false;
		}
		$validasi_rules = Validasi_rules::find($validasi_id);
		$validasi_rules->isdeleted = $candeleted;
		$validasi_rules->update();

	}
}
