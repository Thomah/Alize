<%@page import=" static alize.commun.Constantes.URL_MODULE_CLE"%>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation"
	style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="<c:url value="/"/>">AliZé V1</a>
	</div>

	<ul class="nav navbar-top-links navbar-left">
		<li><a class="active" href="<c:url value="/"/>">Accueil</a></li>
		<li><a href="<c:url value="/nau"/>">Nau</a></li>
		<li><a href="<c:url value="/eole"/>">Eole</a></li>
		<li><a href="<c:url value="/orion"/>">Orion</a></li>
	</ul>
	
	<% 
	Object nomMinusModule = request.getAttribute(URL_MODULE_CLE);
	if(nomMinusModule != null) {
		String menuLateral = "/WEB-INF/jsp/" + nomMinusModule + "/commun/menuLateral.jsp";
	%>
	<jsp:include page="<%=menuLateral%>" />
	<%} %>

</nav>