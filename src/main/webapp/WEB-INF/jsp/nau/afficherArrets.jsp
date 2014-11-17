<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arrets :</title>
</head>
<body>
	<div>
    	<button style="margin-left: 53px" id="creer-arret">Ajouter un arret</button>
    	<br>
    	<table id="tableau" class="sortable" style="border: '0';cellpadding: '0';cellspacing: '0'; overflow: scroll;">
        	<thead>
            	<tr>
                	<td><center>Nom</center></td>
                    <td><center>Id</center></td>
                    <td><center>Temps Immobilisation</center></td>
                    <td><center>Entrée/Sortie</center></td>
                    <td><center>Lieu d'échange conducteur</center></td>
                    <td><center>Terminus</center></td>
                    <td><center>Modifier</center></td>
                    <td><center>Supprimer</center></td>
                </tr>                         
             </thead>
             
             <tr>
                               
             </tr>
        </table>                      
</body>
</html>