<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
</head>
<style>
a {
	color: #E66D2B;
}
a:hover, a:focus {
    color: #651C00;
}
</style>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Attribution des périodicités à la feuille de service : <%=request.getParameter("fds") %></h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Périodicités disponibles
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="periodicitesNonAttribueesContent" class="table-responsive">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Périodicités attribuées
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="periodicitesAttribueesContent" class="table-responsive">
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
	var idFDS;
	
	window.onload = function() {
		idFDS = getUrlParameter('fds');
		getPeriodicites();
	}
	
	function getPeriodicites() {
		getPeriodicitesNonAttribuees();
		getPeriodicitesAttribuees();
	}
	
	function getPeriodicitesNonAttribuees() {
	    $.ajax({
	    	url: "/alize/eole/fdsperiodicites/get/nonattribuees",
	    	data: "idFDS=" + idFDS,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "voie", label: "Voie", datatype: "string", editable: false});
   		     	metadata.push({name: "arret", label: "Arret", datatype: "string", editable: false})
   		     	metadata.push({name: "debut", label: "Heure de début", datatype: "string", editable: false});
   		     	metadata.push({name: "fin", label: "Heure de fin", datatype: "string", editable: false});
   		     	metadata.push({name: "periode", label: "Temps entre deux véhicules", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var periodicites = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < periodicites.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: periodicites[index].id, 
	    		    	 values: {
	    		    		 "id": periodicites[index].id, 
	    		    		 "voie": periodicites[index].voie, 
	    		    		 "arret": periodicites[index].arret, 
	    		    		 "debut": periodicites[index].debut, 
	    		    		 "fin": periodicites[index].fin, 
	    		    		 "periode": periodicites[index].periode, 
	    		    		 "":"<a href='#' onclick='ajouterPeriodicite(" + periodicites[index].id + ")'><span class='glyphicon glyphicon-plus-sign' aria-label='Ajouter'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridPeriodicitesNonAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("periodicitesNonAttribueesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function getPeriodicitesAttribuees() {
	    $.ajax({
	    	url: "/alize/eole/fdsperiodicites/get/attribuees",
	    	data: "idFDS=" + idFDS,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "voie", label: "Voie", datatype: "string", editable: false});
   		     	metadata.push({name: "arret", label: "Arret", datatype: "string", editable: false})
   		     	metadata.push({name: "debut", label: "Heure de début", datatype: "string", editable: false});
   		     	metadata.push({name: "fin", label: "Heure de fin", datatype: "string", editable: false});
   		     	metadata.push({name: "periode", label: "Temps entre deux véhicules", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var periodicites = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < periodicites.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: periodicites[index].id, 
	    		    	 values: {
	    		    		 "id": periodicites[index].id, 
	    		    		 "voie": periodicites[index].voie, 
	    		    		 "arret": periodicites[index].arret, 
	    		    		 "debut": periodicites[index].debut, 
	    		    		 "fin": periodicites[index].fin, 
	    		    		 "periode": periodicites[index].periode, 
	    		    		 "":"<a href='#' onclick='supprimerPeriodicite(" + periodicites[index].id + ")'><span class='glyphicon glyphicon-minus-sign' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridPeriodicitesAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("periodicitesAttribueesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function ajouterPeriodicite(idPeriodicite) {
	    $.ajax({
	    	url: "/alize/eole/fdsperiodicites/ajouter",
	    	data: "idPeriodicite=" + idPeriodicite + 
    		"&idFDS=" + idFDS,
	    	type: "POST",
	    	success: function(str) {
	    		getPeriodicites();
	    	}
	    });
	}

	function supprimerPeriodicite(idPeriodicite) {
	    $.ajax({
	    	url: "/alize/eole/fdsperiodicites/supprimer",
	    	data: "idPeriodicite=" + idPeriodicite + 
    		"&idFDS=" + idFDS,
	    	type: "POST",
	    	success: function(str) {
	    		getPeriodicites();
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