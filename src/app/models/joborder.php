<?php namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Joborder extends Model {

	protected $table = 'joborder';

	public function customer1()
    {
        return $this->hasOne('App\models\Customers','id','customer');
    }

    public function exportir1()
    {
        return $this->hasOne('App\models\Customers','id','exportir');
    }

    public function pegawai()
    {
        return $this->belongsToMany('App\models\Pegawai');
    }
    public function validasi_rules()
    {
        return $this->belongsToMany('App\models\Validasi_rules');
    }
    public function filling1()
    {
        return $this->hasOne('App\models\Filling', 'id', 'filling');
    }
}
