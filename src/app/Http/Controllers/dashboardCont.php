<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;

use App\models\Customers;
use App\models\Pegawai;
use App\models\Joborder;

class dashboardCont extends Controller1 {
	public function index()
	{
		$customerCount = Customers::count();
		$pegawaiCount = Pegawai::count();
		$joborderCount = Joborder::count();
		$joborderKosongCount = Joborder::where('status', '=', 'Kosong')->count();
		$joborderSebagianCount = Joborder::where('status', '=', 'Sebagian')->count();
		$joborderLengkapCount = Joborder::where('status', '=', 'Lengkap')->count();

		return view('dashboard', array(

			'customerCount' => $customerCount,
			'pegawaiCount' => $pegawaiCount,
			'joborderCount' => $joborderCount,
			'joborderKosongCount' => $joborderKosongCount,
			'joborderSebagianCount' => $joborderSebagianCount,
			'joborderLengkapCount' => $joborderLengkapCount,

		));
	}
}