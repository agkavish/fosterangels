<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Foster Angels' Request Site</title>

    <!-- Bootstrap core CSS -->
    <link href="/../dist/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/../css/signup.css" rel="stylesheet">

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
		
		List<CWRequest> results = null;
		if (request.getAttribute("requestList") != null){
			results = (List<CWRequest>) request.getAttribute("requestList");
		} else {
			results = new ArrayList<CWRequest>();
		}
	%>


  <body>
  
<div class="container col-lg-4 col-md-6 col-sm-8 col-xs-12">

<div class="container"> 

    <img class="img-left" src="/../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext"> Foster Angels</h4>

</div>

<div class="container">
<ul class="nav nav-pills">
 <li><a href="/cpshome"><img class="" src="/../img/Home.png" alt="" height="20" width="20"></a></li>
  <li><a href="/profile/view/<%=KeyFactory.keyToString(uProfile.getKey())%>">Profile</a></li>
  <li><a href="/cwrequest/summary">Requests</a></li>
  <ul class="nav navbar-right"> <li><a href="/signout">Signout</a></li> </ul>
</ul>





  
   <div id="wrap">

 
<label class="Alerts" for="AlertSection"><a href="/cwrequest/summary" class="alert-link">Requests</a> / Closed</label>

<div class="AlertSection">

<%
for (CWRequest req:results) {

%>
<div class="alert alert-closed"> <a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>" class="alert-link">Request *<%=req.getChildName() %>* (*<%=req.getRequestNumber() %>*) *<%=req.getRequestedDateString()%>*</a> </div>
<%
}
%>


</div>

<div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-block " type="button" onclick="location.href='/cwrequest/summary'" >Back</button>
  </div>
   	 

 <div class="container">   
     <div id="footer">
     <br />
        <h6 class="text-center"> For questions or problems please contact Foster Angels at</h6>
          <h6 class="text-center"> <a class="text-center" href="mailto:info@fosterangelsctx.org">info@fosterangelsctx.org</a></h6>
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
