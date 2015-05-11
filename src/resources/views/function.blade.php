<?php
function viewdate($str) {
	$tgl = substr($str, 0, 19);
	$datee = new DateTime($tgl);
	$tgl = date_format($datee, 'd F Y,  H:i');
	return $tgl;
}
function viewdateInput($str) {
    $tgl = substr($str, 0, 19);
    $datee = new DateTime($tgl);
    $tgl = date_format($datee, 'd/m/Y H:i');
    return $tgl;
}
?>