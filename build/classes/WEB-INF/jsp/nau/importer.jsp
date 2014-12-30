<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>
<%@ page import=" static alize.nau.commun.Constantes.URL_IMPORTER"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZ� - Nau</title>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Importer un r�seau</h1>
					<form method="POST" action="<c:url value="<%=URL_IMPORTER %>" />" enctype="multipart/form-data">
						<input name="fichierImporte" type="file" />
						<input class="btn btn-primary" type="submit" value="Envoyer" />
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