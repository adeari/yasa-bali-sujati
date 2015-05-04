<div class="ch-container">
    <div class="row">
        <!-- left menu starts -->
        <div class="col-sm-2 col-lg-2">
            <div class="sidebar-nav">
                <div class="nav-canvas">
                    <div class="nav-sm nav nav-stacked"></div>
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header" style="text-align:center;"> <h3>Menu</h3> </li>
                        <li class="accordion">
                            <a href="#"><i class="glyphicon glyphicon-plus"></i><span> Master</span></a>
                            <ul class="nav nav-pills nav-stacked" style="display: block;">
                                <li><a class="ajax-link" href="{{ URL::to('master-user') }}">User Management</a></li>
                                <li><a  class="ajax-link" href="{{ URL::to('master-pegawai') }}">Pegawai</a></li>
                                <li><a  class="ajax-link" href="{{ URL::to('master-customer') }}">Customer</a></li>
                                <li><a  class="ajax-link" href="{{ URL::to('master-filling') }}">Aturan Filling</a></li>
                                <li><a  class="ajax-link" href="{{ URL::to('master-validasi') }}">Aturan validasi</a></li>
                            </ul>
                        </li>
                        <li class="accordion">
                            <a href="#"><i class="glyphicon glyphicon-plus"></i><span> Dokumen</span></a>
                            <ul class="nav nav-pills nav-stacked" style="display: block;">
                                <li><a class="ajax-link" href="{{ URL::to('dokumen-joborder') }}">Job order</a></li>
                            </ul>
                        </li>                        
                    </ul>
                    <label id="for-is-ajax" for="is-ajax"><input id="is-ajax" type="checkbox"> Lebih cepat</label>
                </div>
            </div>
        </div>
        <!--/span-->
        <!-- left menu ends -->
        <div id="content" class="col-lg-10 col-sm-10">
