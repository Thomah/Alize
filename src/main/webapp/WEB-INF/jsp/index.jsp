<%@ include file="/WEB-INF/jsp/commun/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé</title>
<style>
a {
	color: #858585;
}

a:hover, a:focus {
	color: #000000;
}

.panel-nau {
	border-color: #428BCA;
}

.panel-nau>a {
	color: #428BCA;
}

.panel-nau>.panel-heading {
	color: #FFF;
	background-color: #428BCA;
	border-color: #428BCA;
}

.panel-eole {
	border-color: #E66D2B;
}

.panel-eole>a {
	color: #E66D2B;
}

.panel-eole>.panel-heading {
	color: #FFF;
	background-color: #E66D2B;
	border-color: #E66D2B;
}

.panel-orion {
	border-color: #6f00ff;
}

.panel-orion>a {
	color: #6f00ff;
}

.panel-orion>.panel-heading {
	color: #FFF;
	background-color: #6f00ff;
	border-color: #6f00ff;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Bienvenue</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4">
					<div class="panel panel-nau">
						<div class="panel-heading">Module Nau</div>
						<div class="panel-body">
							<div class="col-xs-3">
								<i class="fa fa-code-fork fa-5x"></i>
							</div>
							<div class="col-xs-9">
								<p class="lead">Modélisation du réseau</p>
								<p>Via ce module, vous pouvez, au choix :</p>
								<ul>
									<li>Concevoir pas à pas un réseau de transport avec ses
										différents composants</li>
									<li>Importer un réseau formaté selon <a
										href="<c:url value="/resources/grammaire.xsd" />">cette
											grammaire</a></li>
								</ul>
							</div>
						</div>
						<a href="<c:url value="/nau" />">
							<div class="panel-footer">
								<span class="pull-left">Accéder au module</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-eole">
						<div class="panel-heading">Module Eole</div>
						<div class="panel-body">
							<div class="col-xs-3">
								<i class="fa fa-calculator fa-5x"></i>
							</div>
							<div class="col-xs-9">
								<p class="lead">Génération du planning</p>
								<p>A partir de la modélisation effectuée par Nau, Eole
									calcule les différentes possibilités envisageables de façon à
									fournir un planning général appelé « feuille de service » ainsi
									qu'un diagramme de Ligne montrant le positionnement d'un
									véhicule sur une voie</p>
							</div>
						</div>
						<a href="<c:url value="/eole" />">
							<div class="panel-footer">
								<span class="pull-left">Accéder au module</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="panel panel-orion">
						<div class="panel-heading">Module Orion</div>
						<div class="panel-body">
							<div class="col-xs-3">
								<i class="fa fa-users fa-5x"></i>
							</div>
							<div class="col-xs-9">
								<p class="lead">Attribution des ressources humaines</p>
								<p>Vous permet d'affecter manuellement des conducteurs à chacun des services générés par Eole.</p>
							</div>
						</div>
						<a href="<c:url value="/orion" />">
							<div class="panel-footer">
								<span class="pull-left">Accéder au module</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>

</body>
</html>