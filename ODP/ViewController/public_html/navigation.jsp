<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
		<title></title>
		<meta charset="utf-8" /> 
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" /> 
		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" /> 
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
	<script type="text/javascript" src="assets/js/jquery-1.9.1.min.js"></script>

		<!--inline styles if any-->
	</head>

	<body>
		<div class="navbar navbar-inverse">
			<div class="navbar-inner">
				<div class="container-fluid"> 

					<ul class="nav ace-nav pull-right"> 		 
						<li class="light-blue user-profile">
							<a data-toggle="dropdown" href="#" class="user-menu dropdown-toggle">						 
								<span id="user_info">
									<small>你好啊,</small>
									管理员大人
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer" id="user_menu"> 

								<li>
									<a href="#">
										<i class="icon-off"></i>
										Logout
									</a>
								</li>
							</ul>
						</li>
					</ul><!--/.ace-nav-->
				</div><!--/.container-fluid-->
			</div><!--/.navbar-inner-->
		</div>
        <!-- 这里是头部结束位置-->

		<div class="container-fluid" id="main-container">
			<a id="menu-toggler" href="#">
				<span></span>
			</a>
 
				<div id="sidebar-collapse">
					<i class="icon-double-angle-left"></i>
				</div>
			</div>