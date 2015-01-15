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
					<h1 class="page-header">Services</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Plannification
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterService()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
                               	</div>
	                        	<div id="servicesContent" class="table-responsive">
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
		getServices();
	}
	
	function getServices() {
	    $.ajax({
	    	url: "/alize/eole/services/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "fds", label: "Feuille de services", datatype: "html", editable: false});
   		     	metadata.push({name: "affecterVacations", label: "Affecter les vacations", datatype: "html", editable: false});
   		     	metadata.push({name: "supprimer", label: "Supprimer", datatype: "html", editable: false});
   		     	
				var data = [];
	    		var services = jQuery.parseJSON( str );
   		    	var index;
   		    	var optionsSelectFDS = getOptionsSelectFDS();
	    		for(index = 0; index < services.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: services[index].id, 
	    		    	 values: {
	    		    		 "id": services[index].id, 
	    		    		 "fds": "<div class='form-group'><select class='form-control selectFDS' id='fds_" + services[index].id + "' onchange='updateFDS(" + services[index].id + ")' data-selected='" + services[index].feuilledeservice_id + "'>" + optionsSelectFDS + "</select></div>", 
	    		    		 "affecterVacations":"<a href='vacationsservice?service=" + services[index].id + "'><span class='glyphicon glyphicon-cog' aria-label='Affecter vacations'></span></a>", 
	    		    		 "supprimer":"<a href='#' onclick='supprimerService(" + services[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
	    		    		 }
	    		     });
	    		}
	    		
	    		editableGrid = new EditableGrid("GridServices", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("servicesContent", "table table-bordered table-hover table-striped");
	    		
	    		selectFDS();
	    	}
	    });
	}

	function getOptionsSelectFDS() {
	    var chaineOptions = "<option value='0'></option>";
		$.ajax({
	    	url: "/alize/eole/fds/get",
	    	type: "POST",
	    	async: false,
	    	success: function(str) {
	    		var data = [];
	    		var fds = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < fds.length; ++index)
	    		{
	    			chaineOptions+= "<option value='" + fds[index].id + "'>" + fds[index].couleur + "</option>";
	    		}
	    	}
	    });
		return chaineOptions;
	}
	
	function selectFDS() {
		$('.selectFDS').each(function(i, obj) {
			var id = $(obj).attr('id').substring(15);
			var fdsSelectionne = $(obj).attr("data-selected");
    		$(obj).children("[value=" + fdsSelectionne + "]").attr("selected", true);
		});
	}
	
	function ajouterService() {
	    $.ajax({
	    	url: "/alize/eole/services/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		getServices();
	    	}
	    });
	}
	
	function supprimerService(id) {

	    $.ajax({
	    	url: "/alize/eole/services/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getServices();
	    	}
	    });
	}
	
	function updateFDS(idService) {
		var colname = "fds";
		var newvalue = $('#fds_' + idService).val();
		
		$.ajax({
			url: '/alize/eole/services/update',
			type: 'POST',
	    	data: "id=" + idService + 
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
	
	function updateCellValue(editableGrid, rowIndex, columnIndex, oldValue, newValue, row, onResponse)
	{
		var id = editableGrid.getRowId(rowIndex);
		var newvalue = editableGrid.getColumnType(columnIndex) == "boolean" ? (newValue ? 1 : 0) : newValue;
		var colname = editableGrid.getColumnName(columnIndex);
		
		$.ajax({
			url: '/alize/eole/services/update',
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