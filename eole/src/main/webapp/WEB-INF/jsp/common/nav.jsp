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

	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a class="active" href="<c:url value="/accueil"/>"><i
						class="fa fa-dashboard fa-fw"></i> Accueil</a></li>
				<li><a href="#"><i class="fa fa-list fa-fw"></i>
						Modèle<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="<c:url value="#"/>">Ligne</a></li>
						<li><a href="<c:url value="#"/>">Voies</a></li>
						<li><a href="<c:url value="#"/>">Arrets</a></li>
						<li><a href="<c:url value="#"/>">Transitions</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>