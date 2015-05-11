<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\User;
use Auth;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class masterUserCont extends Controller1 {
	public function index()
	{
		$divisis = User::select('divisi')->groupBy('divisi')->get();
		$users = User::orderBy('name')->get();
		return view('master-user', array(
			'divisis' => $divisis,
			'users' => $users,
			));
	}

	public function check() {
		$countt = 0;
		$msg = '';
		$success = 0;
		$result = array('success' => 1, 'msg' => '');
		if (empty(Input::get('id')) && !empty(Input::get('users'))) {
			$countt = User::where('name','=',Input::get('users'))->count();
		} else if (!empty(Input::get('id')) && !empty(Input::get('users'))) {
			$countt = User::where('name','=',Input::get('users'))->
			where('id','!=',Input::get('id'))->
			count();
		}
		if ($countt > 0) {
			$msg = 'This user already exist';
		} else {
			$success = 1;
		}
		
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}

	public function add()
	{
		$divisiIn = '';
		if (!empty(Input::get('divisi'))) {
			$divisiIn = Input::get('divisi');
		} else if (!empty(Input::get('divisi1'))) {
			$divisiIn = Input::get('divisi1');
		}
		$validator = Validator::make(
			 [
		        'name' => Input::get('users'),
		        'password' => Input::get('pass'),
		        'divisi' => $divisiIn,
		    ],
		    [
		        'name' => 'required',
		        'password' => 'required|min:4',
		        'divisi' => 'required',
		    ]
		);

		$msg = "User tersimpan";
		if ($validator->fails()) {
			$msg = $validator->messages();
		} else {
			if (empty(Input::get('id'))) {
				User::create([
					'name' => Input::get('users') ,
					'password' => bcrypt(Input::get('pass')),
					'divisi' => $divisiIn,
				]);
			} else {
				$userUpdate = User::find(Input::get('id'));
				$userUpdate->name = Input::get('users');
				$userUpdate->password = bcrypt(Input::get('pass'));
				$userUpdate->divisi = $divisiIn;
				$userUpdate->update();
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-user');
	}

	public function del($id) {
		$success = 1;
		$msg = '';
		User::find($id)->delete();
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);
	}
}