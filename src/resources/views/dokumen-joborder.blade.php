@include('layout-header')
@include('layout-menu')
@if (Session::has('msg'))
    <div class="box-content breadcrumb" id="msg" style="">{{ Session::get('msg') }}</div>
@else
    <div class="box-content breadcrumb" id="msg" style="display:none"></div>
@endif
<div class="row">
    <div class="box-inner" id="layoutForm" style="display:none">
        <div class="box-header well" data-original-title="">
            <h2 id="titleForm"><i class="glyphicon glyphicon-edit"></i> Menambah Customer</h2>
        </div>
        <div class="box-content">
            <form action="{{ URL::to('dokumen-joborder-add') }}" method="post" id="fr">
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Kode</label></div>
                    <div class="col-md-9 form-group" id="kodelistt"></div>
                </div>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Customer</label></div>
                    <div class="col-md-4 form-group">
                    <div class="control-group">
                    <div class="controls">
                        <select class="form-control" name="customer" id="customer" data-rel="chosen" style="width:300px;">
                            @foreach ($customers as $customer)
                                <option value="{{ $customer->id }}">{{ $customer->nama_perusahaan }}</option>
                            @endforeach
                        </select>
                        </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Exportir</label></div>
                    <div class="col-md-4 form-group">
                        <select class="form-control" name="exportir" id="exportir" data-rel="chosen">
                            @foreach ($exportirs as $exportir)
                                <option value="{{ $exportir->id }}">{{ $exportir->nama_perusahaan }}</option>
                            @endforeach
                        </select>
                    </div>
                </div>
                @if (count($jenis_kegiatan)<1)
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Jenis kegiatan</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="jenis_kegiatan" id="jenis_kegiatan"></div>
                </div>
                @else
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Jenis kegiatan</label></div>
                    <div class="col-md-3 form-group">
                        <select class="form-control" name="jenis_kegiatan1" id="jenis_kegiatan1">
                            @foreach ($jenis_kegiatan as $jenis_kegiatan1)
                                <option value="{{ $jenis_kegiatan1->jenis_kegiatan }}">{{ $jenis_kegiatan1->jenis_kegiatan }}</option>
                            @endforeach
                        </select>
                    </div>
                    <div class="col-md-2"><label class="labelleft">Selain itu</label></div>
                    <div class="col-md-3 form-group"><input class="form-control" type="text" name="jenis_kegiatan" id="jenis_kegiatan"><br/></div>
                </div>
                @endif
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Waktu pelaksanaan</label></div>
                    <div class="col-md-3 form-group"><input class="form-control" type="text" name="tgl_pelaksanaan" id="tgl_pelaksanaan">dd/mm/yyyy HH:mm</div>
                </div>
                @if (count($pegawais) > 0)
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Petugas operasional</label></div>
                    <div class="col-md-9" style="margin:0 0 10px 0;" id ="pegawailisttt">
                    <div style="width:80%;float:left;padding:0 10px 0 0" id="selectlayoutpegawai">
                        <select class="form-control float-left" name="pegawaiselect" id="pegawaiselect" data-rel="chosen">
                            @foreach ($pegawais as $pegawai)
                                <option value="{{ $pegawai->id }}">{{ $pegawai->nama }}</option>
                            @endforeach
                        </select>
                    </div>
                    <button type="button" class="btn btn-success float-left" id="btnpegawaiok">OK</button>
                    </div>
                </div>
                @else
                 <div class="row">
                    <div class="col-md-4"><label class="labelleft">Data petugas masih kosong</label></div>
                    </div>
                @endif
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Catatan</label></div>
                    <div class="col-md-4 form-group"><textarea class="form-control" type="text" name="catatan" id="catatan" rows="3"></textarea></div>
                </div>
                @if (count($validasi_rules)>0)
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Validasi</label></div>
                    <div class="col-md-4 form-group" id="validasicheckedlist">
                    <span id='msgStatus'></span>
                        @foreach ($validasi_rules as $validasi_rule)
                        <label class='checkboxlabel'><input type="checkbox" class="checkk" id="checkk{{ $validasi_rule->id }}" name="validasi_rules[]" value="{{ $validasi_rule->id }}"> {{ $validasi_rule->aturan }} <span class="label label-warning classbannervalidasi" id="bannervalidasi{{ $validasi_rule->id }}"></span></label><br/>
                        @endforeach 
                    </div>
                </div>
                @endif
                 <button type="submit" class="btn btn-success">Simpan</button>
                 <button type="button" class="btn btn-danger" id="btnBatal">Batal</button>
            </form>
        </div>
    </div>
<!--/span-->

</div>

@if (is_null($statusSelected))
<div class="row" id="layoutTambah"><div class="box col-md-12">
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah job order</button>
</div></div>
@endif

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-list"></i> Tabel job order</h2>
        <div class="box-icon">
            <a href="#" class="btn btn-setting btn-round btn-default"><i
                    class="glyphicon glyphicon glyphicon-question-sign"></i></a>
        </div>
    </div>
    <div class="box-content">
    <div class="row" style="margin:0 0 10px 0;">
        <div class="col-md-2">Status : </div>
        <div class="col-md-4">
            <select class="form-control float-left" name="statusChoose" id="statusChoose">
                <option value="">Semua status</option>}
                <option value="kosong"{{ ($statusSelected == 'kosong') ? ' selected' : '' }}>Belum valid</option>
                <option value="sebagian"{{ ($statusSelected == 'sebagian') ? ' selected' : '' }}>Validasi sebagian</option>
                <option value="lengkap"{{ ($statusSelected == 'lengkap') ? ' selected' : '' }}>Validasi lengkap</option>
            </select>
        </div>
    </div>
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Kode</th>
        <th></th>
        <th>Tgl. pelaksanaan</th>
        <th>Kegiatan</th>
        <th>Customer / CP</th>
        <th>Exportir / CP</th>
        @if (is_null($statusSelected))
        <th></th>
        @endif
    </tr>
    </thead>
    <tbody>
    @foreach ($joborders as $joborder)
    <tr id="row{{ $joborder->id }}" 
    data-kode="{{ $joborder->kode }}" 
    data-customer="{{ $joborder->customer }}" 
    data-jeniskegiatan="{{ $joborder->jenis_kegiatan }}" 
    data-exportir="{{ $joborder->exportir }}" 
    data-tglpelaksanaan="{{ viewdateInput($joborder->tgl_pelaksanaan) }}" 
    data-catatan="{{ $joborder->catatan }}" 
    data-fillingid="{{ $joborder->filling }}" 
    data-status="{{ $joborder->status }}" 
    data-filling="<?php
    $validationRuless = $joborder->filling1->validasi_rules()->get();
    $theFirst = true;

    foreach ($validationRuless as $validationRules) {
        if ($theFirst) {
                echo $validationRules->id;
                $theFirst = false;
            }
            else {
                echo ",".$validationRules->id;
            }
    }
    ?>" 
    data-pegawai='<?php  
        $theFirst = true;

        foreach ($joborder->pegawai as $pegawai) {
            if ($theFirst) {
                echo '[{"id":'.$pegawai->id.',"nama":"'.$pegawai->nama.'"}';
                $theFirst = false;
            }
            else {
                echo ',{"id":'.$pegawai->id.',"nama":"'.$pegawai->nama.'"}';
            }
        }
        if (!$theFirst) {
            echo "]";
        }
        ?>' 
    data-validasirules="<?php 
        $theFirst = true;

        foreach ($joborder->validasi_rules as $validasi_rule) {
            if ($theFirst) {
                echo $validasi_rule->id;
                $theFirst = false;
            }
            else {
                echo ",".$validasi_rule->id;
            }
        }
    ?>" 
    >
        <td><a target="_blank" href="{{ URL::to('dokumen-joborder1/'.$joborder->kode) }}">{{ $joborder->kode }}</a> </td>
        <td><?php 
if ($joborder->status == 'Lengkap') {
    echo '<i class="glyphicon glyphicon-ok"></i>';
} else if ($joborder->status == 'Sebagian') {
    echo '<i class="glyphicon glyphicon-magnet"></i>';
}
        ?></td>
        <td>{{ viewdate($joborder->tgl_pelaksanaan) }}</td>
        <td>{{ $joborder->jenis_kegiatan }}</td>
        <td>{{ $joborder->customer1->nama_perusahaan.' / '.$joborder->customer1->contact_person }}</td>
        <td>{{ $joborder->exportir1->nama_perusahaan.' / '.$joborder->exportir1->contact_person }}</td>
        @if (is_null($statusSelected))
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $joborder->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $joborder->id }}" href="#">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
            </a>
        </td>
        @endif
    </tr>
    @endforeach
    </tbody>
    </table>
    </div>
    </div>
    </div>
    <!--/span-->


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>Bantuan</h3>
                </div>
                <div class="modal-body">
                    <p>
                        Bagian ini untuk menambah job order atau menghapus job order dan juga mengganti data job order.
                        <br/>Pada bagain tanggal cukup isi dengan angka maka otomatis di atur. Aturan tanggal 2 digit tanggal  2 digit bulan  2 digit tahun 2 digit jam 2 digit menit. jadi cukup ketik angka saja ndak usah yang lain.
                        <br/>Untuk memilih petugas operasi marking maka tinggal klik dropdown nya lalu bisa nulis nama petugas untuk memudahkan pencarian lalu klik petugas yang diinginkan. Kemudian jika nama petugas telah terpilih di dropdown tinggal klik ok maka nama petugas akan muncul di atas dropdown. Nama - nama petugas di atas dropdown lah yang akan tersimpan. Jika ternyata nama petugas yang diingkan telah tampil di dropdown tinggal klik ok saja. Untuk menghapus pegawai yang terpilih tinggal klik tombol merah X saja ini bisa di pakai untuk ralat.
                        <br/>Untuk bagian validasi bisa di pilih saat pembuatan awal job order atau waktu ralat saja.
                        <br/>Klik kode job order untuk melihat print previewnya dan (url  atau link) tersebut bisa di copy dan di berikan ke divisi lain agar cepat analisanya.
                    </p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
Array.prototype.contains = function(k) {
  for(var i=0; i < this.length; i++){
    if(this[i] == k){
      return true;
    }
  }
  return false;
}


@if (count($pegawais) > 0)
var pegawaiidlist = [<?php
    $thefirst = true;
    foreach ($pegawais as $pegawai)
    {
        if ($thefirst) {
            $thefirst = false;
            echo $pegawai->id;
        } else {
            echo ','.$pegawai->id;
        }
    }
?>];
var pegawaitextlist = [<?php
    $thefirst = true;
    foreach ($pegawais as $pegawai)
    {
        if ($thefirst) {
            $thefirst = false;
            echo '\''.$pegawai->nama .'\'';
        } else {
            echo ',\''.$pegawai->nama .'\'';
        }
    }
?>];
@endif
function refreshselectoption() {
    var pegawaiidselected = [];
var i = 0;
        $('#pegawailisttt .pegawaiinput').each(function( index ) {
            pegawaiidselected[i] = $(this).val();
            i++;
        });
        
        
        i = 0;
        var optionlist = '';
        pegawaiidlist.forEach(function(entry) {
            (pegawaiidselected.contains(entry)) ? optionlist += '' : optionlist += '<option value="'+entry+'">'+pegawaitextlist[i]+'</option>';
            i++;
        });
        if (optionlist.length > 0 ) {
            $('#pegawailisttt #selectlayoutpegawai').html('<select class="form-control float-left" name="pegawaiselect" id="pegawaiselect">'+optionlist+'</select>');
            $('#pegawailisttt #btnpegawaiok').show();
        } else {
            $('#pegawailisttt #btnpegawaiok').hide();
             $('#pegawailisttt #selectlayoutpegawai').html('');
        }
        
        $('#selectlayoutpegawai #pegawaiselect').chosen({
            no_results_text: "Tidak ada data ini",
            max_selected_options: 25,
            width: "100%",
            height : '38px'
          });
}

function blankInput() {
    $('#msg').hide();
    $('#msg').html('');
    $('input[name="id"]').val('');

    $('#pegawailisttt .innpegawaiiclass').each(function() {
        $(this).remove();
    });
    $('#pegawailisttt #selectlayoutpegawai').html('');
    var optionlist = '';
    var i=0;
        pegawaiidlist.forEach(function(entry) {
            optionlist += '<option value="'+entry+'">'+pegawaitextlist[i]+'</option>';
            i++;
        });
    $('#pegawailisttt #selectlayoutpegawai').html('<select class="form-control float-left" name="pegawaiselect" id="pegawaiselect">'+optionlist+'</select>');
    $('#pegawailisttt #btnpegawaiok').show();
    $('#kodelistt .kodekk').each(function() {
        $(this).prop("checked", false);
    });
    $('#validasicheckedlist .classbannervalidasi').each(function() {
        $(this).html('');
    });
    $('#validasicheckedlist .checkk').each(function() {
        $(this).prop("checked", false);
    });
    $('#jenis_kegiatan').val('');
    $('#tgl_pelaksanaan').val('');
    $('#catatan').val('');

    $('#msgStatus').text('');
    $('#msgStatus').removeClass();
}

function refreshKode(){
    
    $.ajax({
        url: '{{ URL::to('dokumen-joborder-view-option-kode') }}',
        data: {'_token' : '{!! csrf_token() !!}'}
    }).done(function(results){
        var str = '';

        $.each(results, function(index, value) {
            str += '<label class="checkboxlabel" style="margin:0 20px 0 0;"><input type="radio" class="kodekk" name="kode" id="kodeee'+value.id+'" data-validasi="'+value.aturanValidasi+'" value="'+value.id+'"> '+value.kode+' '+value.warna+'</label>';
        });
        $('#kodelistt').html(str);
        $('body').css('overflow','auto');
    });
}

function pegawaiInput(pegawaiidselectedd, strr) {
    return '<div id="inn'+pegawaiidselectedd+'" class="innpegawaiiclass"><input type="hidden" name="pegawaiinputval[]" class="float-left pegawaiinput" value="'+pegawaiidselectedd+'"><input class="form-control float-left" type="text"  style="width:60%;float:left;padding:10px 10px 10px 10px;margin:5px 10px 5px 0;background:#FFF;" name="pegawaiinput"  value="'+strr+'" readonly><button type="button" class="btn btn-danger float-left delthispegawai" data-id="'+pegawaiidselectedd+'" style="margin:5px 5px 5px 0">X</button><br/></div>';
}

function showWajib(str) {
    var datalist = str;
    $('#validasicheckedlist .classbannervalidasi').each(function() {
        $(this).html('');
    });
    
    if (isNumber(datalist)) {
        $('#bannervalidasi'+datalist).html('Wajib');
    } else {
        var dataValidasiarray = datalist.split(',');
        dataValidasiarray.forEach(function(entry) {
            $('#bannervalidasi'+entry).html('Wajib');
        });
    }
}

$(function(){
   $('#tabell').on('click','.edit',function(e) {
        e.preventDefault();
        $('#layoutTambah').hide();
        $('#layoutForm').show( "fast");
        $('#titleForm').html('<i class="glyphicon glyphicon-edit"></i> Mengubah job order');
        blankInput();
        $('input[name="id"]').val($(this).data('id'));
        var rowelement = '#row'+$(this).data('id');
        $('#kodelistt').html('<input type="hidden" name="kode" value="'+$(rowelement).data('fillingid')+'"><input class="form-control" type="text" value="'+$(rowelement).data('kode')+'" style="background:#FFF;width:250px;" readonly>');
        $('#jenis_kegiatan1').val($(rowelement).data('jeniskegiatan'));        
        $("#customer option[value='"+$(rowelement).data('customer')+"']").attr('selected', 'selected');
        $('#customer').trigger("chosen:updated");
        $("#exportir option[value='"+$(rowelement).data('exportir')+"']").attr('selected', 'selected');
        $('#exportir').trigger("chosen:updated");
        $('#tgl_pelaksanaan').val($(rowelement).data('tglpelaksanaan'));
        var statuss = $(rowelement).data('status');
        if (statuss == 'Lengkap') {
            $('#msgStatus').html('Validasi '+statuss+'&nbsp;&nbsp;&nbsp;<br/>');
            $('#msgStatus').addClass("label-success label label-default");
        } else if (statuss == 'Sebagian') {
            $('#msgStatus').html('Validasi '+statuss+'&nbsp;&nbsp;&nbsp;<br/>');
            $('#msgStatus').addClass("label-warning  label label-default");
        } else {
            $('#msgStatus').html('Belum divalidasi&nbsp;&nbsp;&nbsp;<br/>');
            $('#msgStatus').addClass("label-danger  label label-default");
        }
        if ($(rowelement).data('pegawai').length > 0) {
            $.each($(rowelement).data('pegawai'), function(i, item) {
                $('#pegawailisttt #selectlayoutpegawai').before(pegawaiInput(item.id, item.nama));
            });
            refreshselectoption();
        }
        $('#catatan').val($(rowelement).data('catatan'));
        var strvalidasi = $(rowelement).data('validasirules');
        if (isNumber(strvalidasi)) {
            $('#checkk'+strvalidasi).prop("checked", true);
        } else {
            var dataValidasiarray = strvalidasi.split(',');
            dataValidasiarray.forEach(function(entry) {
                $('#checkk'+entry).prop("checked", true);
            });
        }
        showWajib($(rowelement).data('filling'));
        $("html, body").animate({ scrollTop: 0 }, "fast");
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        $('#btnBatal').trigger('click');
        var iddata = $(this).data('id');
        if (confirm('Apakah job order ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('dokumen-joborder-delete') }}/'+iddata,
                data: {'_token' : '{!! csrf_token() !!}'}
            }).done(function(result){
            if (result.success == 1) {
                $('#row'+iddata).remove();
            } else {
                alert(result.msg);
            }
            });
            
        }
    });

   $('#btnTambah').click(function(){
    $('#layoutTambah').hide();
    $('#layoutForm').show( "fast");
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah job order');
    blankInput();
    refreshKode();
   });

   $('#btnBatal').click(function(){
    $('#layoutTambah').show();
    $('#layoutForm').hide( "fast");
    blankInput();
   });

   $('#fr').submit(function(e) {
        e.preventDefault();
        var msg = '';

        var iskodechecked = false;
        $('#kodelistt .kodekk').each(function() {
            if ($(this).is(':checked')) {
                iskodechecked = true;
            }
        });

        if (!iskodechecked && $('input[name="id"]').val().length < 1) {
            msg = 'Pilih kode';

@if (count($jenis_kegiatan)<1)
        } else if ($('#jenis_kegiatan').val().length < 1) {
            msg = 'Tulis jenis kegiatan';
            $('#jenis_kegiatan').focus();
@endif
        } else if ($('#tgl_pelaksanaan').val().length < 1) {
            msg = 'Tulis tanggal pelaksanaan';
            $('#tgl_pelaksanaan').focus();
        } 
        if (msg.length > 0) {
            $('#msg').show();
            $('#msg').text(msg);
        } else {
            $(this).unbind();
            $(this).submit();
        }
   });


   $('#pegawailisttt').on('click','#btnpegawaiok',function(e) {
        var pegawaiidselectedd = $("#pegawailisttt #pegawaiselect").chosen().val();
        $('#pegawailisttt #selectlayoutpegawai').before(
            pegawaiInput(pegawaiidselectedd, $('#pegawailisttt #pegawaiselect_chosen a.chosen-single').text()));
        refreshselectoption();
   });

$('#pegawailisttt').on('click','.delthispegawai',function(){
    $('#pegawailisttt #inn'+$(this).data('id')).remove();
    refreshselectoption();
});

$('#kodelistt').on('click','.kodekk',function(e){
    showWajib($(this).data('validasi'));
});
$('#statusChoose').change(function(e) {
    var str = '{{ URL::to('dokumen-joborder') }}';

    if ($(this).val().length > 0) {
        str += '-'+$(this).val();
    }
    document.location = str;
});

$('#tgl_pelaksanaan').mask("00/00/0000 00:00", {placeholder: "__/__/____ __:__"}, {selectOnFocus: true});
refreshKode();
});    
</script>
@include('layout-footer')