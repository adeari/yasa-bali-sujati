<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;

class dashboardCont extends Controller1 {
	public function index()
	{
		return view('dashboard');
	}
}