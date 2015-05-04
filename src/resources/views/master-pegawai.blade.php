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
            <h2 id="titleForm"><i class="glyphicon glyphicon-edit"></i> Menambah Pegawai</h2>
        </div>
        <div class="box-content">
            <form action="{{ URL::to('master-pegawai-add') }}" method="post" id="fr">
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Nama</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="nama" id="nama"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Alamat</label></div>
                    <div class="col-md-4 form-group"><textarea class="form-control" type="text" name="alamat" id="alamat"></textarea> </div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Telepon</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="telepon" id="telepon"></div>
                </div>
                @if (count($divisis)<1)
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Divisi</label></div>
                    <div class="col-md-3 form-group"><input class="form-control" type="text" name="divisi" id="divisi"></div>
                </div>
                @else
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Divisi</label></div>
                    <div class="col-md-3 form-group">
                        <select class="form-control" name="divisi1" id="divisi1">
                            @foreach ($divisis as $divisi)
                                <option value="{{ $divisi->divisi }}">{{ $divisi->divisi }}</option>
                            @endforeach
                        </select>
                    </div>
                    <div class="col-md-2"><label class="labelleft">Selain itu</label></div>
                    <div class="col-md-3 form-group"><input class="form-control" type="text" name="divisi" id="divisi"></div>
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
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah Pegawai</button>
</div></div>

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-user"></i> Tabel Pegawai</h2>
        <div class="box-icon">
            <a href="#" class="btn btn-setting btn-round btn-default"><i
                    class="glyphicon glyphicon glyphicon-question-sign"></i></a>
        </div>
    </div>
    <div class="box-content">
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Nama</th>
        <th>Alamat</th>
        <th>Telepon</th>
        <th>Divisi</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    @foreach ($pegawais as $pegawai)
    <tr id="row{{ $pegawai->id }}">
        <td>{{ $pegawai->nama }}</td>
        <td>{{ $pegawai->alamat }}</td>
        <td>{{ $pegawai->telepon }}</td>
        <td>{{ $pegawai->divisi }}</td>
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $pegawai->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $pegawai->id }}" href="#">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
            </a>
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
                        Bagian ini untuk menambah pegawai atau menghapus pegawai dan juga mengganti data pegawai.
                        <br/>Pada kolom divisi bisa memilih data yang ada di dropdown pilihan tapi jika divisi terebut tidak ada
                        di dropdown maka isilah kolom selain itu. Jika mengisi kolom selain itu maka selain itulah yang akan di simpan walau di dropdown memilih admin misalnya.
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
    $('input[name="id"]').val('');
    $('#nama').val('');
    $('#telepon').val('');
    $('#alamat').val('');
    $('#divisi').val('');
}
$(function(){
   $('#tabell').on('click','.edit',function(e) {
        e.preventDefault();
        $('#layoutTambah').hide();
        $('#layoutForm').show( "fast");
        $('#titleForm').html('<i class="glyphicon glyphicon-edit"></i> Mengubah User');
        blankInput();
        $('input[name="id"]').val($(this).data('id'));
        var rowelement = '#row'+$(this).data('id');
        $('#nama').val($(rowelement).children('td:first').text());
        $('#telepon').val($(rowelement).children('td:eq(1)').text());
        $('#alamat').val($(rowelement).children('td:eq(2)').text());
        $("#divisi1 option[value='"+$(rowelement).children('td:eq(3)').text()+"']").attr('selected', 'selected');
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        var iddata = $(this).data('id');
        if (confirm('Apakah pegawai ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('master-pegawai-delete') }}/'+iddata,
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
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah Pegawai');
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
        if ($('#nama').val().length < 1) {
            msg = 'Tulis nama';
            $('#nama').focus();
        } else if ($('#alamat').val().length < 1) {
            msg = 'Tulis alamat';
            $('#alamat').focus();
        } else if ($('#telepon').val().length < 1) {
            msg = 'Tulis telepon';
            $('#telepon').focus();
        @if (count($divisis)<1)
        } else if ($('#divisi').val().length < 1) {
            msg = 'Tulis Divisi';
            $('#divisi').focus();
        @endif
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