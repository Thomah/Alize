<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import=" static alize.commun.Constantes.URL_MODULE_CLE"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Orion</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/plugins/datepicker/datepicker3.css"/>" />
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
					<h1 class="page-header">Bienvenue</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Attribution des services
						</div>
						<div class="panel-body">
                        	<div id="servicesContent" class="table-responsive">
                            </div>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Sélection du jour
						</div>
						<div class="panel-body">
							<div id="datepicker"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script src="<c:url value="/resources/js/plugins/editablegrid/editablegrid.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/datepicker/bootstrap-datepicker.js"/>"></script>
	<script type="text/javascript">
		var dateSelectionnee;
	
	    $('#datepicker').datepicker({
	        weekStart: 1,
	        language: "fr",
	        calendarWeeks: true,
	        todayHighlight: true
	        }).on('changeDate', function(e){
		        console.log(e);
		        dateSelectionnee = e.date;
				getServices();
		    });
	    
		function getServices() {
		    $.ajax({
		    	url: "/alize/orion/services/get",
		    	type: "POST",
		    	data: "date=" + dateSelectionnee.toLocaleString(),
		    	success: function(str) {
	   		    	var metadata = [];
	   		     	metadata.push({name: "id", label: "ID", datatype: "int", editable: false});
	   		     	metadata.push({name: "conducteur", label: "Conducteur", datatype: "html", editable: false});
	   		     	
					var data = [];
		    		var services = jQuery.parseJSON( str );
	   		    	var index;
	   		    	var optionsSelectConducteur = getOptionsSelectConducteur();
		    		for(index = 0; index < services.length; ++index)
		    		{
		    		     data.push({
		    		    	 id: services[index].id, 
		    		    	 values: {
		    		    		 "id": services[index].id, 
		    		    		 "conducteur": "<div class='form-group'><select class='form-control selectConducteur' id='conducteur_" + services[index].id + "' onchange='updateConducteur(" + services[index].id + ")' data-selected='" + services[index].conducteur + "'>" + optionsSelectConducteur + "</select></div>", 
		    		    		 }
		    		     });
		    		}
		    		
		    		editableGrid = new EditableGrid("GridServices");
		    		editableGrid.load({"metadata": metadata, "data": data});
		    		editableGrid.renderGrid("servicesContent", "table table-bordered table-hover table-striped");
		    		
		    		selectConducteur();
		    	}
		    });
		}

		function getOptionsSelectConducteur() {
		    var chaineOptions = "<option value='0'></option>";
			$.ajax({
		    	url: "/alize/orion/conducteurs/get",
		    	type: "POST",
		    	async: false,
		    	success: function(str) {
		    		var data = [];
		    		var conducteurs = jQuery.parseJSON( str );
	   		    	var index;
		    		for(index = 0; index < conducteurs.length; ++index)
		    		{
		    			chaineOptions+= "<option value='" + conducteurs[index].id + "'>" + conducteurs[index].nom + "</option>";
		    		}
		    	}
		    });
			return chaineOptions;
		}
		
		function selectConducteur() {
			$('.selectConducteur').each(function(i, obj) {
				var id = $(obj).attr('id').substring(15);
				var conducteurSelectionne = $(obj).attr("data-selected");
	    		$(obj).children("[value=" + conducteurSelectionne + "]").attr("selected", true);
			});
		}
		
		function updateConducteur(idService) {
			var colname = "conducteur";
			var newvalue = $('#conducteur_' + idService).val();
			
			$.ajax({
				url: '/alize/orion/services/update',
				type: 'POST',
		    	data: "idService=" + idService + 
	    			"&date=" + dateSelectionnee.toLocaleString() + 
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