<?php

Route::get('test', 'testtCont@index');

Route::get('/', 'loginCont@index');
Route::post('postlogin', 'loginCont@postlogin');
Route::get('dashboard', 'dashboardCont@index');
Route::get('logout', 'logoutCont@logout');

$prefix = 'master-user';
Route::get($prefix, 'masterUserCont@index');
Route::post($prefix.'-add', 'masterUserCont@add');
Route::post($prefix.'-check', 'masterUserCont@check');
Route::post($prefix.'-delete/{id}', 'masterUserCont@del');

$prefix = 'master-pegawai';
$controllername = 'masterPegawaiCont@';
Route::get($prefix, $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');

$prefix = 'master-customer';
$controllername = 'masterCustomerCont@';
Route::get($prefix, $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');
Route::get($prefix.'-{status}', $controllername.'index');

$prefix = 'master-shipper';
$controllername = 'masterSipperCont@';
Route::get($prefix, $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');
Route::get($prefix.'-{status}', $controllername.'index');

$prefix = 'master-filling';
$controllername = 'masterFillingCont@';
Route::get($prefix, $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');

$prefix = 'master-validasi';
$controllername = 'masterValidasiCont@';
Route::get($prefix, $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');

$prefix = 'dokumen-joborder';
$controllername = 'dokumenJoborderCont@';
Route::get($prefix, $controllername.'index');
Route::get($prefix.'-{status}', $controllername.'index');
Route::post($prefix.'-add', $controllername.'add');
Route::post($prefix.'-delete/{id}', $controllername.'del');

Route::post($prefix.'-view-option-kode', $controllername.'viewOptionCode');
Route::get($prefix.'1/{kode}', $controllername.'viewKode');
Route::post($prefix.'-view-1customer', $controllername.'view1Customer');