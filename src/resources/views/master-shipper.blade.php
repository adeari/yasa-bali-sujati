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
            <h2 id="titleForm"><i class="glyphicon glyphicon-edit"></i> Menambah shipper</h2>
        </div>
        <div class="box-content">
            <form action="{{ URL::to('master-shipper-add') }}" method="post" id="fr">
                    <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    <input name="id" type="hidden"/>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Nama perusahaan</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="nama_perusahaan" id="nama_perusahaan"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Contact person</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="contact_person" id="contact_person"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Alamat</label></div>
                    <div class="col-md-4 form-group"><textarea class="form-control" type="text" name="alamat" id="alamat"></textarea> </div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Telepon</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="text" name="telepon" id="telepon"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Email</label></div>
                    <div class="col-md-4 form-group"><input class="form-control" type="email" name="email" id="email"></div>
                </div>
                <div class="row">
                    <div class="col-md-2"><label class="labelleft">Jenis shipper</label></div>
                    <div class="col-md-3 form-group">
                        <select class="form-control" name="jenis_customer1" id="jenis_customer1">
                            <option value="Exportir">Exportir</option>
                            <option value="Importir">Importir</option>
                        </select>
                    </div>
                </div>
                 <button type="submit" class="btn btn-success">Simpan</button>
                 <button type="button" class="btn btn-danger" id="btnBatal">Batal</button>
            </form>
        </div>
    </div>
<!--/span-->

</div>

<div class="row" id="layoutTambah"><div class="box col-md-12">
<button type="button" class="btn btn-success" id="btnTambah"><i class="glyphicon glyphicon-plus"></i>Tambah shipper</button>
</div></div>

<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-user"></i> Tabel shipper</h2>
            
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
                <option value="">Semua status</option>
                <option value="false" {{ ($statusSelected == 'false') ? ' selected' : '' }}>Belum lengkap</option>
                <option value="true" {{ ($statusSelected == 'true') ? ' selected' : '' }}>Lengkap</option>
            </select>
        </div>
    </div>
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive" id="tabell">
    <thead>
    <tr>
        <th>Perusahaan</th>
        <th></th>
        <th>Cont. person</th>
        <th>Alamat</th>
        <th>Telepon</th>
        <th style="display:none">Email</th>
        <th>Jenis shipper</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    @foreach ($customers as $customer)
    <tr id="row{{ $customer->id }}" 
    data-nama_perusahaan="{{ $customer->nama_perusahaan }}"
    data-contact_person="{{ $customer->contact_person }}"
    data-alamat="{{ $customer->alamat }}"
    data-telepon="{{ $customer->telepon }}"
    data-email="{{ $customer->email }}"
    data-jenis_customer="{{ $customer->jenis_customer }}"
    >
        <td>{{ $customer->nama_perusahaan }}</td>
        <td><?php
         echo ($customer->islengkap) ? '<i class="glyphicon glyphicon-ok"></i>' : '<i class="glyphicon glyphicon-magnet"></i>'; 
         ?></td>
        <td>{{ $customer->contact_person }}</td>
        <td>{{ $customer->alamat }}</td>
        <td>{{ $customer->telepon }}</td>
        <td style="display:none">{{ $customer->email }}</td>
        <td>{{ $customer->jenis_customer }}</td>
        <td class="center">
            <a class="btn btn-info btn-xs edit"data-id="{{ $customer->id }}" href="#">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
            @if ($customer->isdeleted)
            <a class="btn btn-danger btn-xs hapus" data-id="{{ $customer->id }}" href="#">
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
                        Bagian ini untuk menambah shipper atau menghapus shipper dan juga mengganti data shipper.
                        <br/>Pada kolom jenis shipper bisa memilih data yang ada di dropdown pilihan tapi jika jenis shipper b terebut tidak ada
                        di dropdown maka isilah kolom selain itu. Jika mengisi kolom selain itu maka selain itulah yang akan di simpan walau di dropdown memilih Rekanan misalnya.
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
    $('#nama_perusahaan').val('');
    $('#telepon').val('');
    $('#alamat').val('');
    $('#contact_person').val('');
    $('#email').val('');
    $("#jenis_customer1 :selected").prop('selected', false);
}
$(function(){
   $('#tabell').on('click','.edit',function(e) {
        e.preventDefault();
        $('#layoutTambah').hide();
        $('#layoutForm').show( "fast");
        $('#titleForm').html('<i class="glyphicon glyphicon-edit"></i> Mengubah shipper');
        blankInput();
        $('input[name="id"]').val($(this).data('id'));
        var rowelement = '#row'+$(this).data('id');
        $('#nama_perusahaan').val($(rowelement).data('nama_perusahaan'));
        $('#contact_person').val($(rowelement).data('contact_person'));
        $('#alamat').val($(rowelement).data('alamat'));
        $('#telepon').val($(rowelement).data('telepon'));
        $('#email').val($(rowelement).data('email'));
        $("#jenis_customer1 option[value='"+$(rowelement).data('jenis_customer')+"']").prop('selected', true);
        $("html, body").animate({ scrollTop: 0 }, "fast");
   });

   $('#tabell').on('click','.hapus',function(e) {
        e.preventDefault();
        $('#btnBatal').trigger('click');
        var iddata = $(this).data('id');
        if (confirm('Apakah shipper ini ingin di hapus?')) {
            $.ajax({
                url: '{{ URL::to('master-shipper-delete') }}/'+iddata,
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
    $('#titleForm').html('<i class="glyphicon glyphicon-plus"></i> Menambah shipper');
    blankInput();
    $("#jenis_customer1 :selected").prop('selected', false);
   });

   $('#btnBatal').click(function(){
    $('#layoutTambah').show();
    $('#layoutForm').hide( "fast");
    blankInput();
   });

   $('#fr').submit(function(e) {
        e.preventDefault();
        var msg = '';
        if ($('#nama_perusahaan').val().length < 1) {
            msg = 'Tulis nama perusahaan';
            $('#nama_perusahaan').focus();
        } else if ($('#contact_person').val().length < 1) {
            msg = 'Tulis contact person';
            $('#contact_person').focus();
        } else if ($('#alamat').val().length < 1) {
            msg = 'Tulis alamat perusahaan';
            $('#alamat').focus();
        } else if ($('#telepon').val().length < 1) {
            msg = 'Tulis telepon';
            $('#telepon').focus();
        }
        if (msg.length > 0) {
            $('#msg').show();
            $('#msg').text(msg);
        } else {
            $(this).unbind();
            $(this).submit();
        }
   });

   $('#statusChoose').change(function(e) {
    var str = '{{ URL::to('master-shipper') }}';

    if ($(this).val().length > 0) {
        str += '-'+$(this).val();
    }
    document.location = str;
});

});    
</script>
@include('layout-footer')