<?php namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\models\Customers;

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Session;

class masterCustomerCont extends Controller1 {
	public function index($status = null)
	{
		$jenis_customer = Customers::select('jenis_customer')
		->whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir', 'rekanan')")
		->groupBy('jenis_customer')
		->get();

		$customers = Customers::orderBy('nama_perusahaan')
		->whereRaw("LOWER(jenis_customer) NOT IN ('exportir', 'importir')");
		if (!is_null($status)) {
			$customers = $customers->where('islengkap', $status);
		}
		$customers = $customers->get();
		return view('master-shipper', array(
			'customers' => $customers,
			'jenis_customer' => $jenis_customer,
			'statusSelected' => $status,
			));
	}
	public function add()
	{
		$jenisCustomerIn  = '';
		if (!empty(Input::get('jenis_customer'))) {
			$jenisCustomerIn = Input::get('jenis_customer');
		} else if (!empty(Input::get('jenis_customer1'))) {
			$jenisCustomerIn = Input::get('jenis_customer1');
		}
		$validator = Validator::make(
			 [
		        'nama_perusahaan' => Input::get('nama_perusahaan'),
		        'contact_person' => Input::get('contact_person'),
		        'telepon' => Input::get('telepon'),
		        'alamat' => Input::get('alamat'),
		        'jenis_customer' => $jenisCustomerIn,
		    ],
		    [
		        'nama_perusahaan' => 'required',
		        'contact_person' => 'required',
		        'telepon' => 'required',
		        'alamat' => 'required',
		    ]
		);

		$msg = "Customer tersimpan";
		if ($validator->fails()) {
			$msg = $validator->messages();
		} else {
			$customer = new Customers();
			if (!empty(Input::get('id'))) {
				$customer = Customers::find(Input::get('id'));
			}

			$customer->contact_person = Input::get('contact_person');
			$customer->alamat = Input::get('alamat');
			$customer->telepon = Input::get('telepon');
			$customer->nama_perusahaan = Input::get('nama_perusahaan');
			$customer->email = Input::get('email');
			$customer->jenis_customer = $jenisCustomerIn;

			if (!empty($customer->contact_person) 
				&& !empty($customer->alamat) 
				&& !empty($customer->telepon) 
				&& !empty($customer->nama_perusahaan) 
				&& !empty($customer->email) 
				&& !empty($customer->jenis_customer) 
				) {
				$customer->islengkap = true;
			} else {
				$customer->islengkap = false;
			}

			if (empty(Input::get('id'))) {
				$customer->save();
			} else {
				$customer->update();
			}
		}
		Session::flash('msg', $msg);
		return redirect('master-shipper');
	}

	public function del($id) {
		$success = 0;
		$msg = '';
		if (Customers::where('isdeleted','=',false)->whereid($id)->count() > 0) {
			$msg = 'Tidak bida dihapus karena data ini di pakai di job order';
		} else {
			Customers::where('isdeleted','=',true)->whereid($id)->delete();
			$success = 1;
		}
		$result = array('success' => $success, 'msg' => $msg);
		return response()->json($result);

	}
}