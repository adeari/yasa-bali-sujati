@include('function')
<!DOCTYPE html>
<html lang="en">
<head>
    <!--
        ===
        This comment should NOT be removed.

        Charisma v2.0.0

        Copyright 2012-2014 Muhammad Usman
        Licensed under the Apache License v2.0
        http://www.apache.org/licenses/LICENSE-2.0

        http://usman.it
        http://twitter.com/halalit_usman
        ===
    -->
    <meta charset="utf-8">
    <title>PT. YASA BALI SUJATI</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Muhammad Usman">

    <!-- The styles -->
    <link id="bs-css" href="{{ URL::to("public/komponenku/charisma") }}/css/bootstrap-cerulean.min.css" rel="stylesheet">

    <link href="{{ URL::to("public/komponenku/charisma") }}/css/charisma-app.css" rel="stylesheet">
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/jquery.noty.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/noty_theme_default.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/elfinder.min.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/elfinder.theme.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/uploadify.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/animate.min.css' rel='stylesheet'>
    <link href='{{ URL::to("public/komponenku/charisma") }}/css/mystyleee.css' rel='stylesheet'>
</head>
<body>
<div class="row" style="width:100%;">
    <div class="box-inner" style="width:90%;margin:0 auto;">
        <div class="box-header well" data-original-title="">
            <h2 id="titleForm"> Job order</h2>
        </div>
        <div class="box-content">
            <form id="fr">
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Kode</label></div>
                    <div class="col-md-8 form-group" id="kodelistt">{{ $joborder->kode }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Customer</label></div>
                    <div class="col-md-8 form-group">{{ $joborder->customer1->nama_perusahaan }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Exportir</label></div>
                    <div class="col-md-8 form-group">{{ $joborder->exportir1->nama_perusahaan }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Jenis kegiatan</label></div>
                    <div class="col-md-8 form-group">{{ $joborder->jenis_kegiatan }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Waktu pelaksanaan</label></div>
                    <div class="col-md-8 form-group">{{ viewdateInput($joborder->tgl_pelaksanaan) }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Waktu pelaksanaan</label></div>
                    <div class="col-md-8 form-group">{{ viewdateInput($joborder->tgl_pelaksanaan) }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Petugas operasional</label></div>
                    <div class="col-md-8 form-group"><?php
foreach ($joborder->pegawai as $pegawai) {
                echo $pegawai->nama.'<br/>';
        }
                    ?></div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Catatan</label></div>
                    <div class="col-md-8 form-group">{{ $joborder->catatan }}</div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Status validasi</label></div>
                    <div class="col-md-8 form-group"><?php 
                    if ($joborder->status == 'Kosong') {
                        echo "Belum valid";
                        } else {
                            echo 'Validasi '.$joborder->status;
                            }?></div>
                </div>
@if (count($validasi_ruless) > 0)
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Data yang valid</label></div>
                    <div class="col-md-8 form-group"><?php
    foreach ($validasi_ruless as $validasi_rule) {
                echo $validasi_rule->aturan.'<br/>';
    }?></div>
                </div>
    @if (count($rulesnotValid) > 0)
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-2"><label class="labelleft">Data belum valid</label></div>
                    <div class="col-md-8 form-group"><?php
                    foreach ($rulesnotValid as $rulesnotValid1) {
                        echo $rulesnotValid1->aturan.'<br/>';
                    }
    ?></div>
    @endif
@endif
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>