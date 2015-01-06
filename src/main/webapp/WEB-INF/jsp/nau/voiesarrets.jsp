<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Nau</title>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Attribution des arrêts à la voie : <%=request.getParameter("voie") %></h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Arrêts disponibles
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="arretsNonAttribuesContent" class="table-responsive">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Arrêts attribués
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="arretsAttribuesContent" class="table-responsive">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
	
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script src="<c:url value="/resources/js/plugins/editablegrid/editablegrid.js"/>"></script>
	<script type="text/javascript">
	var idVoie;
	
	window.onload = function() {
		idVoie = getUrlParameter('voie');
		getArrets();
	}
	
	function getArrets() {
		getArretsNonAttribues();
		getArretsAttribues();
	}
	
	function getArretsNonAttribues() {
	    $.ajax({
	    	url: "/alize/nau/voiesarrets/get/nonattribues",
	    	data: "idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "statut", label: "Statut", datatype: "string", editable: false});
   		     	metadata.push({name: "nom", label: "Nom", datatype: "string", editable: false});
   		     	metadata.push({name: "estCommercial", label: "Commercial", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estLieuEchangeConducteur", label: "Echange Conducteur", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estEntreeDepot", label: "Entrée Dépôt", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estSortieDepot", label: "Sortie Dépôt", datatype: "boolean", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var arrets = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < arrets.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: arrets[index].id, 
	    		    	 values: {
	    		    		 "id": arrets[index].id, 
	    		    		 "statut": arrets[index].statut, 
	    		    		 "nom": arrets[index].nom, 
	    		    		 "estCommercial": arrets[index].estCommercial, 
	    		    		 "estLieuEchangeConducteur": arrets[index].estLieuEchangeConducteur, 
	    		    		 "estEntreeDepot": arrets[index].estEntreeDepot, 
	    		    		 "estSortieDepot": arrets[index].estSortieDepot, 
	    		    		 "":"<a href='#' onclick='ajouterArret(" + arrets[index].id + ")'><span class='glyphicon glyphicon-plus-sign' aria-label='Ajouter'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridArretsNonAttribues");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("arretsNonAttribuesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function getArretsAttribues() {
	    $.ajax({
	    	url: "/alize/nau/voiesarrets/get/attribues",
	    	data: "idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "statut", label: "Statut", datatype: "string", editable: false});
   		     	metadata.push({name: "nom", label: "Nom", datatype: "string", editable: false});
   		     	metadata.push({name: "estCommercial", label: "Commercial", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estLieuEchangeConducteur", label: "Echange Conducteur", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estEntreeDepot", label: "Entrée Dépôt", datatype: "boolean", editable: false});
   		     	metadata.push({name: "estSortieDepot", label: "Sortie Dépôt", datatype: "boolean", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var arrets = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < arrets.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: arrets[index].id, 
	    		    	 values: {
	    		    		 "id": arrets[index].id, 
	    		    		 "statut": arrets[index].statut, 
	    		    		 "nom": arrets[index].nom, 
	    		    		 "estCommercial": arrets[index].estCommercial, 
	    		    		 "estLieuEchangeConducteur": arrets[index].estLieuEchangeConducteur, 
	    		    		 "estEntreeDepot": arrets[index].estEntreeDepot, 
	    		    		 "estSortieDepot": arrets[index].estSortieDepot, 
	    		    		 "":"<a href='#' onclick='supprimerArret(" + arrets[index].id + ")'><span class='glyphicon glyphicon-minus-sign' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridArretsAttribues");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("arretsAttribuesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function ajouterArret(id) {
	    $.ajax({
	    	url: "/alize/nau/voiesarrets/ajouter",
	    	data: "idArret=" + id + 
    		"&idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
	    		getArrets();
	    	}
	    });
	}

	function supprimerArret(id) {
	    $.ajax({
	    	url: "/alize/nau/voiesarrets/supprimer",
	    	data: "idArret=" + id + 
    		"&idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
	    		getArrets();
	    	}
	    });
	}
	
	function getUrlParameter(sParam) {
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		for(var i = 0; i < sURLVariables.length; i++) {
			var sParameterName = sURLVariables[i].split('=');
			if(sParameterName[0] == sParam) {
				return sParameterName[1];
			}
		}
	}
	
	</script>
	
</body>
</html>