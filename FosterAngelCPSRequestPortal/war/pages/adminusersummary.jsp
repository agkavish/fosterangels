<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="com.fact.services.RequestService" %>
<%@ page import="com.fact.wps.model.RequestSummary" %>
<%@ page import="com.fact.wps.controller.AdminController" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="/../assets/ico/favicon.png">

    <title>Foster Angels' Request Site</title>
  
    <!-- Bootstrap core CSS -->
    <link href="/../dist/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/../css/signup.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="/../assets/js/html5shiv.js"></script>
      <script src="/../assets/js/respond.min.js"></script>
    <![endif]-->
  
  </head>
  
<script>
function generateFormAction() {
  var filtercriteria = "";
  var searchFieldName = document.adminuserfilter.fieldselect.value;
  var searchValue = document.adminuserfilter.searchentry.value;
       
  if(document.adminuserfilter.cwCheckbox.checked) {
    filtercriteria += "c"; 
  }
  if(document.adminuserfilter.staffCheckbox.checked){
    filtercriteria += "s";
  }
  if(document.adminuserfilter.adminCheckbox.checked){
    filtercriteria += "a";
  }
  
  if(searchValue == '') {
    searchValue = "*";
  }
  
  var formAction = "/admin/usersummary/" +  filtercriteria +"/" + searchFieldName +"/" + searchValue;
  document.adminuserfilter.action = formAction;
  document.adminuserfilter.submit();
}
</script>
  
    <%
    Profile uProfile= null;
	
	if(request.getSession().getAttribute("signinuser")!=null){
		uProfile = (Profile) request.getSession().getAttribute("signinuser");			
	} 	else {
		uProfile = new Profile();
	}
				
	Map<String, List<Profile>> reqSummaryMap = null;
	RequestService reqService = null;
	Boolean checkCW = true;
	Boolean checkStaff = true;
	Boolean checkAdmin = true;
	String searchField = "f";
	String searchValue = "";
	if (request.getAttribute("requests") != null){
		reqSummaryMap = (Map<String, List<Profile>>) request.getAttribute("requests");
		reqService = (RequestService) request.getAttribute("requestService");
		String filterBy = (String) request.getAttribute("filterBy");
		if (filterBy != null) {
			checkCW = filterBy.contains("c");
			checkStaff = filterBy.contains("s");
			checkAdmin = filterBy.contains("a");
		}
		searchField = (String) request.getAttribute("searchField");
		if (searchField == null)
			searchField = "firstName";
		searchValue = (String) request.getAttribute("searchValue");
		if (searchValue.equals("*"))
			searchValue = "";
	}
	
	%>


  <body>
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">

<div class="container"> 

    <img class="img-left" src="/../img/FA.png" alt="Foster Angles logo" height="30" width="30"><h4 class="headertext"> Foster Angels</h4>

</div>

<div class="container">
<ul class="nav nav-pills">
 <li><a href="/cpshome"><img class="" src="/../img/Home.png" alt="" height="20" width="20"></a></li>
  <li><a href="/admin/reqsummary">Requests</a></li>
  <li><a href="#">Users</a></li>
  <li><a href="/admin/reports">Reports</a></li>
  <li><a href="/cwrequest/add">Create Request</a></li>
  <li><a href="/profile/view/<%=KeyFactory.keyToString(uProfile.getKey())%>">Profile</a></li>
  <ul class="nav navbar-right">
      <li><a href="/signout">Sign Out</a></li>
      </ul>
  </ul>
  
<div id="wrap">

 
<label class="Alerts" for="AlertSection"> Users</label>
<div class="AlertSection">

<form name="adminuserfilter" class="form-inline" role="form" action="/admin/usersummary" method="get" onsubmit="return generateFormAction();" >

<div class="form-group">
<label for="checkboxes">Filter by:</label>

<label class="checkbox-inline" id="checkboxes">
  <input type="checkbox" id="cwCheckbox" value="c" <% if (checkCW){ %> checked="checked" <% } %> > CPS Workers
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="staffCheckbox" value="s" <% if (checkStaff){ %> checked="checked" <% } %> > Support Staff
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="adminCheckbox" value="a" <% if (checkAdmin){ %> checked="checked" <% } %> > Admins
</label>
</div>

<br></br>

<div class="form-group" >
<label for="fieldselect">Search Field:</label>
<select class="form-control small" id="fieldselect">
  <option value="firstName" <% if(searchField.equals("firstName")){ %> selected="selected" <% } %>  >First Name</option>
  <option value="lastName" <% if(searchField.equals("lastName")){ %> selected="selected" <% } %> >Last Name</option>
  <option value="unitNumber" <% if(searchField.equals("unitNumber")){ %> selected="selected" <% } %> >Unit Number</option>
  <option value="supervisor" <% if(searchField.equals("supervisor")){ %> selected="selected" <% } %> >Supervisor</option>
</select>

<label for="searchentry">By:</label>
    <input type="text" class="form-control small" id="searchentry" placeholder="a name" value="<%=searchValue%>">
</div>
<button type="submit" class="btn btn-primary">Filter + Search</button>
</form>
<br></br>
<div>


<%
List <Profile> profileReq = reqSummaryMap.get("cw");
if (profileReq != null && profileReq.size() > 0) {
%>
<label for="cps_workers">CPS Workers:</label>
<table class="table table-striped table table-condensed  table table-hover" id="adminstable">
<thead>
<tr>
<th>Name</th>
<th>Supervisor</th>
<th>Unit Number</th>
<th>email</th>
<th>Requests P/A/O</th>
</tr>
</thead>
<tbody>
<%
    for (Profile req : profileReq) {
        String name = req.getLastName() + ", " + req.getFirstName();
        String supervisorName = "";
        if (req.getSupervisorLastName() != null)
        	supervisorName =  req.getSupervisorLastName() + ", " + req.getSupervisorFirstName();
        String unitNum = "";
    	if (req.getUnitNumber() != null)
    		unitNum = req.getUnitNumber();
    	RequestSummary reqSummary = reqService.getRequestSummaryForUser(req.getEmail());
%>
<tr>
<td><a href="/admin/userdetails/<%=KeyFactory.keyToString(req.getKey())%>"><%= name %></a></td>
<td><%= supervisorName %></td>
<td><%= unitNum %></td>
<td><a href="mailto:<%= req.getEmail() %>"><%= req.getEmail() %></a></td>
<td><%= reqSummary.getPendingRequest() %> / <%= reqSummary.getApprovedRequest() %> / <%= reqSummary.getOverdueRequest() %></a></td>
</tr>
<%
    }
}
%>
</tbody>
</table>


<%
profileReq = reqSummaryMap.get("ss");
if (profileReq != null && profileReq.size() > 0) {
%>
<label for="support_staff">Support Staff:</label>
<table class="table table-striped table table-condensed  table table-hover" id="adminstable">
<thead>
<tr>
<th>Name</th>
<th>Supervisor</th>
<th>Unit Number</th>
<th>email</th>
<th>Requests P/A/O</th>
</tr>
</thead>
<tbody>
<%
    for (Profile req : profileReq) {
        String name = req.getLastName() + ", " + req.getFirstName();
        String supervisorName = "";
        if (req.getSupervisorLastName() != null)
        	supervisorName =  req.getSupervisorLastName() + ", " + req.getSupervisorFirstName();
        String unitNum = "";
    	if (req.getUnitNumber() != null)
    		unitNum = req.getUnitNumber();
    	RequestSummary reqSummary = reqService.getRequestSummaryForUser(req.getEmail());
%>
<tr>
<td><a href="/admin/userdetails/<%=KeyFactory.keyToString(req.getKey())%>"><%= name %></a></td>
<td><%= supervisorName %></td>
<td><%= unitNum %></td>
<td><a href="mailto:<%= req.getEmail() %>"><%= req.getEmail() %></a></td>
<td><%= reqSummary.getPendingRequest() %> / <%= reqSummary.getApprovedRequest() %> / <%= reqSummary.getOverdueRequest() %></a></td>
</tr>
<%
    }
}
%>
</tbody>
</table>


<%
profileReq = reqSummaryMap.get("fa");
if (profileReq != null && profileReq.size() > 0) {
%>
<label for="fact_admin">FACT Administrator:</label>
<table class="table table-striped table table-condensed  table table-hover" id="adminstable">
<thead>
<tr>
<th>Name</th>
<th>Supervisor</th>
<th>Unit Number</th>
<th>email</th>
<th>Requests P/A/O</th>
</tr>
</thead>
<tbody>
<%
    for (Profile req : profileReq) {
        String name = req.getLastName() + ", " + req.getFirstName();
        String supervisorName = "";
        if (req.getSupervisorLastName() != null)
        	supervisorName =  req.getSupervisorLastName() + ", " + req.getSupervisorFirstName();
        String unitNum = "";
    	if (req.getUnitNumber() != null)
    		unitNum = req.getUnitNumber();
    	RequestSummary reqSummary = reqService.getRequestSummaryForUser(req.getEmail());
%>
<tr>
<td><a href="/admin/userdetails/<%=KeyFactory.keyToString(req.getKey())%>"><%= name %></a></td>
<td><%= supervisorName %></td>
<td><%= unitNum %></td>
<td><a href="mailto:<%= req.getEmail() %>"><%= req.getEmail() %></a></td>
<td><%= reqSummary.getPendingRequest() %> / <%= reqSummary.getApprovedRequest() %> / <%= reqSummary.getOverdueRequest() %></a></td>
</tr>
<%
    }
}
%>
</tbody>
</table>

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
