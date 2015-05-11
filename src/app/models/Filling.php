<?php namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Filling extends Model {

	protected $table = 'filling';

	public function validasi_rules()
    {
        return $this->belongsToMany('App\models\Validasi_rules');
    }

}
