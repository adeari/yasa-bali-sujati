<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\User;
use Auth;

use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Hash;

class loginCont extends Controller {
	public function index()
	{
		if (Auth::check()) {
			return redirect('dashboard');
		}

		
		$data['divisis'] = User::select('divisi')->groupBy('divisi')->orderBy('divisi')->get();

		if (!is_null(Input::get('nextt'))) {
			$data['nextt'] = Input::get('nextt');
		}

		return view('login', $data);
	}

	public function postlogin() {
		if (Auth::attempt(array('name' => Input::get('usernamee'), 'password' => Input::get('passwordd'), 'divisi' => Input::get('divisi'))))
		//if (Auth::attempt(array('name' => 'ade', 'password' => '1234', 'divisi' => 'Admin')))
		{
			if (Input::get('divisi') == 'Admin') {
				Auth::user()->divisi = 'admin';
			}

			$userUp = User::find(Auth::user()->id);
			$userUp->last_login = date('Y-m-d H:i:s');
			$userUp->update();

		    $result = array('success' => 1, 'msg' => 'masuk');
		} else {
			$result = array('success' => 0, 'msg' => 'User name atau Password salah');
		}
		return response()->json($result);
	}
}