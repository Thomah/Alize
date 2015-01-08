<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import="static alize.nau.commun.Constantes.*"%>
<%@ page import="static alize.commun.Constantes.*"%>
<%!
	public String classActive(String urlPage, String urlLien) {
		String chaineRetournee = "";
		if(urlPage.compareTo(urlLien) == 0) {
			chaineRetournee = "class = 'active'";
		}
		return chaineRetournee;
	}
%>
<%
	String urlPage = request.getAttribute(URL_PAGE_CLE).toString();
%>

<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li>
				<a <% out.print(classActive(urlPage, URL_INDEX)); %> href="<c:url value="<%=URL_INDEX %>" />">
					<i class="fa fa-home fa-fw"></i> Accueil
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_LIGNES)); %> href="<c:url value="<%=URL_LIGNES %>" />">
					<i class="fa fa-th-list fa-fw"></i> Lignes
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_VOIES)); %> href="<c:url value="<%=URL_VOIES %>" />">
					<i class="fa fa-th-list fa-fw"></i> Voies
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_ARRETS)); %> href="<c:url value="<%=URL_ARRETS %>" />">
					<i class="fa fa-th-list fa-fw"></i> Arrêts
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_TRANSITIONS)); %> href="<c:url value="<%=URL_TRANSITIONS %>" />">
					<i class="fa fa-th-list fa-fw"></i> Transitions
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_IMPORTER_EXPORTER)); %> href="<c:url value="<%=URL_IMPORTER_EXPORTER %>" />">
					<i class="fa fa-save fa-fw"></i> Importer / Exporter
				</a>
			</li>
		</ul>
	</div>
</div>