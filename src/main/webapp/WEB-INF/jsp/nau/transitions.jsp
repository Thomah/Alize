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
					<h1 class="page-header">Transitions</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Composition du réseau
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterVoie()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="transitionsContent" class="table-responsive">
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
	window.onload = function() {
		getTransitions();
	}
	
	function getTransitions() {
	    $.ajax({
	    	url: "/alize/nau/transitions/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "duree", label: "Durée", datatype: "string", editable: true});
   		     	metadata.push({name: "arretPrecedent", label: "Arrêt précédent", datatype: "html", editable: false});
   		     	metadata.push({name: "arretSuivant", label: "Arrêt suivant", datatype: "html", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var transitions = jQuery.parseJSON( str );
   		    	var index;
   		    	var optionsSelectArret = getOptionsSelectArret();
	    		for(index = 0; index < transitions.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: transitions[index].id, 
	    		    	 values: {
	    		    		 "id": transitions[index].id, 
	    		    		 "duree": transitions[index].duree, 
	    		    		 "arretPrecedent": "<div class='form-group'><select class='form-control selectArret' id='arretPrecedent_" + transitions[index].id + "' onchange='updateArret(" + transitions[index].id + ", \"Precedent\")' data-selected='" + transitions[index].arretPrecedent_id + "'>" + optionsSelectArret + "</select></div>", 
	    		    		 "arretSuivant": "<div class='form-group'><select class='form-control' id='arretSuivant_" + transitions[index].id + "' onchange='updateArret(" + transitions[index].id + ", \"Suivant\")' data-selected='" + transitions[index].arretSuivant_id + "'>" + optionsSelectArret + "</select></div>", 
	    		    		 "":"<a href='#' onclick='supprimerVoie(" + transitions[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridTransitions", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("transitionsContent", "table table-bordered table-hover table-striped");
	    		
	    		selectArrets();
	    	}
	    });
	}
	
	function getOptionsSelectArret() {
	    var chaineOptions = "";
		$.ajax({
	    	url: "/alize/nau/arrets/get",
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
	
	function selectArrets() {
		$('.selectArret').each(function(i, obj) {
			var id = $(obj).attr('id').substring(15);
			var arretSelectionne = $(obj).attr("data-selected");
    		$(obj).children("[value=" + arretSelectionne + "]").attr("selected", true);

    		var selectArretSuivant = $("#arretSuivant_" + id);
    		arretSelectionne = selectArretSuivant.attr("data-selected");
    		selectArretSuivant.children("[value=" + arretSelectionne + "]").attr("selected", true);
		});
	}
	
	function ajouterVoie() {
	    $.ajax({
	    	url: "/alize/nau/transitions/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		getTransitions();
	    	}
	    });
	}
	
	function supprimerVoie(id) {
	    $.ajax({
	    	url: "/alize/nau/transitions/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getTransitions();
	    	}
	    });
	}
	
	function updateArret(idTransition, typeArret) {
		var colname = "arret" + typeArret + "_id";
		var newvalue = $('#arret' + typeArret + "_" + idTransition).val();
		
		$.ajax({
			url: '/alize/nau/transitions/update',
			type: 'POST',
	    	data: "id=" + idTransition + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				// reset old value if failed then highlight row
				var success = response == "ok" || !isNaN(parseInt(response)); // by default, a sucessfull reponse can be "ok" or a database id
			    highlight(idVoie, success ? "ok" : "error"); 
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
		var coltype = editableGrid.getColumnType(columnIndex);
		
		$.ajax({
			url: '/alize/nau/transitions/update',
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
	
	</script>
	
</body>
</html>