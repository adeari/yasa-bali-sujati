<?php namespace App\Http\Controllers;

use Illuminate\Support\Facades\Response;

class utilityCont extends Controller {
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