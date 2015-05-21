<?php namespace App\Http\Controllers;

use App\models\Joborder;

class testtCont extends Controller {

	public function index()
	{
		$kodee = "PO_";
		$kodee11 = str_replace("_", "\_", $kodee);
		dd(Joborder::select('kode')->whereRaw("kode like '$kodee11%'")->orderBy('kode', 'desc')->first());
	}
}