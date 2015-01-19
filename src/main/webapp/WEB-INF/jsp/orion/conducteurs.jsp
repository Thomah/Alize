<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Orion</title>
<style>
a {
	color: #A800FF;
}
a:hover, a:focus {
    color: #A57EFF;
}
.datepicker {
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Conducteurs</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Ressources humaines
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouter()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="content" class="table-responsive">
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
		get();
	}
	
	function get() {
	    $.ajax({
	    	url: "/alize/orion/conducteurs/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "nom", label: "Nom", datatype: "string", editable: true});
   		     	metadata.push({name: "telephone", label: "Téléphone", datatype: "string", editable: true});
   		     	metadata.push({name: "supprimer", label: "Supprimer", datatype: "html", editable: false});
   		     	
				var data = [];
	    		var dataJSON = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < dataJSON.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: dataJSON[index].id, 
	    		    	 values: {
	    		    		 "id": dataJSON[index].id, 
	    		    		 "nom": dataJSON[index].nom, 
	    		    		 "telephone": dataJSON[index].telephone, 
	    		    		 "supprimer":"<a href='#' onclick='supprimer(" + dataJSON[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("Grid", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("content", "table table-bordered table-hover table-striped");
	    	}
	    });
	}

	function ajouter() {
	    $.ajax({
	    	url: "/alize/orion/conducteurs/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		get();
	    	}
	    });
	}
	
	function supprimer(id) {

	    $.ajax({
	    	url: "/alize/orion/conducteurs/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		get();
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
			url: '/alize/orion/conducteurs/update',
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