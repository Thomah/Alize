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
					<h1 class="page-header">Attribution des voies à la ligne : <%=request.getParameter("ligne") %></h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Voies disponibles
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="voiesNonAttribueesContent" class="table-responsive">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Voies attribuées
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="voiesAttribueesContent" class="table-responsive">
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
	var idLigne;
	
	window.onload = function() {
		idLigne = getUrlParameter('ligne');
		getVoies();
	}
	
	function getVoies() {
		getVoiesNonAttribuees();
		getVoiesAttribuees();
	}
	
	function getVoiesNonAttribuees() {
	    $.ajax({
	    	url: "/alize/nau/lignesvoies/get/nonattribuees",
	    	data: "idLigne=" + idLigne,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "direction", label: "Direction", datatype: "string", editable: false});
   		     	metadata.push({name: "terminus", label: "Terminus", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var voies = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < voies.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: voies[index].id, 
	    		    	 values: {
	    		    		 "id": voies[index].id, 
	    		    		 "direction": voies[index].direction, 
	    		    		 "terminus": voies[index].terminus, 
	    		    		 "":"<a href='#' onclick='ajouterVoie(" + voies[index].id + ")'><span class='glyphicon glyphicon-plus-sign' aria-label='Ajouter'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridVoiesNonAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("voiesNonAttribueesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function getVoiesAttribuees() {
	    $.ajax({
	    	url: "/alize/nau/lignesvoies/get/attribuees",
	    	data: "idLigne=" + idLigne,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "direction", label: "Direction", datatype: "string", editable: false});
   		     	metadata.push({name: "terminus", label: "Terminus", datatype: "string", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var voies = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < voies.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: voies[index].id, 
	    		    	 values: {
	    		    		 "id": voies[index].id, 
	    		    		 "direction": voies[index].direction, 
	    		    		 "terminus": voies[index].terminus, 
	    		    		 "":"<a href='#' onclick='supprimerVoie(" + voies[index].id + ")'><span class='glyphicon glyphicon-minus-sign' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridVoiesAttribuees");
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("voiesAttribueesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function ajouterVoie(id) {
	    $.ajax({
	    	url: "/alize/nau/lignesvoies/ajouter",
	    	data: "id=" + id + 
    		"&idLigne=" + idLigne,
	    	type: "POST",
	    	success: function(str) {
	    		getVoies();
	    	}
	    });
	}

	function supprimerVoie(id) {
	    $.ajax({
	    	url: "/alize/nau/lignesvoies/supprimer",
	    	data: "id=" + id + 
    		"&idLigne=" + idLigne,
	    	type: "POST",
	    	success: function(str) {
	    		getVoies();
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