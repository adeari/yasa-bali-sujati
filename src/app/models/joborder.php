<?php namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Joborder extends Model {

	protected $table = 'joborder';

	public function customer()
    {
        return $this->hasOne('App\models\Customers');
    }

    public function exportir()
    {
        return $this->hasOne('App\models\Customers');
    }

}
