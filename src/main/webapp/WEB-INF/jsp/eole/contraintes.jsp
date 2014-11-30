<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import=" static alize.eole.constante.Contraintes.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Eole</title>
<style>
	.labelLeft {
		text-align: right;
		float: left;
		margin-top: 6px;
		margin-right: 1em;
	}
	.inputright {
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857;
		color: #555;
		background-color: #FFF;
		background-image: none;
		border: 1px solid #CCC;
		border-radius: 4px;
		box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
		transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
	}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Contraintes</h1>
					<form method="post" action="/alize/eole/contraintes">
						<div class="panel panel-default">
							<div class="panel-heading">Edition</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<div class="form-group">
											<label for="<%=NB_VEHICULES_MAX_LABEL %>">Nombre de véhicules maximum</label>
											<input class="form-control" type="number" name="<%=NB_VEHICULES_MAX_LABEL %>" id="<%=NB_VEHICULES_MAX_LABEL %>" value="<%=request.getAttribute("nbVehiculesMax") %>" />
										</div>
										<div class="form-group">
											<label for="<%=TEMPS_TRAVAIL_MAX_LABEL %>">Temps de travail maximum légal par jour</label>
											<input class="form-control" type="time" name="<%=TEMPS_TRAVAIL_MAX_LABEL %>" id="<%=TEMPS_TRAVAIL_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsTravailMax") %>" />
										</div>
									</div>
									<div class="col-lg-6">
										<div class="form-group">
											<label for="<%=TEMPS_CONDUITE_MAX_LABEL %>">Temps de conduite maximum légal</label>
											<input class="form-control" type="time" name="<%=TEMPS_CONDUITE_MAX_LABEL %>" id="<%=TEMPS_CONDUITE_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsConduiteMax") %>" />
										</div>
										<div class="form-group">
											<div class="row">
												<div class="col-lg-12">
													<label>Temps de pause</label>
												</div>
											</div>
											<div class="row">
												<div class="col-lg-6">
													<label class="labelLeft" for="<%=TEMPS_PAUSE_MIN_LABEL %>">Min</label>
													<input class="inputright" type="time" name="<%=TEMPS_PAUSE_MIN_LABEL %>" id="<%=TEMPS_PAUSE_MIN_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsPauseMin") %>" />
												</div>
												<div class="col-lg-6">
													<label class="labelLeft" for="<%=TEMPS_PAUSE_MAX_LABEL %>">Max</label>
													<input class="inputright" type="time" name="<%=TEMPS_PAUSE_MAX_LABEL %>" id="<%=TEMPS_PAUSE_MAX_LABEL %>" placeholder="hh:mm" value="<%=request.getAttribute("tempsPauseMax") %>" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<input type="submit" class="btn btn-default" value="Envoyer" />
					</form>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
	
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	
</body>
</html>