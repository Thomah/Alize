<%@ include file="/WEB-INF/jsp/commun/include.jsp"%>
<%@ page import="static alize.commun.Constantes.URL_MODULE_CLE"%>
<%@ page import="static alize.eole.constante.Contraintes.*"%>
<%@ page import="java.util.List"%>
<%@ page import="alize.commun.modele.tables.pojos.*"%>
<%
	List<Ligne> lignes = (List<Ligne>) request.getAttribute("lignes");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/plugins/datetimepicker/bootstrap-datetimepicker.css"/>" />
<style>
a {
	color: #E66D2B;
}
a:hover, a:focus {
    color: #651C00;
}
svg {
	width: 800;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Diagramme de ligne</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Diagramme
						</div>
						<div class="panel-body" id="diagramme">
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Sélection de la ligne
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label for="<%=LIGNE_LABEL %>">Ligne</label>
								<select class="form-control" name="<%=LIGNE_LABEL %>" id="<%=LIGNE_LABEL %>" onchange="getDiagramme()">
								<% for (Ligne l : lignes) { %>
									<option value="<%=l.getId() %>">Ligne <%=l.getId() %></option>
								<% } %>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script
		src="<c:url value="/resources/js/plugins/moment/moment.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/datetimepicker/bootstrap-datetimepicker.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/snapsvg/snap.svg-min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/diagrammeligne/diagrammeligne.js"/>"></script>
	<script type="text/javascript">
	
	// Variables globales
	var diagrammes = [];
	
	var LARGEUR_SVG = 914;
	var INTERVALLE_GRADUATION_ORDONNEES = 50;
	var MIN_TEMPS = 5;
	var MAX_TEMPS = 27;
	var HEIGHT_TEXTE = 20;
	
	// Appels au chargement de la page
	getDiagramme();
	
	function getDiagramme() {
		var idLigne = $("#<%=LIGNE_LABEL %>").val();
		
		if(!(idLigne === null)) {
		    $.ajax({
		    	url: "/alize/eole/diagrammeligne/get",
		    	data: "idLigne=" + idLigne,
		    	type: "POST",
		    	success: function(str) {
		    		var data = [];
		    		var dataJSON = jQuery.parseJSON( str );
	   		    	var indexArray;
	   		    	
		    		for(indexArray = 0; indexArray < dataJSON[1].length; ++indexArray) {
		    			diagrammes[indexArray] = new DiagrammeVoie();
		    			diagrammes[indexArray].initialiserPaper(document, dataJSON, indexArray);
		    		}
		    		
		    		for(indexArray = 0; indexArray < dataJSON[0].length; ++indexArray) {
		    			paintActions(dataJSON, indexArray);
		    		}
		    	}
		    });
		}
	}
	
	</script>
</body>
</html>