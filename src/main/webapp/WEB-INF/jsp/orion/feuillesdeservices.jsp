<%@ include file="/WEB-INF/jsp/commun/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/commun/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AliZé - Orion</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/plugins/datepicker/datepicker3.css"/>" />
<style>
a {
	color: #A800FF;
}
a:hover, a:focus {
    color: #A57EFF;
}
.datepicker {
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body>
	<div id="wrapper">

		<%@ include file="/WEB-INF/jsp/commun/nav.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Feuilles de service</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4">
                   <div class="panel panel-default">
                       <div class="panel-heading">
                           Kitchen Sink
                       </div>
                       <!-- /.panel-heading -->
                       <div class="panel-body">
                           <div class="table-responsive">
                               <table class="table table-striped table-bordered table-hover">
                                   <thead>
                                       <tr>
                                           <th>#</th>
                                           <th>First Name</th>
                                           <th>Last Name</th>
                                           <th>Username</th>
                                       </tr>
                                   </thead>
                                   <tbody>
                                       <tr>
                                           <td>1</td>
                                           <td>Mark</td>
                                           <td>Otto</td>
                                           <td>@mdo</td>
                                       </tr>
                                       <tr>
                                           <td>2</td>
                                           <td>Jacob</td>
                                           <td>Thornton</td>
                                           <td>@fat</td>
                                       </tr>
                                       <tr>
                                           <td>3</td>
                                           <td>Larry</td>
                                           <td>the Bird</td>
                                           <td>@twitter</td>
                                       </tr>
                                   </tbody>
                               </table>
                           </div>
                       </div>
                   </div>
               </div>
               <div class="col-lg-4">
                   <div class="panel panel-default">
                       <div class="panel-heading">
                           Kitchen Sink
                       </div>
                       <!-- /.panel-heading -->
                       <div class="panel-body">
                           <div class="table-responsive">
                               <table class="table table-striped table-bordered table-hover">
                                   <thead>
                                       <tr>
                                           <th>#</th>
                                           <th>First Name</th>
                                           <th>Last Name</th>
                                           <th>Username</th>
                                       </tr>
                                   </thead>
                                   <tbody>
                                       <tr>
                                           <td>1</td>
                                           <td>Mark</td>
                                           <td>Otto</td>
                                           <td>@mdo</td>
                                       </tr>
                                       <tr>
                                           <td>2</td>
                                           <td>Jacob</td>
                                           <td>Thornton</td>
                                           <td>@fat</td>
                                       </tr>
                                       <tr>
                                           <td>3</td>
                                           <td>Larry</td>
                                           <td>the Bird</td>
                                           <td>@twitter</td>
                                       </tr>
                                   </tbody>
                               </table>
                           </div>
                       </div>
                   </div>
               </div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Sélection du jour
						</div>
						<div class="panel-body">
							<div id="datepicker"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- /#wrapper -->
	
	<%@ include file="/WEB-INF/jsp/commun/scripts.jsp"%>
	<script src="<c:url value="/resources/js/plugins/editablegrid/editablegrid.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/datepicker/bootstrap-datepicker.js"/>"></script>
	<script type="text/javascript">
	var dateSelectionnee;
	
    $('#datepicker').datepicker({
        weekStart: 1,
        language: "fr",
        calendarWeeks: true,
        todayHighlight: true
        }).on('changeDate', function(e){
	        console.log(e);
	        dateSelectionnee = e.date;
			getServices();
	    });
	
	function getServices() {
	    $.ajax({
	    	url: "/alize/orion/feuillesdeservices/get",
	    	type: "POST",
	    	data: "date=" + dateSelectionnee.toLocaleString(),
	    	success: function(str) {
   		    	console.log(str);
	    	}
	    });
	}

	</script>
	
</body>
</html>