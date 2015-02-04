<%@ include file="/WEB-INF/jsp/commun/include.jsp"%>
<%@ page import=" static alize.commun.Constantes.URL_MODULE_CLE"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<style>
a {
	color: #E66D2B;
}
a:hover, a:focus {
    color: #651C00;
}
.progress-bar {
    background-color: #E66D2B;
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
					<h1 class="page-header">Bienvenue</h1>
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
							<h2>Calculs</h2>
							<p>En fonction du réseau de transport Nau et des contraintes
								Eole, vous pouvez générer les services associés grâce au menu
								ci-après.</p>
							<p>Veuillez indiquer le nombre d'itérations que le programme doit parcourir.</p>
							<div class="progress">
								<div class="progress-bar progress-bar-striped active"
									role="progressbar" aria-valuenow="0" aria-valuemin="0"
									aria-valuemax="100" style="width: 0%" id="progress-bar-eole">
									<span class="sr-only">0% Complété</span>
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
									<a class="navbar-brand" href="#"> Journal</a>
								</div>
							</div>

							</nav>
							<div id="journal-logs-scrollspy" class="scrollspy-example"
								data-offset="0" data-target="#journal-logs-bar"
								data-spy="scroll">
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Paramètre
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label for="finEole">Nombre d'itérations</label>
								<div class='input-group' id='dureeEole'>
									<input type='text' class="form-control" name="finEole" id="finEole" />
								</div>
							</div>
							<button id="lancerEole" class="btn btn-default" onclick="lancerEole()">Calculer</button>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- /#wrapper -->

	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script
		src="<c:url value="/resources/js/plugins/moment/moment.js"/>"></script>
	<script type="text/javascript">
		var socket;
		connect();
		
		function disconnect() {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		}
		
		function connect() {
			
			if ('WebSocket' in window){
				socket = new WebSocket('ws://192.168.4.10:8080/alize/websocket');
				
				socket.onopen = function() {
					// do something
				};

				socket.onmessage = function(event) {
					var received_msg = event.data;
					var data = jQuery.parseJSON(received_msg);
										
    				$('#journal-logs-scrollspy').html(data.journal);
    				$('#journal-logs-scrollspy').scrollTop(document.getElementById('journal-logs-scrollspy').scrollHeight);

	    	  		document.getElementById("progress-bar-eole").setAttribute("aria-valuenow", data.avancement);
	    	  		document.getElementById("progress-bar-eole").setAttribute("style", "width: " + data.avancement + "%");
	    	  		
					// A changer pour une requete AJAX
					$('#lancerEole').attr("disabled", "disabled");
				};

				socket.onclose = function(event) {
					// do something
				};
				
    		} else {
    		  console.log('Websocket not supported');
    		}
			
		}
		
		function lancerEole() {

			var finEole = document.getElementById("finEole").value;
			waitForSocketConnection(function(){
				var msg = '{"finEole":"' + finEole + '"}';
				socket.send(msg);
		    });
			
			function waitForSocketConnection(callback){
			    setTimeout(
			        function () {
			            if (socket.readyState === 1) {
			                if(callback != null){
			                    callback();
			                }
			                return;

			            } else {
			                waitForSocketConnection(callback);
			            }
			        }, 5);
			}
		}
		
	</script>
</body>
</html>