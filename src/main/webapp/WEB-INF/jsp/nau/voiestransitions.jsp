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
					<h1 class="page-header">Attribution des transitions à la voie : <%=request.getParameter("voie") %></h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Transitions disponibles
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="transitionsNonAttribuesContent" class="table-responsive">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Transitions attribuées
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="transitionsAttribuesContent" class="table-responsive">
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
		get();
	}
	
	function get() {
		getNonAttribues();
		getAttribues();
	}
	
	function getNonAttribues() {
	    $.ajax({
	    	url: "/alize/nau/voiestransitions/get/nonattribuees",
	    	data: "idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "duree", label: "Durée", datatype: "string", editable: false});
   		     	metadata.push({name: "arrets", label: "Arrêts", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var transitions = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < transitions.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: transitions[index].id, 
	    		    	 values: {
	    		    		 "id": transitions[index].id, 
	    		    		 "duree": transitions[index].duree, 
	    		    		 "arrets": transitions[index].arrets, 
	    		    		 "":"<a href='#' onclick='ajouter(" + transitions[index].id + ")'><span class='glyphicon glyphicon-plus-sign' aria-label='Ajouter'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridTransitionsNonAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("transitionsNonAttribuesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function getAttribues() {
	    $.ajax({
	    	url: "/alize/nau/voiestransitions/get/attribuees",
	    	data: "idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "duree", label: "Durée", datatype: "string", editable: false});
   		     	metadata.push({name: "arrets", label: "Arrêts", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var transitions = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < transitions.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: transitions[index].id, 
	    		    	 values: {
	    		    		 "id": transitions[index].id, 
	    		    		 "duree": transitions[index].duree, 
	    		    		 "arrets": transitions[index].arrets, 
	    		    		 "":"<a href='#' onclick='supprimer(" + transitions[index].id + ")'><span class='glyphicon glyphicon-minus-sign' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridTransitionsAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("transitionsAttribuesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function ajouter(id) {
	    $.ajax({
	    	url: "/alize/nau/voiestransitions/ajouter",
	    	data: "idTransition=" + id + 
    		"&idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
	    		get();
	    	}
	    });
	}

	function supprimer(id) {
	    $.ajax({
	    	url: "/alize/nau/voiestransitions/supprimer",
	    	data: "idTransition=" + id + 
    		"&idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
	    		get();
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