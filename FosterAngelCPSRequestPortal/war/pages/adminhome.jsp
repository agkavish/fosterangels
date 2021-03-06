<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.AdminSummary" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="./../assets/ico/favicon.png">

    <title>Foster Angels' Request Site</title>
    
    <!-- Bootstrap core CSS -->
    <link href="./../dist/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="./../css/signup.css" rel="stylesheet">


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
  
  </head>

<%

		Profile uProfile= null;
	
		if(request.getSession().getAttribute("signinuser")!=null){
		
			uProfile = (Profile) request.getSession().getAttribute("signinuser");			
		} 	else {
			uProfile = new Profile();
		}
		

	
		AdminSummary adminSummary = null;
		if (request.getAttribute("adminsummary") != null){
			adminSummary = (AdminSummary) request.getAttribute("adminsummary");
		}
%>


 <body>
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">

<div class="container"> 

    <img class="img-left" src="./../img/FA.png" alt="Foster Angles logo" height="30" width="30"><h4 class="headertext"> Foster Angels</h4>

</div>

<div class="container">
<ul class="nav nav-pills">
 <li><a href="#"><img class="" src="./../img/Home.png" alt="" height="20" width="20"></a></li>

   <li><a href="/admin/reqsummary">Requests</a></li>
  <li><a href="/admin/usersummary">Users</a></li>
  <li><a href="/admin/reports">Reports</a></li>
  <li><a href="/cwrequest/add">Create Request</a></li>
  <li><a href="/profile/view/<%=KeyFactory.keyToString(uProfile.getKey())%>">Profile</a></li>
  <ul class="nav navbar-right">
      <li><a href="/signout">Sign Out</a></li>
      </ul>
</ul>




  
   <div id="wrap">

 
<label class="Alerts" for="AlertSection"> New Activity</label>
<div class="AlertSection">

<ul class="list-group col-lg-6 col-md-8 col-sm-10 col-xs-12">
    <li class="list-group-item">
    <span class="badge warrning"><%= adminSummary.getNewRequests() %></span>
    <a href="/admin/reqsummary/p">New Requests</a>
  </li>
    <li class="list-group-item">
    <span class="badge pending"><%= adminSummary.getNewCaseWorkers() %></span>
    <a href="/admin/usersummary/cw">New CPS Workers</a>
  </li>
      <li class="list-group-item">
    <span class="badge pending"><%= adminSummary.getNewSupportStaff() %></span>
   <a href="/admin/usersummary/ss">New Support Staff</a>
  </li>
    <li class="list-group-item">
    <span class="badge warrning"><%= adminSummary.getOverdueRequests() %></span>
    <a href="/admin/reqsummary/o">Overdue Requests</a>
  </li> 
</ul>



</div>

    	 

 <div class="navbar navbar-fixed-bottom">   
     <div id="footer">
        <h6 class="text-center"> For questions or problems please contact foster angles at</h6>
          <h6 class="text-center"> <a href="info@fosterangelsctx.org">info@fosterangelsctx.org</a></h6>
      </div> 
    </div>
</div>
  </div>
     </div>
     <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>
