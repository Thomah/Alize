
<%@page import="alize.nau.controlleur.NauControlleur"%>
<%@ include file ="/WEB-INF/jsp/commun/include.jsp"%>


<%@ page import="java.util.ArrayList"%>
<%@ page import="alize.commun.modele.tables.pojos.Arret"%>
<%@ page import="alize.commun.modele.tables.pojos.Terminus"%>
<%@ page import="static alize.nau.commun.Constantes.*"%>
<%
	ArrayList<ArrayList<String>> tableauArret = (ArrayList<ArrayList<String>>) request.getAttribute(TABLEAU_ARRET_CLE);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arrets :</title>

</head>
<body>



	<div id="wrapper">
		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Afficher les arrets</h1>
					<button style="margin-left: 5px" id="creer-arret">Ajouter
						arrêt</button>
					<br>
					<table id="tableau" class="sortable"  align = 'center' border="5" style="border: '0'; cellpadding: '5'; cellspacing: '5'; overflow: scroll; text-align: center;" >
						<thead>
							<tr>
								<td><center>Id</center></td>
								<td><center>Nom</center></td>
								<td><center>Commercial</center></td>
								<td><center>Temps Immobilisation</center></td>
								<td><center>Entrée</center></td>
								<td><center>Sortie</center></td>
								<td><center>Lieu d'échange conducteur</center></td>
								<td><center>Terminus</center></td>
								<td><center>Modifier</center></td>
								<td><center>Supprimer</center></td>
							</tr>
							<%
							
								for (ArrayList<String> ligneArret : tableauArret) {
								
							%>
							<tr>
							<%
									for(String s : ligneArret){
							%>
							
								<td>
									<%
										if(s.equals("true")||s.equals("false")){
											%>
											<input type="checkbox"  disabled="disabled" align = "middle"
												<%
												if(s.equals("true")){
												%>
												 checked
												<%
												}
												%>
											>
											
											<%
										}else{
											out.println(s);
										}
									%>
								</td>
								<%
										}
										
									%>
							<td>
								<button type="button" class="btn btn-default" onclick="supprimerArret(<%=ligneArret.get(0)%>)">Modifier</button>
							</td>
							<td>
								<button type="button" class="btn btn-default" onclick="supprimerArret(<%=ligneArret.get(0)%> )">Supprimer</button>
							</td>
						</tr>
						<%
						}			
						%>
						</thead>
					</table>
					<div id="periodiciteContent" class="table-responsive">
	                </div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->

		</div>
		<label id="test">Test : </label>
	<script type="text/javascript">
		
	function supprimerArret(id) {
	
		editableGrid = new EditableGrid("GridPeriodicites", {
	    			modelChanged: function(rowIndex, columnIndex, oldValue, newValue, row) {
	    	   	    	updateCellValue(this, rowIndex, columnIndex, oldValue, newValue, row);
	    	       	}
	    	 	});
	    editableGrid.load({"metadata": metadata, "data": data});
	    editableGrid.renderGrid("periodiciteContent", "table table-bordered table-hover table-striped");
	}
	
	</script>
	</div>
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
</body>

</html>
