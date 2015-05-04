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
            <h2 id="titleForm"><i class="glyphicon glyphicon-edit"></i> Menambah User</h2>
        </div>
        <div class="box-content">
            <form action="{{ URL::to('master-user-add') }}" method="post" id="fr">

                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Username</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="users" id="users"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Password</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="pass" id="pass"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Divisi</label></div>
                    <div class="col-md-3 form-group">
                        <select class="form-control" name="divisi1" id="divisi1">
                            @foreach ($divisis as $divisi)
                                <option value="{{ $divisi->divisi }}">{{ $divisi->divisi }}</option>
                            @endforeach
                        </select>
                    </div>
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                    <div class="col-md-2"><label class="labelleft">Selain itu</label></div>
                    <div class="col-md-3 form-group"><input class="form-control" type="text" name="divisi" id="divisi"></div>
                </div>
                 <button type="submit" class="btn btn-success">Simpan</button>
                 <button type="button" class="btn btn-danger" id="btnBatal">Batal</button>
            </form>
        </div>
    </div>
<!--/span-->

</div>

<div class="row" id="layoutTambah"><div class="box col-md-12">
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah User</button>
</div></div>

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-user"></i> Tabel User</h2>
        <div class="box-icon">
            <a href="#" class="btn btn-setting btn-round btn-default"><i
                    class="glyphicon glyphicon glyphicon-question-sign"></i></a>
        </div>
    </div>
    <div class="box-content">
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Username</th>
        <th>Terakhir login</th>
        <th>Divisi</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    @foreach ($users as $user)
    <tr id="row{{ $user->id }}">
        <td>{{ $user->name }}</td>
        <td class="center">{{ (empty($user->last_login)) ? '' : viewdate($user->last_login) }}</td>
        <td class="center">{{ $user->divisi }}</td>
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $user->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            @if ($user->id != Auth::user()->id && $user->id != 1)
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $user->id }}" href="#">
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
                        Bagian ini untuk menambah user atau menghapus user dan juga mengganti password user lain.
                        <br/>Pada bagian ini hanya bisa di akses oleh admin saja.
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
    $('#users').val('');
    $('#pass').val('');
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
        $('#users').val($(rowelement).children('td:first').text());
        $("#divisi1 option[value='"+$(rowelement).children('td:eq(2)').text()+"']").attr('selected', 'selected');
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        var iddata = $(this).data('id');
        if (confirm('Apakah user ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('master-user-delete') }}/'+iddata,
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
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah User');
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
        if ($('#users').val().length < 1) {
            msg = 'Tulis Username';
            $('#users').focus();
        } else if ($('#pass').val().length < 1) {
            msg = 'Tulis Password';
            $('#pass').focus();
        } else if ($('#pass').val().length < 4) {
            msg = 'Password minimal 4 huruf';
            $('#pass').focus();
        }
        if (msg.length > 0) {
            $('#msg').show();
            $('#msg').text(msg);
        } else {
            $.ajax({
                url: '{{ URL::to('master-user-check') }}',
                data: $(this).serialize()
            }).done(function(result){
            if (result.success == 1) {
                $('#fr').unbind();
                $('#fr').submit();
            } else {
                $('#msg').show();
                $('#msg').text(result.msg);
            }
            });
        }
   });

});    
</script>
@include('layout-footer')