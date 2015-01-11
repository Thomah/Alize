<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
<style>
a {
	color: #E66D2B;
}
a:hover, a:focus {
    color: #651C00;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Vacations pour le service : <%=request.getParameter("service") %></h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Plannification
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterVacation()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="vacationsContent" class="table-responsive">
	                            </div>
	                        </div>
	                        <!-- /.panel-body -->
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
	<script src="<c:url value="/resources/js/plugins/editablegrid/editablegrid.js"/>"></script>
	<script type="text/javascript">
	var idService;
	
	window.onload = function() {
		idService = getUrlParameter('service');
		getVacations();
	}
	
	function getVacations() {
	    $.ajax({
	    	url: "/alize/eole/vacations/getByService",
	    	type: "POST",
	    	data: "idService=" + idService,
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "heureDebut", label: "Heure de début", datatype: "string", editable: true});
   		     	metadata.push({name: "heureFin", label: "Heure de fin", datatype: "string", editable: true});
   		     	metadata.push({name: "arretEchangeConducteurDebut", label: "Arrêt Début", datatype: "html", editable: false});
   		     	metadata.push({name: "arretEchangeConducteurFin", label: "Arrêt Fin", datatype: "html", editable: false});
   		     	metadata.push({name: "vehicule", label: "Véhicule", datatype: "html", editable: false});
   		     	metadata.push({name: "service", label: "Service", datatype: "html", editable: false});
   		     	metadata.push({name: "supprimer", label: "Supprimer", datatype: "html", editable: false});
   		     	
				var data = [];
	    		var vacations = jQuery.parseJSON( str );
   		    	var index;
   		    	var optionsSelectArret = getOptionsSelectArret();
   		    	var optionsSelectVehicule = getOptionsSelectVehicule();
   		    	var optionsSelectService = getOptionsSelectService();
	    		for(index = 0; index < vacations.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: vacations[index].id, 
	    		    	 values: {
	    		    		 "id": vacations[index].id, 
	    		    		 "heureDebut": vacations[index].heureDebut, 
	    		    		 "heureFin": vacations[index].heureFin, 
	    		    		 "arretEchangeConducteurDebut": "<div class='form-group'><select class='form-control selectArret' id='arretEchangeConducteurDebut_" + vacations[index].id + "' onchange='updateArret(" + vacations[index].id + ", \"Debut\")' data-selected='" + vacations[index].arretEchangeConducteurDebut_id + "'>" + optionsSelectArret + "</select></div>", 
	    		    		 "arretEchangeConducteurFin": "<div class='form-group'><select class='form-control' id='arretEchangeConducteurFin_" + vacations[index].id + "' onchange='updateArret(" + vacations[index].id + ", \"Fin\")' data-selected='" + vacations[index].arretEchangeConducteurFin_id + "'>" + optionsSelectArret + "</select></div>",
	    		    		 "vehicule": "<div class='form-group'><select class='form-control selectVehicule' id='vehicule_" + vacations[index].id + "' onchange='updateVehicule(" + vacations[index].id + ")' data-selected='" + vacations[index].vehicule_id + "'>" + optionsSelectVehicule + "</select></div>",
	    		    		 "service": "<div class='form-group'><select class='form-control selectService' id='service_" + vacations[index].id + "' onchange='updateService(" + vacations[index].id + ")' data-selected='" + vacations[index].service_id + "'>" + optionsSelectService + "</select></div>",
	    		    		 "supprimer":"<a href='#' onclick='supprimerVacation(" + vacations[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridVacations", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("vacationsContent", "table table-bordered table-hover table-striped");
	    		
	    		selectArrets();
	    		selectVehicule();
	    		selectService();
	    	}
	    });
	}

	function getOptionsSelectArret() {
	    var chaineOptions = "<option value='0'></option>";
		$.ajax({
	    	url: "/alize/nau/arrets/getEchangesConducteurs",
	    	type: "POST",
	    	async: false,
	    	success: function(str) {
	    		var data = [];
	    		var arrets = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < arrets.length; ++index)
	    		{
	    			chaineOptions+= "<option value='" + arrets[index].id + "'>" + arrets[index].nom + "</option>";
	    		}
	    	}
	    });
		return chaineOptions;
	}
	
	function getOptionsSelectVehicule() {
	    var chaineOptions = "";
		$.ajax({
	    	url: "/alize/eole/vehicules/get",
	    	type: "POST",
	    	async: false,
	    	success: function(str) {
	    		var data = [];
	    		var vehicules = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < vehicules.length; ++index)
	    		{
	    			chaineOptions+= "<option value='" + vehicules[index].id + "'>" + vehicules[index].type + " " + vehicules[index].id + "</option>";
	    		}
	    	}
	    });
		return chaineOptions;
	}
	
	function getOptionsSelectService() {
	    var chaineOptions = "";
		$.ajax({
	    	url: "/alize/eole/services/get",
	    	type: "POST",
	    	async: false,
	    	success: function(str) {
	    		var data = [];
	    		var services = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < services.length; ++index)
	    		{
	    			chaineOptions+= "<option value='" + services[index].id + "'>" + "Service " + services[index].id + "</option>";
	    		}
	    	}
	    });
		return chaineOptions;
	}
	
	function selectArrets() {
		$('.selectArret').each(function(i, obj) {
			var id = $(obj).attr('id').substring(28);
			var arretSelectionne = $(obj).attr("data-selected");
    		$(obj).children("[value=" + arretSelectionne + "]").attr("selected", true);

    		var selectArretSuivant = $("#arretEchangeConducteurFin_" + id);
    		arretSelectionne = selectArretSuivant.attr("data-selected");
    		selectArretSuivant.children("[value=" + arretSelectionne + "]").attr("selected", true);
		});
	}

	function selectVehicule() {
		$('.selectVehicule').each(function(i, obj) {
			var vehiculeSelectionne = $(obj).attr("data-selected");
    		$(obj).children("[value=" + vehiculeSelectionne + "]").attr("selected", true);
		});
	}

	function selectService() {
		$('.selectService').each(function(i, obj) {
    		$(obj).children("[value=" + idService + "]").attr("selected", true);
		});
	}
	
	function ajouterVacation() {
	    $.ajax({
	    	url: "/alize/eole/vacations/ajouterParService",
	    	type: "POST",
	    	data: "idService=" + idService,
	    	success: function(str) {
	    		getVacations();
	    	}
	    });
	}
	
	function supprimerVacation(id) {

	    $.ajax({
	    	url: "/alize/eole/vacations/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getVacations();
	    	}
	    });
	}
	
	function updateArret(idVacation, typeArret) {
		var colname = "arretEchangeConducteur" + typeArret;
		var newvalue = $('#arretEchangeConducteur' + typeArret + '_' + idVacation).val();
		
		$.ajax({
			url: '/alize/eole/vacations/update',
			type: 'POST',
	    	data: "id=" + idVacation + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				// reset old value if failed then highlight row
				var success = response == "ok" || !isNaN(parseInt(response)); // by default, a sucessfull reponse can be "ok" or a database id
			    highlight(idService, success ? "ok" : "error"); 
			},
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure"); },
			async: true
		});
	}
	
	function updateVehicule(idVacation) {
		var colname = "vehicule";
		var newvalue = $('#vehicule_' + idVacation).val();
		
		$.ajax({
			url: '/alize/eole/vacations/update',
			type: 'POST',
	    	data: "id=" + idVacation + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				// reset old value if failed then highlight row
				var success = response == "ok" || !isNaN(parseInt(response)); // by default, a sucessfull reponse can be "ok" or a database id
			    highlight(idService, success ? "ok" : "error"); 
			},
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure"); },
			async: true
		});
	}
	
	function updateService(idVacation) {
		var colname = "service";
		var newvalue = $('#service_' + idVacation).val();
		
		$.ajax({
			url: '/alize/eole/vacations/update',
			type: 'POST',
	    	data: "id=" + idVacation + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				getVacations();
			},
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure"); },
			async: true
		});
	}
	
	function updateCellValue(editableGrid, rowIndex, columnIndex, oldValue, newValue, row, onResponse)
	{
		var id = editableGrid.getRowId(rowIndex);
		var newvalue = editableGrid.getColumnType(columnIndex) == "boolean" ? (newValue ? 1 : 0) : newValue;
		var colname = editableGrid.getColumnName(columnIndex);
		
		$.ajax({
			url: '/alize/eole/vacations/update',
			type: 'POST',
	    	data: "id=" + id + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				// reset old value if failed then highlight row
				var success = onResponse ? onResponse(response) : (response == "ok" || !isNaN(parseInt(response))); // by default, a sucessfull reponse can be "ok" or a database id 
				if (!success) editableGrid.setValueAt(rowIndex, columnIndex, oldValue);
			    highlight(row.id, success ? "ok" : "error"); 
			},
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure\n" + errortext); },
			async: true
		});
	}
	
	function highlightRow(rowId, bgColor, after)
	{
		var rowSelector = $("#" + rowId);
		rowSelector.css("background-color", bgColor);
		rowSelector.fadeTo("normal", 0.5, function() { 
			rowSelector.fadeTo("fast", 1, function() { 
				rowSelector.css("background-color", '');
			});
		});
	}

	function highlight(div_id, style) {
		highlightRow(div_id, style == "error" ? "#e5afaf" : style == "warning" ? "#ffcc00" : "#8dc70a");
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