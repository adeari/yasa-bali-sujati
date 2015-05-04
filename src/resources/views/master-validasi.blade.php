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
            <form action="{{ URL::to('master-validasi-add') }}" method="post" id="fr">
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Urutan</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="urutan" id="urutan" style="text-align:right;"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Aturan</label></div>
                    <div class="col-md-4 form-group"><textarea name="aturan" id="aturan" class="form-control" rows="3"></textarea></div>
                </div>
                 <button type="submit" class="btn btn-success">Simpan</button>
                 <button type="button" class="btn btn-danger" id="btnBatal">Batal</button>
            </form>
        </div>
    </div>
<!--/span-->

</div>

<div class="row" id="layoutTambah"><div class="box col-md-12">
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah aturan validasi</button>
</div></div>

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-list"></i> Tabel aturan validasi</h2>
        <div class="box-icon">
            <a href="#" class="btn btn-setting btn-round btn-default"><i
                    class="glyphicon glyphicon glyphicon-question-sign"></i></a>
        </div>
    </div>
    <div class="box-content">
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Urutan</th>
        <th>Aturan</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    @foreach ($validasi_rules as $validasi_rule)
    <tr id="row{{ $validasi_rule->id }}">
        <td>{{ $validasi_rule->urutan }}</td>
        <td>{{ $validasi_rule->aturan }}</td>
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $validasi_rule->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            @if ($validasi_rule->isdeleted)
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $validasi_rule->id }}" href="#">
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
                        Bagian ini untuk menambah aturan validasi atau menghapus validasi dan juga mengganti data validasi.
                        <br/>Contohnya SPK, berita acara, marking list dan lainnya.
                        <br/>Data yang tidak bisa di hapus karena sedang di pakai oleh data filling atau di job order.
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
    $('#urutan').val('');
    $('#aturan').val('');
}
$(function(){
   $('#tabell').on('click','.edit',function(e) {
        e.preventDefault();
        $('#layoutTambah').hide();
        $('#layoutForm').show( "fast");
        $('#titleForm').html('<i class="glyphicon glyphicon-edit"></i> Mengubah aturan validasi');
        blankInput();
        $('input[name="id"]').val($(this).data('id'));
        var rowelement = '#row'+$(this).data('id');
        $('#urutan').val($(rowelement).children('td:first').text());
        $('#aturan').val($(rowelement).children('td:eq(1)').text());
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        var iddata = $(this).data('id');
        if (confirm('Apakah validasi aturan ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('master-validasi-delete') }}/'+iddata,
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
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah aturan validasi');
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
        if ($('#urutan').val().length < 1) {
            msg = 'Tulis urutannya';
            $('#urutan').focus();
        } else if ($('#aturan').val().length < 1) {
            msg = 'Tulis aturannya';
            $('#aturan').focus();
        }
        if (msg.length > 0) {
            $('#msg').show();
            $('#msg').text(msg);
        } else {
            $(this).unbind();
            $(this).submit();
        }
   });
$('#urutan').mask("00000");
});    
</script>
@include('layout-footer')