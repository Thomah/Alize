<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import="static alize.orion.commun.Constantes.*"%>
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
				<a <% out.print(classActive(urlPage, URL_CONDUCTEURS)); %> href="<c:url value="<%=URL_CONDUCTEURS %>" />">
					<i class="fa fa-th-list fa-fw"></i> Conducteurs
				</a>
			</li>
			<li>
				<a <% out.print(classActive(urlPage, URL_FEUILLES_DE_SERVICES)); %> href="<c:url value="<%=URL_FEUILLES_DE_SERVICES %>" />">
					<i class="fa fa-th-list fa-fw"></i> Feuilles de services
				</a>
			</li>
		</ul>
	</div>
</div>