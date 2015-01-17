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
					<h1 class="page-header">Voies</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Composition du réseau
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterVoie()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="voiesContent" class="table-responsive">
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
		getVoies();
	}
	
	function getVoies() {
	    $.ajax({
	    	url: "/alize/nau/voies/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "direction", label: "Direction", datatype: "string", editable: true});
   		     	metadata.push({name: "terminusDepart", label: "Terminus Départ", datatype: "html", editable: false});
   		     	metadata.push({name: "terminusArrivee", label: "Terminus Arrivée", datatype: "html", editable: false});
   		     	metadata.push({name: "estCommerciale", label: "Commerciale", datatype: "boolean", editable: true});
   		     	metadata.push({name: "affecterTransitions", label: "Affecter les arrets", datatype: "html", editable: false});
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
	    		    		 "terminusDepart": "<div class='form-group'><select class='form-control selectTerminus' id='terminusDepart_" + voies[index].id + "' onchange='updateTerminus(" + voies[index].id + ", \"Depart\")' data-selected='" + voies[index].terminusDepart_id + "'></select></div>", 
	    		    		 "terminusArrivee": "<div class='form-group'><select class='form-control' id='terminusArrivee_" + voies[index].id + "' onchange='updateTerminus(" + voies[index].id + ", \"Arrivee\")' data-selected='" + voies[index].terminusArrivee_id + "'></select></div>", 
	    		    		 "estCommerciale": voies[index].estCommerciale, 
	    		    		 "affecterTransitions":"<a href='voiestransitions?voie=" + voies[index].id + "'><span class='glyphicon glyphicon-cog' aria-label='Affecter transitions'></span></a>", 
	    		    		 "":"<a href='#' onclick='supprimerVoie(" + voies[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridVoies", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("voiesContent", "table table-bordered table-hover table-striped");
	    		
	    	    getTerminusVoie();
	    	}
	    });
	}
	
	function getTerminusVoie() {
		$('.selectTerminus').each(function(i, obj) {
			var id = $(obj).attr('id').substring(15);
			$.ajax({
		    	url: "/alize/nau/voies/getTerminus",
		    	data: "idVoie=" + id,
		    	type: "POST",
		    	success: function(str) {
		    		var terminus = jQuery.parseJSON( str );
	   		    	var index;
		    		for(index = 0; index < terminus.length; ++index)
		    		{
						var option = document.createElement("option");
		    		    option.text = terminus[index].nom;
		    		    option.value = terminus[index].id;
		    		    $(obj).append(option);
		    		}
		    		
		    		var terminusSelectionne = $(obj).attr("data-selected");
		    		$(obj).children("[value=" + terminusSelectionne + "]").attr("selected", true);
		    		
		    		var selectTerminusArrivee = $("#terminusArrivee_" + id);
	   		    	var index;
		    		for(index = 0; index < terminus.length; ++index)
		    		{
						var option = document.createElement("option");
		    		    option.text = terminus[index].nom;
		    		    option.value = terminus[index].id;
		    		    selectTerminusArrivee.append(option);
		    		}
		    		
		    		terminusSelectionne = selectTerminusArrivee.attr("data-selected");
		    		selectTerminusArrivee.children("[value=" + terminusSelectionne + "]").attr("selected", true);
		    		
		    	}
		    });
		});
	}
	
	function ajouterVoie() {
	    $.ajax({
	    	url: "/alize/nau/voies/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		getVoies();
	    	}
	    });
	}
	
	function supprimerVoie(id) {
	    $.ajax({
	    	url: "/alize/nau/voies/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getVoies();
	    	}
	    });
	}
	
	function updateTerminus(idVoie, typeTerminus) {
		var colname = "terminus" + typeTerminus + "_id";
		var newvalue = $('#terminus' + typeTerminus + "_" + idVoie).val();
		
		$.ajax({
			url: '/alize/nau/voies/update',
			type: 'POST',
	    	data: "id=" + idVoie + 
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
			url: '/alize/nau/voies/update',
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