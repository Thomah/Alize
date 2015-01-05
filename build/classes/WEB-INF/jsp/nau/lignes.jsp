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
					<h1 class="page-header">Lignes</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Composition du réseau
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterLigne()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="lignesContent" class="table-responsive">
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
		getLignes();
	}
	
	function getLignes() {
	    $.ajax({
	    	url: "/alize/nau/lignes/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "typeVehicule", label: "Type de véhicule", datatype: "string", editable: true});
   		     	metadata.push({name: "affecterVoies", label: "Affecter les voies", datatype: "html", editable: false});
   		     	metadata.push({name: "supprimer", label: "Supprimer", datatype: "html", editable: false});
   		     	
				var data = [];
	    		var lignes = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < lignes.length; ++index)
	    		{
	    		     data.push({id: lignes[index].id, values: {"id": lignes[index].id, "typeVehicule": lignes[index].typeVehicule, "affecterVoies":"<a href='lignesvoies?ligne=" + lignes[index].id + "'><span class='glyphicon glyphicon-cog' aria-label='Affecter voies'></span></a>", "supprimer":"<a href='#' onclick='supprimerLigne(" + lignes[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"}});
	    		}
	    		
	    		editableGrid = new EditableGrid("GridLignes", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("lignesContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}

	function ajouterLigne() {
	    $.ajax({
	    	url: "/alize/nau/lignes/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		getLignes();
	    	}
	    });
	}
	
	function supprimerLigne(id) {

	    $.ajax({
	    	url: "/alize/nau/lignes/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getLignes();
	    	}
	    });
	}
	
	function updateCellValue(editableGrid, rowIndex, columnIndex, oldValue, newValue, row, onResponse)
	{
		var id = editableGrid.getRowId(rowIndex);
		var newvalue = editableGrid.getColumnType(columnIndex) == "boolean" ? (newValue ? 1 : 0) : newValue;
		var colname = editableGrid.getColumnName(columnIndex);
		var coltype = editableGrid.getColumnType(columnIndex);
		
		$.ajax({
			url: '/alize/nau/lignes/update',
			type: 'POST',
	    	data: "id=" + id + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname +
   				"&coltype=" + coltype,
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