<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import="static alize.eole.constante.Communes.*"%>

<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li><a class="active" href="<c:url value="<%=URL_INDEX %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Accueil</a></li>
			<li><a href="<c:url value="<%=URL_CONTRAINTES %>" />"><i
					class="fa fa-dashboard fa-fw"></i> Contraintes</a></li>
		</ul>
	</div>
</div>