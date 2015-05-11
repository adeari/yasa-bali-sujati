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

    <!-- jQuery -->
    <script src="{{ URL::to("public/komponenku/charisma") }}/bower_components/jquery/jquery.min.js"></script>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="{{ URL::to("public/komponenku/charisma") }}/img/favicon.ico">

</head>
<body>
    <!-- topbar starts -->
    <div class="navbar navbar-default" role="navigation">

        <div class="navbar-inner">
            <button type="button" class="navbar-toggle pull-left animated flip">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="dashboard"> <img alt="Charisma Logo" src="{{ URL::to("public/komponenku/charisma") }}/img/logo20.png" class="hidden-xs"/>
                <span>PT. YASA BALI SUJATI</span></a>

            <!-- user dropdown starts -->
            <div class="btn-group pull-right">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs">&nbsp; {{ Auth::user()->name }} &nbsp;</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#">Ganti Password</a></li>
                    <li class="divider"></li>
                    <li><a href="{{ URL::to("logout") }}">Logout</a></li>
                </ul>
            </div>
            <!-- user dropdown ends -->

            <!-- theme selector starts -->
            <div class="btn-group pull-right theme-container animated tada">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-tint"></i><span
                        class="hidden-sm hidden-xs">&nbsp; Ganti warna &nbsp;</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" id="themes">
                    <li><a data-value="classic" href="#"><i class="whitespace"></i> Classic</a></li>
                    <li><a data-value="cerulean" href="#"><i class="whitespace"></i> Cerulean</a></li>
                    <li><a data-value="cyborg" href="#"><i class="whitespace"></i> Cyborg</a></li>
                    <li><a data-value="simplex" href="#"><i class="whitespace"></i> Simplex</a></li>
                    <li><a data-value="darkly" href="#"><i class="whitespace"></i> Darkly</a></li>
                    <li><a data-value="lumen" href="#"><i class="whitespace"></i> Lumen</a></li>
                    <li><a data-value="slate" href="#"><i class="whitespace"></i> Slate</a></li>
                    <li><a data-value="spacelab" href="#"><i class="whitespace"></i> Spacelab</a></li>
                    <li><a data-value="united" href="#"><i class="whitespace"></i> United</a></li>
                </ul>
            </div>
            <!-- theme selector ends -->
        </div>
    </div>
    <!-- topbar ends -->