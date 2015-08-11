<?php namespace App\Http\Controllers;

use App\models\Joborder;
use Illuminate\Support\Facades\Response;

class testtCont extends Controller {

	public function index()
	{
		echo bcrypt('1234');
	}

	public function backupdata(){
		$strfilename = exec(env('FOLDERBACKUP'));
		$fullFilename = env('PLACEBACKUP').$strfilename;
	    if (file_exists($fullFilename))
	    {
	        return Response::download($fullFilename, $strfilename, [
	            'Content-Length: '. filesize($fullFilename)
	        ]);
	    }
	    else
	    {
	        exit('Requested file does not exist on our server!');
	    }
	}
}