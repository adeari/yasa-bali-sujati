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
            <form action="{{ URL::to('master-filling-add') }}" method="post" id="fr">
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Warna</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="warna" id="warna"></div>
                </div>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Kode awal</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="huruf" id="huruf"></div>
                </div>
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Batas digit angka</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="digit" id="digit"></div>
                </div>

                @if (count($validasi_rules)>0)
                <div class="row">
                    <div class="col-md-3"><label class="labelleft">Aturan yang harus dipilih</label></div>
                    <div class="col-md-4 form-group">
                        @foreach ($validasi_rules as $validasi_rule)
                        <label class='checkboxlabel'><input type="checkbox" class="checkk" id="checkk{{ $validasi_rule->id }}" name="validasi_rules[]" value="{{ $validasi_rule->id }}"> {{ $validasi_rule->aturan }}</label> <br/>
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

<div class="row" id="layoutTambah"><div class="box col-md-12">
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah filling</button>
</div></div>

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-list"></i> Tabel filling</h2>
        <div class="box-icon">
            <a href="#" class="btn btn-setting btn-round btn-default"><i
                    class="glyphicon glyphicon glyphicon-question-sign"></i></a>
        </div>
    </div>
    <div class="box-content">
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Warna</th>
        <th>Kode awal</th>
        <th>Digit</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    @foreach ($fillings as $filling)
    <?php
    $datahere = '';
    foreach ($filling->validasi_rules as $validasii_rule) {
        $datahere .= ",".$validasii_rule->id;
    }
    if (strlen($datahere)>0) {
        $datahere = substr($datahere, 1);
    }
    ?>
    <tr id="row{{ $filling->id }}" data-validasi="{{ $datahere }}">
        <td>{{ $filling->warna }}</td>
        <td>{{ $filling->huruf }}</td>
        <td>{{ $filling->digit }}</td>
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $filling->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            @if ($filling->isdeleted)
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $filling->id }}" href="#">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
            </a>
            @endif
        </td>
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
                        Bagian ini untuk menambah filling atau menghapus filling dan juga mengganti data filling.
                        <br/>Warna untuk warna map yang di pakai.
                        <br/>Kode awal untuk awal penomoran contoh isi AB maka kode filenya berawalan AB.
                        <br/>Digit adalah banyaknya digit angka setelah kode contoh di ketik 5 maka kodenya AB00123.
                    </p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
function blankInput() {
    $('#msg').hide();
    $('#msg').html('');
    $('input[name="id"]').val('');
    $('#warna').val('');
    $('#huruf').val('');
    $('#digit').val('');
    $('.checkk').each(function() {
        this.checked = false;
    });
}
$(function(){
   $('#tabell').on('click','.edit',function(e) {
        e.preventDefault();
        $('#layoutTambah').hide();
        $('#layoutForm').show( "fast");
        $('#titleForm').html('<i class="glyphicon glyphicon-edit"></i> Mengubah filling');
        blankInput();
        $('input[name="id"]').val($(this).data('id'));
        var rowelement = '#row'+$(this).data('id');
        $('#warna').val($(rowelement).children('td:first').text());
        $('#huruf').val($(rowelement).children('td:eq(1)').text());
        $('#digit').val($(rowelement).children('td:eq(2)').text());
        var strvalidasi = $(rowelement).data('validasi');
        if (isNumber(strvalidasi)) {
            $('#checkk'+strvalidasi).prop("checked", true);
        } else {
            var dataValidasiarray = strvalidasi.split(',');
            dataValidasiarray.forEach(function(entry) {
                $('#checkk'+entry).prop("checked", true);
            });
        }
        $("html, body").animate({ scrollTop: 0 }, "fast");
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        $('#btnBatal').trigger('click');
        var iddata = $(this).data('id');
        if (confirm('Apakah filling ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('master-filling-delete') }}/'+iddata,
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
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah filling');
    blankInput();
   });

   $('#btnBatal').click(function(){
    $('#layoutTambah').show();
    $('#layoutForm').hide( "fast");
    blankInput();
   });

   $('#fr').submit(function(e) {
        e.preventDefault();
        var msg = '';
        if ($('#warna').val().length < 1) {
            msg = 'Tulis warnanya';
            $('#warna').focus();
        } else if ($('#huruf').val().length < 1) {
            msg = 'Tulis kode awal';
            $('#huruf').focus();
        } else if ($('#digit').val().length < 1) {
            msg = 'Tulis diit';
            $('#digit').focus();
        }
        if (msg.length > 0) {
            $('#msg').show();
            $('#msg').text(msg);
        } else {
            $(this).unbind();
            $(this).submit();
        }
   });

});    
</script>
@include('layout-footer')