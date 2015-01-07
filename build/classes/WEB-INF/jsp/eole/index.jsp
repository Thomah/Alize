<%@ include file="/WEB-INF/jsp/commun/include.jsp"%>
<%@ page import=" static alize.commun.Constantes.URL_MODULE_CLE"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/plugins/datetimepicker/bootstrap-datetimepicker.css"/>" />
<style>
a {
	color: #E66D2B;
}
a:hover, a:focus {
    color: #651C00;
}
#journal-logs-scrollspy {
	height: 400px;
	overflow: auto;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Dashboard</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Génération des services
						</div>
						<div class="panel-body">
							<h2>Recherche multicritères</h2>
							<p>En fonction du réseau de transport Nau et des contraintes
								Eole, vous pouvez générer les services associés grâce au menu
								ci-après.</p>
							<p>Veuillez indiquer le temps maximum que vous souhaitez
								allouer au processus</p>
							<div class="progress">
								<div class="progress-bar progress-bar-striped active"
									role="progressbar" aria-valuenow="45" aria-valuemin="0"
									aria-valuemax="100" style="width: 45%" id="progress-bar-eole">
									<span class="sr-only">45% Complété</span>
								</div>
							</div>
							<nav id="journal-logs-bar"
								class="navbar navbar-default navbar-static" role="navigation">

							<div class="container-fluid">
								<div class="navbar-header">
									<button class="navbar-toggle collapsed"
										data-target=".bs-example-js-navbar-scrollspy"
										data-toggle="collapse" type="button">
										<span class="sr-only"> Toggle navigation </span> <span
											class="icon-bar"></span> <span class="icon-bar"></span> <span
											class="icon-bar"></span>
									</button>
									<a class="navbar-brand" href="#"> Journal de Logs </a>
								</div>
								<div
									class="collapse navbar-collapse bs-example-js-navbar-scrollspy">
									<ul class="nav navbar-nav">
										<li class=""><a href="#fat"> @fat </a></li>
										<li class="active"><a href="#mdo"> @mdo </a></li>
										<li class="dropdown"><a id="navbarDrop1"
											class="dropdown-toggle" aria-expanded="false" role="button"
											data-toggle="dropdown" href="#"> Dropdown <span
												class="caret"></span>
										</a>
											<ul class="dropdown-menu" aria-labelledby="navbarDrop1"
												role="menu">
												<li class=""><a tabindex="-1" href="#one"> one </a></li>
												<li class=""><a tabindex="-1" href="#two"> two </a></li>
												<li class="divider"></li>
												<li class=""><a tabindex="-1" href="#three"> three
												</a></li>
											</ul>
								</div>
							</div>

							</nav>
							<div id="journal-logs-scrollspy" class="scrollspy-example"
								data-offset="0" data-target="#journal-logs-bar"
								data-spy="scroll">

								<h4 id="fat"></h4>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>
								<h4 id="mdo"></h4>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>
								<h4 id="one"></h4>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>
								<h4 id="two"></h4>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>
								<h4 id="three"></h4>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>
								<p>Occaecat commodo aliqua delectus. Fap craft beer deserunt
									skateboard ea. Lomo bicycle rights adipisicing banh mi, velit
									ea sunt next level locavore single-origin coffee in magna
									veniam. High life id vinyl, echo park consequat quis aliquip
									banh mi pitchfork. Vero VHS est adipisicing. Consectetur nisi
									DIY minim messenger bag. Cred ex in, sustainable delectus
									consectetur fanny pack iphone.</p>

							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Temps accordé
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label for="finEole">Fin des calculs</label>
								<div class='input-group date' id='dureeEole'>
									<input type='text' class="form-control" name="finEole" id="finEole" /> <span
										class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span> </span>
								</div>
							</div>
							<button class="btn btn-default" onclick="lancerEole()">Calculer</button>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Résumé des paramètres
						</div>
						<div class="panel-body">
							<div class="list-group">
								<a class="list-group-item" href="#"><i
									class="fa fa-comment fa-fw"></i> Nombre de véhicules <span
									class="pull-right text-muted small"> <em> 30 </em>
								</span> </a> <a class="list-group-item" href="#"><i
									class="fa fa-comment fa-fw"></i> Nombre de véhicules <span
									class="pull-right text-muted small"> <em> 30 </em>
								</span> </a> <a class="list-group-item" href="#"><i
									class="fa fa-comment fa-fw"></i> Nombre de véhicules <span
									class="pull-right text-muted small"> <em> 30 </em>
								</span> </a> <a class="list-group-item" href="#"><i
									class="fa fa-comment fa-fw"></i> Nombre de véhicules <span
									class="pull-right text-muted small"> <em> 30 </em>
								</span> </a> <a class="list-group-item" href="#"><i
									class="fa fa-comment fa-fw"></i> Nombre de véhicules <span
									class="pull-right text-muted small"> <em> 30 </em>
								</span> </a>
							</div>
						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script
		src="<c:url value="/resources/js/plugins/moment/moment.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/datetimepicker/bootstrap-datetimepicker.js"/>"></script>
	<script type="text/javascript">
	
		$('#dureeEole').datetimepicker({
	        format: 'DD/MM/YYYY HH:mm',
	        pickSeconds: false,
	        pick12HourFormat: false            
	    });
		$('#journal-logs-scrollspy').scrollspy({
			target : '#fat'
		});
		
		function updateStatut(){
		    setTimeout(function(){
		    	$.ajax({
		    	  	url: "/alize/eole/recupererStatut",
		    	  	type: "POST",
		    	  	success: function(str) {
		    	  		document.getElementById("progress-bar-eole").setAttribute("aria-valuenow", str);
		    	  		document.getElementById("progress-bar-eole").setAttribute("style", "width: " + str + "%");
		    	  	}
		    	  });
				updateStatut();
		    }, 1000);
		}
		
		function lancerEole() {
			var finEole = document.getElementById("finEole").value;

		    $.ajax({
		    	url: "/alize/eole/calculer",
		    	data: "finEole=" + finEole,
		    	type: "POST",
		    	success: function(str) {
		    		$('#dureeEole').data("DateTimePicker").disable();
		    		updateStatut();
		    	}
		    });
		}
		
		
		
	</script>
</body>
</html>