@include('layout-header')
@include('layout-menu')
<div class="row">

<div class="box col-md-3">
<a href="{{ URL::to('dokumen-joborder-kosong') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Job order tidak valid</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $joborderKosongCount }}</h3></div>
            </div>
        </div>
    </div>
    </a>
</div>

<div class="box col-md-3">
<a href="{{ URL::to('dokumen-joborder-sebagian') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Job order valid sebagian</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $joborderSebagianCount }}</h3></div>
            </div>
        </div>
    </div>
</a>
</div>

<div class="box col-md-2">
<a href="{{ URL::to('dokumen-joborder-lengkap') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Job order valid</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $joborderLengkapCount }}</h3></div>
            </div>
        </div>
    </div>
</a>
</div>

<div class="box col-md-2">
<a href="{{ URL::to('dokumen-joborder') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Job order</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $joborderCount }}</h3></div>
            </div>
        </div>
    </div>
</a>
</div>

</div>
<div class="row">



<div class="box col-md-2">
<a href="{{ URL::to('master-pegawai') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Pegawai</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $pegawaiCount }}</h3></div>
            </div>
        </div>
    </div>
</a>
</div>

<div class="box col-md-2">
<a href="{{ URL::to('master-customer') }}">
    <div class="box-inner">
        <div class="box-header well" data-original-title="">
            <h2>Customer</h2>
        </div>
        <div class="box-content">
            <div class="row">
                <div class="col-md-12" style="text-align:center;"><h3>{{ $customerCount }}</h3></div>
            </div>
        </div>
    </div>
</a>
</div>

</div>
@include('layout-footer')