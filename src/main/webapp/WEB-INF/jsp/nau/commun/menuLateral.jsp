<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import=" static alize.nau.commun.Constantes.*"%>

<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li><a class="active" href="<c:url value="<%=URL_INDEX %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Accueil</a></li>
			<li><a href="#"><i class="fa fa-list fa-fw"></i> Modèle<span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="<c:url value="<%=URL_LIGNES %>" />">Lignes</a></li>
					<li><a href="<c:url value="<%=URL_VOIES %>" />">Voies</a></li>
					<li><a href="<c:url value="#"/>">Arrets</a></li>
					<li><a href="<c:url value="#"/>">Transitions</a></li>
				</ul></li>
			<li><a href="<c:url value="<%=URL_IMPORTER %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Importer</a></li>
			<li><a href="<c:url value="<%=URL_EXPORTER %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Exporter</a></li>
			<li><a href="<c:url value="<%=URL_AFFICHERARRETS %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Afficher arrêts</a></li>
		</ul>
	</div>
</div>