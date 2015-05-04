
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
    <link id="bs-css" href="{{ URL::to("public/komponenku/charisma") }}/css/bootstrap-united.min.css" rel="stylesheet">

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

    <!-- jQuery -->
    <script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/jquery/jquery.min.js"></script>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="{{ URL::to("public/komponenku/charisma") }}/http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="{{ URL::to("public/komponenku/charisma") }}/img/favicon.ico">

</head>

<body>
<div class="ch-container">
    <div class="row" id="content">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>Welcome</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            <div class="alert alert-info" id="alertt" style='display:none'>
                Please login with your Username and Password.
            </div>
            <form class="form-horizontal" id="fr" method="post">
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                        <input type="text" class="form-control" name="usernamee" placeholder="Username">
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input type="password" class="form-control" name="passwordd" placeholder="Password">
                        <input name="_token" type="hidden" value="{!! csrf_token() !!}" />
                    </div>
                    <div class="clearfix"></div>
                    
                    <div class="input-group input-group-lg">
                    <br/>
                    	<div class="row">
	                        <div class="col-md-3"><label style="margin:10px 0 0 0">Divisi : </label></div>
	                        <div class="col-md-4"><div class="controls">
	                        	<select style="width:300px;height:30px;cursor:pointer" name="divisi">
		                            @foreach ($divisis as $divisi)
		                            	<option value="{{ $divisi->divisi }}">{{ $divisi->divisi }}</option>
		                            @endforeach
		                        </select>
		                    </div></div>
	                    </div>
                    </div>
                    
                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->

<!-- external javascript -->

<script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- library for cookie management -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src='{{ URL::to("public/komponenku/charisma") }}/bower_components/moment/min/moment.min.js'></script>
<script src='{{ URL::to("public/komponenku/charisma") }}/bower_components/fullcalendar/dist/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='{{ URL::to("public/komponenku/charisma") }}/js/jquery.dataTables.min.js'></script>

<!-- select or dropdown enhancer -->
<script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="{{ URL::to("public/komponenku/charisma") }}/js/charisma.js"></script>

<script type="text/javascript">
	$('#fr').submit(function(e){
		e.preventDefault();
        $('#loading').remove();
            $('#content').fadeOut().parent().prepend('<div id="loading" class="center"><div class="center"></div></div>');
		$.ajax({
            method:'post',
            url: '{{ URL::to('postlogin') }}',
            data: $(this).serialize()
		}).done(function(result){
            if (result.success == 1) {
                location = '{{ URL::to('dashboard') }}';
            } else {
                $('#loading').remove();
                $('#content').fadeIn();
                $('#alertt').show();
                $('#alertt').text(result.msg);
            }
            });
	});
</script>

</body>
</html>
