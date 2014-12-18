<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="alize.commun.modele.tables.pojos.Arret"%>
<% ArrayList<Arret> listeArret = (ArrayList<Arret>) request.getAttribute("listeArret"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arrets :</title>

</head>
<body>
	<div>
    	<button style="margin-left: 5px" id="creer-arret">Ajouter arrêt</button>
    	<br>
		<table id="tableau" class="sortable"
			style="border: '0'; cellpadding: '0'; cellspacing: '0'; overflow: scroll;">
			<thead>
				<tr>
					<td><center>Nom</center></td>
					<td><center>Id</center></td>
					<td><center>Commercial</center></td>
					<td><center>Temps Immobilisation</center></td>
					<td><center>Entrée/Sortie</center></td>
					<td><center>Lieu d'échange conducteur</center></td>
					<td><center>Terminus</center></td>
					<td><center>Modifier</center></td>
					<td><center>Supprimer</center></td>
				</tr>
				<%
     			for(Arret a : listeArret)
     			{
          		%>
				<tr>
					<td>
						<%out.println(a.getNom());%>
					</td>
					<td>
						<%out.println(a.getId());%>
					</td>
					<td><%out.println(a.getEstcommercial());%></td>
					<td>
<%-- 						<%out.println(a.getTempsimmobilisation());%> --%>
					</td>
					<td>
<%-- 						<%out.println(a.getEstentreesortiedepot());%> --%>
					</td>
					<td>
						<%out.println(a.getEstlieuechangeconducteur());%>
					</td>
					<td>
						<%out.println("Test");%>
						
					</td>
					<td>
						<button style="margin-left: 5px" id="modifier-arret">Modifier</button>
					</td>
					<td>
						<button style="margin-left: 5px" id="creer-arret">Supprimer</button>
					</td>
				</tr>
				<%
    			}
				%>
			</thead>
		</table>
		</div>
	</body>
		<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
</html>