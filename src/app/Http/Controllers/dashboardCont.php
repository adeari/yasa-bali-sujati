<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;

use App\models\Customers;
use App\models\Pegawai;
use App\models\Joborder;

class dashboardCont extends Controller1 {
	public function index()
	{
		$customerCount = Customers::whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir')")->count();
		$customerCountLengkap = Customers::whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir')")->where("islengkap", "=", true)->count();
		$customerCountBelumLengkap = Customers::whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir')")
		->where("islengkap", "=", false)->count();

		$shipperCount = Customers::whereRaw("LOWER(jenis_customer) IN ('exportir', 'importir')")->count();
		$shipperCountLengkap = Customers::whereRaw("LOWER(jenis_customer) IN ('exportir', 'importir')")
		->where("islengkap", "=", true)->count();
		$shipperCountBelumLengkap = Customers::whereRaw("LOWER(jenis_customer) IN ('exportir', 'importir')")
		->where("islengkap", "=", false)->count();

		$pegawaiCount = Pegawai::count();
		$joborderCount = Joborder::count();
		$joborderKosongCount = Joborder::where('status', '=', 'Kosong')->count();
		$joborderSebagianCount = Joborder::where('status', '=', 'Sebagian')->count();
		$joborderLengkapCount = Joborder::where('status', '=', 'Lengkap')->count();

		return view('dashboard', array(

			'customerCount' => $customerCount,
			'customerCountLengkap' => $customerCountLengkap,
			'customerCountBelumLengkap' => $customerCountBelumLengkap,

			'shipperCount' => $shipperCount,
			'shipperCountLengkap' => $shipperCountLengkap,
			'shipperCountBelumLengkap' => $shipperCountBelumLengkap,

			'pegawaiCount' => $pegawaiCount,
			'joborderCount' => $joborderCount,
			'joborderKosongCount' => $joborderKosongCount,
			'joborderSebagianCount' => $joborderSebagianCount,
			'joborderLengkapCount' => $joborderLengkapCount,

		));
	}
}