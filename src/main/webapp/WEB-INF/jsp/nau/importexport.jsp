<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import="static alize.nau.commun.Constantes.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Nau</title>
<style>
.envoyer {
	text-align: center;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Importation / Exportation</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Importer
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<form method="POST" action="<c:url value="<%=URL_IMPORTER_EXPORTER %>" />" enctype="multipart/form-data">
                        		<div class="form-group">
                        			<label for="fichierImporte">Sélectionnez le fichier</label>
                        			<div>
										<input name="fichierImporte" type="file" data-filename-placement="outside" />
									</div>
								</div>
								<button class="btn btn-default" type="submit">Envoyer</button>
							</form>
                        </div>
                    </div>
				</div>
				<div class="col-lg-6">
					<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Exporter
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<form method="GET" action="<c:url value="<%=URL_IMPORTER_EXPORTER %>" />/exporter" enctype="multipart/form-data">
                        		<div class="form-group">
                        			<label for="exporter">Télécharger le réseau</label>
                        			<div>
                        				<button id="exporter" class="btn btn-default" type="submit">Envoyer</button>
                        			</div>
								</div>
								
							</form>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
			</div>
			<!-- /.row -->
		</div>

	</div>
	<!-- /#wrapper -->
	
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script src="<c:url value="/resources/js/plugins/bootstrap.file-input/bootstrap.file-input.js"/>"></script>
	<script type="text/javascript">
		$('input[type=file]').bootstrapFileInput();
		$('.file-inputs').bootstrapFileInput();
	</script>
	
</body>
</html>