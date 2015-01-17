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
					<h1 class="page-header">Arrêts</h1>
					<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Composition du réseau
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
                               	<div class="btn-group" role="group">
                               		<button type="button" class="btn btn-default" onclick="ajouterArret()"><span class="glyphicon glyphicon-plus"></span>Ajouter</button>
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
		getArrets();
	}
	
	function getArrets() {
	    $.ajax({
	    	url: "/alize/nau/arrets/get",
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
   		     	metadata.push({name: "nom", label: "Nom", datatype: "string", editable: true});
   		     	metadata.push({name: "estCommercial", label: "Commercial", datatype: "boolean", editable: true});
   		     	metadata.push({name: "tempsImmobilisation", label: "Temps d'immobilisation", datatype: "html", editable: false});
   		     	metadata.push({name: "estEntree", label: "Entrée", datatype: "boolean", editable: true});
   		     	metadata.push({name: "estSortie", label: "Sortie", datatype: "boolean", editable: true});
   		     	metadata.push({name: "estLieuEchangeConducteur", label: "Echange Conducteurs", datatype: "boolean", editable: true});
   		    	metadata.push({name: "estTerminus", label: "Terminus", datatype: "boolean", editable: true});
   		    	metadata.push({name: "estDepot", label: "Dépôt", datatype: "boolean", editable: true});
   		    	metadata.push({name: "estEnFaceDe", label: "En face de", datatype: "html", editable: false});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var arrets = jQuery.parseJSON( str );
   		    	var index;
   		    	var optionsSelectArret = getOptionsSelectArret();
	    		for(index = 0; index < arrets.length; ++index)
	    		{
	    		     data.push({
	    		    	 id: arrets[index].id, 
	    		    	 values: {
	    		    		 "id": arrets[index].id, 
	    		    		 "nom": arrets[index].nom, 
	    		    		 "estCommercial": arrets[index].estCommercial,
	    		    		 "tempsImmobilisation": "<table><tr><td>Min  : </td><td><input type='text' name='tpsMIN_"+ arrets[index].tempsImmobilisationID +"' value='"+ arrets[index].tempsImmobilisationMIN +"' onchange='updateTempsImmobilisation("+ arrets[index].tempsImmobilisationID+ ",\"MIN_\"," + arrets[index].id + ")'> </td></tr><tr><td>Pref : </td><td><input type='text' name='tpsPREF_"+ arrets[index].tempsImmobilisationID +"' value='"+ arrets[index].tempsImmobilisationPREF +"' onchange='updateTempsImmobilisation("+ arrets[index].tempsImmobilisationID+ ",\"PREF_\"," + arrets[index].id + ")'></td></tr><tr><td>Max  : </td><td><input type='text' name='tpsMAX_"+ arrets[index].tempsImmobilisationID +"' value='"+ arrets[index].tempsImmobilisationMAX +"' onchange='updateTempsImmobilisation("+ arrets[index].tempsImmobilisationID+ ",\"MAX_\"," + arrets[index].id + ")'></td></tr></table>",
	    		    		 "estEntree": arrets[index].estEntree,
	    		    		 "estSortie": arrets[index].estSortie,
	    		    		 "estLieuEchangeConducteur": arrets[index].estLieuEchangeConducteur,
	    		    		 "estTerminus": arrets[index].estTerminus,
	    		    		 "estDepot": arrets[index].estDepot,
	    		    		 "estEnFaceDe": "<div class='form-group'><select class='form-control selectArret' id='estEnFaceDe_" + arrets[index].id + "' onchange='updateArret(" + arrets[index].id + ")' data-selected='" + arrets[index].estEnFaceDe + "'>" + optionsSelectArret + "</select></div>",
	    		    		 "":"<a href='#' onclick='supprimerArret(" + arrets[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"
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
	    		
	    		selectArrets();
	    	}
	    });
	}
	
	function getOptionsSelectArret() {
	    var chaineOptions = "<option value='0'>Aucun</option>";
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
			var id = $(obj).attr('id').substring(12);
			var arretSelectionne = $(obj).attr("data-selected");
    		$(obj).children("[value=" + arretSelectionne + "]").attr("selected", true);
		});
	}
	
	function updateArret(idArret) {
		var colname = "estEnFaceDe";
		var newvalue = $('#estEnFaceDe_' + idArret).val();
		
		$.ajax({
			url: '/alize/nau/arrets/updateArret',
			type: 'POST',
	    	data: "id=" + idArret + 
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
	
	function updateTempsImmobilisation(idTempsImmobilisation, particule, idArret) {
		var colname = "tps"+particule+idTempsImmobilisation;
		var newvalue = document.getElementsByName('tps'+ particule + idTempsImmobilisation)[0].value
		$.ajax({
			url: '/alize/nau/arrets/updateTempsImmobilisation',
			type: 'POST',
	    	data: "id=" +idTempsImmobilisation + 
	    		"&newvalue=" + newvalue + 
   				"&colname=" + colname,
			success: function (response) 
			{ 
				// reset old value if failed then highlight row
				var success = response == "ok" || !isNaN(parseInt(response)); // by default, a sucessfull reponse can be "ok" or a database id
			    highlight(idArret, success ? "ok" : "error"); 
			    getArrets();
			},
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure"); },
			async: true
		});
	}
	
	function ajouterArret() {
	    $.ajax({
	    	url: "/alize/nau/arrets/ajouter",
	    	type: "POST",
	    	success: function(str) {
	    		getArrets();
	    	}
	    });
	}
	
	function supprimerArret(id) {
	    $.ajax({
	    	url: "/alize/nau/arrets/supprimer",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getArrets();
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
			url: '/alize/nau/arrets/updateArret',
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
			error: function(XMLHttpRequest, textStatus, exception) { alert("Ajax failure\n" ); },
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