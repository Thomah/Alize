<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZ� - Orion</title>
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
					<h1 class="page-header">Feuille de service : <span id="idFDS"></span></h1>
				</div>
			</div>
			<div class="row">
				<div id="colonne-1" class="col-lg-4"></div>
				<div id="colonne-2" class="col-lg-4"></div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> S�lection du jour
						</div>
						<div class="panel-body">
							<div id="datepicker"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- /#wrapper -->
	
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
	        dateSelectionnee = e.date;
			getServices();
	    });
	
	function getServices() {
	    $.ajax({
	    	url: "/alize/orion/feuillesdeservices/get",
	    	type: "POST",
	    	data: "date=" + dateSelectionnee.toLocaleString(),
	    	success: function(str) {
	    		var dataJSON = jQuery.parseJSON(str);
	    		
	    		if(typeof dataJSON.id != 'undefined') {
	    			document.getElementById("idFDS").innerHTML = dataJSON.id;
	    		} else {
	    			document.getElementById("idFDS").innerHTML = "";
	    		}
	    		
	    		$("#colonne-1").empty();
	    		$("#colonne-2").empty();
	    		
	    		var indexService;
	    		for(indexService = 0; indexService < dataJSON.services.length; ++indexService)
	    		{
	    			addServiceOnColumn(indexService % 2 + 1, dataJSON.services[indexService]);
	    		}
	    	}
	    });
	}
	
	function addServiceOnColumn(numColonne, service) {
		var colonne = document.getElementById("colonne-" + numColonne);
		
		var panel = document.createElement("div");
		panel.className = "panel panel-default";

		var panelHeading = document.createElement("div");
		panelHeading.className = "panel-heading";
		panelHeading.innerHTML = "Service " + service.id;
		
		var selectConducteur = document.createElement("div");
		selectConducteur.className = "pull-right";
		
		var btnConducteur = document.createElement("div");
		btnConducteur.className = "btn-group";
		
		var buttonConducteur = document.createElement("button");
		buttonConducteur.className = "btn btn-default btn-xs dropdown-toggle";
		buttonConducteur.innerHTML = "TEST ";
		buttonConducteur.type = "button";
		buttonConducteur.setAttribute("data-toggle", "dropdown");
		
		var caret = document.createElement("span");
		caret.className = "caret";
		
		buttonConducteur.appendChild(caret);
		btnConducteur.appendChild(buttonConducteur);
		
		var ulDropdown = document.createElement("ul");
		ulDropdown.className = "dropdown-menu pull-right";
		ulDropdown.setAttribute("role", "menu");
		
		var liDropdown = document.createElement("li");
		liDropdown.innerHTML = "TEST";
		
		ulDropdown.appendChild(liDropdown);
		btnConducteur.appendChild(ulDropdown);
		selectConducteur.appendChild(btnConducteur);
		panelHeading.appendChild(selectConducteur);
		
		var panelBody = document.createElement("div");
		panelBody.className = "panel-body";
		
		var divVacations = document.createElement("div");
		divVacations.id = "vacations-" + service.id;
		divVacations.className = "table-responsive";

		panelBody.appendChild(divVacations);
		panel.appendChild(panelHeading);
		panel.appendChild(panelBody);
		colonne.appendChild(panel);
		
		if(service.vacations.length > 0) {
		
			var metadata = [];
	     	metadata.push({name: "vehicule", label: "V�hicule", datatype: "string", editable: false});
	     	metadata.push({name: "heureDebut", label: "Heure D�but", datatype: "string", editable: false});
	     	metadata.push({name: "arretDebut", label: "Arr�t D�but", datatype: "string", editable: false});
	     	metadata.push({name: "heureFin", label: "Heure Fin", datatype: "string", editable: false});
	     	metadata.push({name: "arretFin", label: "Arr�t Fin", datatype: "string", editable: false});
	
			var data = [];
			var indexVacation;
			for(indexVacation = 0; indexVacation < service.vacations.length; ++indexVacation)
			{
				var vehiculeId = service.vacations[indexVacation].vehicule_id;
	    		if(typeof vehiculeId == 'undefined') {
	    			vehiculeId = "Non d�fini";
	    		}
				
				var heureDebut = service.vacations[indexVacation].heureDebut;
	    		if(typeof heureDebut == 'undefined') {
	    			heureDebut = "Non d�finie";
	    		}
	    		
	    		var arretEchangeConducteurDebut_id = service.vacations[indexVacation].arretEchangeConducteurDebut_id;
	    		if(typeof arretEchangeConducteurDebut_id == 'undefined') {
	    			arretEchangeConducteurDebut_id = "Non d�fini";
	    		}
	    		
	    		var heureFin = service.vacations[indexVacation].heureFin;
	    		if(typeof heureFin == 'undefined') {
	    			heureFin = "Non d�finie";
	    		}
	    		
	    		var arretEchangeConducteurFin_id = service.vacations[indexVacation].arretEchangeConducteurFin_id;
	    		if(typeof arretEchangeConducteurFin_id == 'undefined') {
	    			arretEchangeConducteurFin_id = "Non d�fini";
	    		}
				
			     data.push({
			    	 id: service.vacations[indexVacation].id, 
			    	 values: {
			    		 "vehicule" : vehiculeId,
			    		 "heureDebut": heureDebut, 
			    		 "arretDebut": arretEchangeConducteurDebut_id, 
			    		 "heureFin": heureFin, 
			    		 "arretFin": arretEchangeConducteurFin_id
			    		 }
			     });
			}
			
			editableGrid = new EditableGrid("Grid");
			editableGrid.load({"metadata": metadata, "data": data});
			editableGrid.renderGrid("vacations-" + service.id, "table table-bordered table-hover table-striped");
			
		}
	
	}

	</script>
	
</body>
</html>