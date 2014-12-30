<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import="static alize.eole.constante.Contraintes.*"%>
<%@ page import="java.util.List"%>
<%@ page import="alize.commun.modele.tables.pojos.*"%>
<%
	List<Ligne> lignes = (List<Ligne>) request.getAttribute("lignes");
	List<Voie> voies = (List<Voie>) request.getAttribute("voies");
	List<Arret> arrets = (List<Arret>) request.getAttribute("arrets");
	List<Periodicite> periodicites = (List<Periodicite>) request.getAttribute("periodicites");
	
	Ligne ligne = (Ligne) request.getAttribute("ligne");
	Voie voie = (Voie) request.getAttribute("voie");
	Arret arret = (Arret) request.getAttribute("arret");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
<style>
	.labelLeft {
		text-align: right;
		float: left;
		margin-top: 6px;
		margin-right: 1em;
	}
	.inputright {
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857;
		color: #555;
		background-color: #FFF;
		background-image: none;
		border: 1px solid #CCC;
		border-radius: 4px;
		box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
		transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
	}
	
	a {	color: #069; }
	a:hover { color: #28b; }
	
	h2 {
		margin-top: 15px;
		font: normal 32px "omnes-pro", Helvetica, Arial, sans-serif;
	}
	
	h3 {
		margin-left: 30px;
		font: normal 26px "omnes-pro", Helvetica, Arial, sans-serif;
		color: #666;
	}
	
	p {
		margin-top: 10px;
	}
	
	button {
		font-size: 18px;
		padding: 1px 7px;
	}
	
	input {
		font-size: 18px;
	}
	
	input[type=checkbox] {
		margin: 7px;
	}
	
	#header {
		position: relative;
		width: 900px;
		margin: auto;
	}
	
	#header h2 {
		margin-left: 10px;
		vertical-align: middle;
		font-size: 42px;
		font-weight: bold;
		text-decoration: none;
		color: #000;
	}
	
	#content {
		width: 880px;
		margin: 0 auto;
		padding: 10px;
	}
	
	#footer {
		margin-top: 25px;
		margin-bottom: 10px;
		text-align: center;
		font-size: 12px;
		color: #999;
	}
	
	.demo-container {
		box-sizing: border-box;
		width: 850px;
		height: 450px;
		padding: 20px 15px 15px 15px;
		margin: 15px auto 30px auto;
		border: 1px solid #ddd;
		background: #fff;
		background: linear-gradient(#f6f6f6 0, #fff 50px);
		background: -o-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -ms-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -moz-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -webkit-linear-gradient(#f6f6f6 0, #fff 50px);
		box-shadow: 0 3px 10px rgba(0,0,0,0.15);
		-o-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-ms-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-moz-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-webkit-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
	}
	
	.demo-placeholder {
		width: 100%;
		height: 100%;
		font-size: 14px;
		line-height: 1.2em;
	}
	
	.legend table {
		border-spacing: 5px;
	}
	.btn-group {
		margin-bottom: 5px;
	}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Contraintes</h1>
					<form method="post" action="/alize/eole/contraintes">
						<div class="panel panel-default">
							<div class="panel-heading">Edition</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<div class="form-group">
											<label for="<%=NB_VEHICULES_MAX_LABEL %>">Nombre de véhicules maximum</label>
											<input class="form-control" type="number" name="<%=NB_VEHICULES_MAX_LABEL %>" id="<%=NB_VEHICULES_MAX_LABEL %>" value="<%=request.getAttribute("nbVehiculesMax") %>" />
										</div>
										<div class="form-group">
											<label for="<%=TEMPS_TRAVAIL_MAX_LABEL %>">Temps de travail maximum légal par jour</label>
											<input class="form-control" type="time" name="<%=TEMPS_TRAVAIL_MAX_LABEL %>" id="<%=TEMPS_TRAVAIL_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsTravailMax") %>" />
										</div>
									</div>
									<div class="col-lg-6">
										<div class="form-group">
											<label for="<%=TEMPS_CONDUITE_MAX_LABEL %>">Temps de conduite maximum légal</label>
											<input class="form-control" type="time" name="<%=TEMPS_CONDUITE_MAX_LABEL %>" id="<%=TEMPS_CONDUITE_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsConduiteMax") %>" />
										</div>
										<div class="form-group">
											<div class="row">
												<div class="col-lg-12">
													<label>Temps de pause</label>
												</div>
											</div>
											<div class="row">
												<div class="col-lg-6">
													<label class="labelLeft" for="<%=TEMPS_PAUSE_MIN_LABEL %>">Min</label>
													<input class="inputright" type="time" name="<%=TEMPS_PAUSE_MIN_LABEL %>" id="<%=TEMPS_PAUSE_MIN_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsPauseMin") %>" />
												</div>
												<div class="col-lg-6">
													<label class="labelLeft" for="<%=TEMPS_PAUSE_MAX_LABEL %>">Max</label>
													<input class="inputright" type="time" name="<%=TEMPS_PAUSE_MAX_LABEL %>" id="<%=TEMPS_PAUSE_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsPauseMax") %>" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-bar-chart-o fa-fw"></i> Périodicités
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <div class="row">
	                                <div class="col-lg-4">
	                                	<div class="form-group">
                                            <label for="<%=LIGNE_LABEL %>">Ligne</label>
                                            <select class="form-control" name="<%=LIGNE_LABEL %>" id="<%=LIGNE_LABEL %>" onchange="getVoiesPourLaLigne()">
                                           	<% for (Ligne l : lignes) { %>
                                           		<option value="<%=l.getId() %>">Ligne <%=l.getId() %></option>
                                           	<% } %>
											</select>
                                        </div>
                                        <div class="form-group">
                                            <label for="<%=VOIE_LABEL %>">Voie</label>
                                            <select class="form-control" name="<%=VOIE_LABEL %>" id="<%=VOIE_LABEL %>" onchange="getArretsPourLaVoie()">
											</select>
                                        </div>
                                        <div class="form-group">
                                            <label for="<%=ARRET_LABEL %>">Arrêt de référence</label>
                                            <select class="form-control" name="<%=ARRET_LABEL %>" id="<%=ARRET_LABEL %>" onchange="getPeriodicitePourLArret()">
											</select>
                                        </div>
	                                </div>
	                                <!-- /.col-lg-4 (nested) -->
	                                <div class="col-lg-8">
	                                	<div class="btn-group" role="group">
	                                		<button type="button" class="btn btn-default" onclick="ajouterPeriodicite()"><span class="glyphicon glyphicon-plus"></span> Ajouter</button>
	                                	</div>
										<div id="periodiciteContent" class="table-responsive">
	                                    </div>
	                                    <!-- /.table-responsive -->
	                                </div>
	                                <!-- /.col-lg-8 (nested) -->
	                            </div>
	                            <!-- /.row -->
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
						<input type="submit" class="btn btn-default" value="Envoyer" />
					</form>
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
	<script src="<c:url value="/resources/js/plugins/editablegrid/demo.js"/>"></script>
	<script type="text/javascript">
	window.onload = function() {
		document.getElementById("<%=LIGNE_LABEL %>").value = <%=lignes.get(0).getId()%>;
		getVoiesPourLaLigne();
	}
	
	function getVoiesPourLaLigne() {
		var idLigne = $("#<%=LIGNE_LABEL %>").val();

	    $.ajax({
	    	url: "/alize/eole/contraintes/selectLigne",
	    	data: "idLigne=" + idLigne,
	    	type: "POST",
	    	success: function(str) {
	    		var voies = jQuery.parseJSON( str );
   		    	var select = document.getElementById("<%=VOIE_LABEL %>");
   		    	select.innerHTML = "";
   		    	var index;
	    		for(index = 0; index < voies.length; ++index)
	    		{
	    		     var id = voies[index].id;
	    		     var direction = voies[index].direction;
	    		     
	    		     var option = document.createElement("option");
	    		     option.text = direction;
	    		     option.value = id;
	    		     select.appendChild(option);
	    		}
	    		document.getElementById("<%=VOIE_LABEL %>").firstChild.selected = true;
	    		getArretsPourLaVoie();
	    	}
	    });
	}
	
	function getArretsPourLaVoie() {
		var idVoie = $("#<%=VOIE_LABEL %>").val();

	    $.ajax({
	    	url: "/alize/eole/contraintes/selectVoie",
	    	data: "idVoie=" + idVoie,
	    	type: "POST",
	    	success: function(str) {
	    		var arrets = jQuery.parseJSON( str );
   		    	var select = document.getElementById("<%=ARRET_LABEL %>");
   		    	select.innerHTML = "";
   		    	var index;
	    		for(index = 0; index < arrets.length; ++index)
	    		{
	    		     var id = arrets[index].id;
	    		     var nom = arrets[index].nom;

	    		     var option = document.createElement("option");
	    		     option.text = nom;
	    		     option.value = id;
	    		     select.appendChild(option);
	    		}
	    		document.getElementById("<%=ARRET_LABEL %>").firstChild.selected = true;
	    		getPeriodicitePourLArret();
	    	}
	    });
	}
	
	function getPeriodicitePourLArret() {
		var idVoie = $("#<%=VOIE_LABEL %>").val();
		var idArret = $("#<%=ARRET_LABEL %>").val();

	    $.ajax({
	    	url: "/alize/eole/contraintes/selectArret",
	    	data: "idVoie=" + idVoie + "&idArret=" + idArret,
	    	type: "POST",
	    	success: function(str) {
   		    	var metadata = [];
   		     	metadata.push({name: "debut", label: "Heure de début", datatype: "string", editable: true});
   		     	metadata.push({name: "fin", label: "Heure de fin", datatype: "string", editable: true});
   		     	metadata.push({name: "periode", label: "Temps entre deux véhicules", datatype: "string", editable: true});
   		     	metadata.push({datatype: "html", editable: false});
   		     	
				var data = [];
	    		var periodicites = jQuery.parseJSON( str );
   		    	var index;
	    		for(index = 0; index < periodicites.length; ++index)
	    		{
	    		     data.push({id: periodicites[index].id, values: {"debut": periodicites[index].debut, "fin": periodicites[index].fin, "periode": periodicites[index].periode, "":"<a href='#' onclick='supprimerPeriodicite(" + periodicites[index].id + ")'><span class='glyphicon glyphicon-remove' aria-label='Supprimer'></span></a>"}});
	    		}
	    		
	    		editableGrid = new EditableGrid("GridPeriodicites", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    		editableGrid.load({"metadata": metadata, "data": data});
	    		editableGrid.renderGrid("periodiciteContent", "table table-bordered table-hover table-striped");
	    	}
	    });
	}
	
	function ajouterPeriodicite() {
		var idVoie = $("#<%=VOIE_LABEL %>").val();
		var idArret = $("#<%=ARRET_LABEL %>").val();

	    $.ajax({
	    	url: "/alize/eole/contraintes/ajouterPeriodicite",
	    	data: "idVoie=" + idVoie + "&idArret=" + idArret,
	    	type: "POST",
	    	success: function(str) {
	    		getPeriodicitePourLArret();
	    	}
	    });
	}

	function supprimerPeriodicite(id) {
		var idVoie = $("#<%=VOIE_LABEL %>").val();
		var idArret = $("#<%=ARRET_LABEL %>").val();

	    $.ajax({
	    	url: "/alize/eole/contraintes/supprimerPeriodicite",
	    	data: "id=" + id,
	    	type: "POST",
	    	success: function(str) {
	    		getPeriodicitePourLArret();
	    	}
	    });
	}
	
	function updateCellValue(editableGrid, rowIndex, columnIndex, oldValue, newValue, row, onResponse)
	{
		var id = editableGrid.getRowId(rowIndex);
		var newvalue = editableGrid.getColumnType(columnIndex) == "boolean" ? (newValue ? 1 : 0) : newValue;
		var colname = editableGrid.getColumnName(columnIndex);
		
		$.ajax({
			url: '/alize/eole/contraintes/updatePeriodicite',
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