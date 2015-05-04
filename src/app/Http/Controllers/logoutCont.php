<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Auth;

class logoutCont extends Controller1 {
	public function logout()
	{
		Auth::logout();
		return redirect('/');
	}	
}