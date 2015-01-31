<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.fact.common.RequestStatusTypes"%>
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
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
  
 
  
  <script>
  	function generateFormAction(){
  		var pending = '';
  		var approved = '';
  		var overdue = '';
  		var denied = '';
  		var closed = '';
  	
  		if(document.reqfilter.pending.checked) {
  			pending = document.reqfilter.pending.value;	
  		}
	   if(document.reqfilter.approved.checked){
	   		approved = document.reqfilter.approved.value;
  		}
	   
	   if(document.reqfilter.overdue.checked){
	   		overdue = document.reqfilter.overdue.value;
 		}
	   
	   
	   if(document.reqfilter.denied.checked){
	   		denied = document.reqfilter.denied.value;
 		}
	   
	   if(document.reqfilter.closed.checked){
	   		closed = document.reqfilter.closed.value;
 		}
	   
	   var filtercriteria = pending + approved + overdue + denied + closed;
	   
	   var searchFieldName = document.reqfilter.fieldselect.value;
	   var searchValue = document.reqfilter.searchentry.value;
	   if(searchValue == '') {
		   searchValue = "*";
	   }
	  //alert("searchFieldName - " + searchFieldName);
	  //alert("searchValue- " + searchValue);
	  var formAction = "/admin/reqsummary/" +  filtercriteria +"/" + searchFieldName +"/" + searchValue;
	  //alert("formAction- " + formAction);
	  document.reqfilter.action = formAction;
	  document.reqfilter.submit();
  	}
  </script>
  
   </head>
  
    <%
    Profile uProfile= null;
	
	if(request.getSession().getAttribute("signinuser")!=null){
	
		uProfile = (Profile) request.getSession().getAttribute("signinuser");			
	} 	else {
		uProfile = new Profile();
	}
				
		Map<String, List<CWRequest>> reqSummaryMap = null;
		if (request.getAttribute("requests") != null){
			reqSummaryMap = (Map<String, List<CWRequest>>) request.getAttribute("requests");
		} 
		
		// read back filter parameters if available
		String filter = "";
		if (request.getAttribute("filter") != null){
			filter = (String) request.getAttribute("filter");
		}
		
		String searchField = "";
		if (request.getAttribute("searchField") != null){
			searchField = (String) request.getAttribute("searchField");
		}
		
		String searchValue = "";
		if (request.getAttribute("searchValue") != null){
			searchValue = (String) request.getAttribute("searchValue");
		}
		
		Boolean checkPending = true;
	    Boolean checkApproved = true;
	    Boolean checkOverdue = true;
	    Boolean checkClosed = true;
	    Boolean checkDenied = true;

	    if (filter != null) {
	    	checkPending = filter.contains("p");
	    	checkApproved = filter.contains("a");
	    	checkOverdue = filter.contains("o");
	    	checkClosed = filter.contains("c");
	    	checkDenied = filter.contains("d");
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
  <li><a href="/admin/usersummary">Users</a></li>
  <li><a href="/admin/reports">Reports</a></li>
  <li><a href="/cwrequest/add">Create Request</a></li>
  <li><a href="/profile/view/<%=KeyFactory.keyToString(uProfile.getKey())%>">Profile</a></li>
  <ul class="nav navbar-right">
      <li><a href="/signout">Sign Out</a></li>
      </ul>
</ul>




  
   <div id="wrap">

 
<label class="Alerts" for="AlertSection"> Requests</label>
<div class="AlertSection">

<form class="form-inline" role="form" name="reqfilter" method="get" action="#" onsubmit="return generateFormAction();">

<div class="form-group">
<label for="checkboxes">Filter by:</label>

<label class="checkbox-inline" id="filerby">
  <input type="checkbox" id="pending" value="p" <% if (checkPending){ %> checked="checked" <% } %>> Pending
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="approved" value="a" <% if (checkApproved){ %> checked="checked" <% } %>> Approved
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="overdue" value="o" <% if (checkOverdue){ %> checked="checked" <% } %>> Delivered
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="denied" value="d" <% if (checkDenied){ %> checked="checked" <% } %>> Denied
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="closed" value="c" <% if (checkClosed){ %> checked="checked" <% } %>> Closed
</label>
</div>

 <br></br>

<div class="form-group" >
<label for="fieldselect">Search Field:</label>
<select class="form-control small" id="fieldselect">
  <option value="childName">Child's First Name</option>
  <option value ="requestorFirstName">Case Worker</option>
  <option value ="pid">Identification Number</option>
  <option value ="requestedVendor">Vendor</option>
<!-- <option value ="supervisor">Supervisor</option>  -->  
</select>


<label for="searchentry">By:</label>
    <input type="text" class="form-control small" id="searchentry" placeholder="a name" value = "<%= searchValue %>">

</div>

<button type="submit" class="btn btn-primary">Filter + Search</button>

</form>

<br></br>

<div>


<%
List <CWRequest> pendingReq = reqSummaryMap.get("adminpending");
if(pendingReq != null && pendingReq.size() > 0){
	%>
	<label for="pendingtable">Pending Requests:</label>
<table class="table table-condensed table table-hover" id="pendingtable">
<thead>
<tr>
<th>Request Number </th>
<th>Child's Name</th>
<th>Request Date</th>
<th>Requested By</th>
<th>Request Type</th>
<th>Receipt</th>
<th>Amount</th>
<th>Vendor</th>
</tr>
</thead>
<tbody>
	<% 
	for (CWRequest req : pendingReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
			receiptYN = "Yes";
		}
%>
<tr class="pending">
<td><a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>">#<%= req.getRequestNumber() %></a></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
</tr>

<%
	} %> 
	</tbody>
	</table>
	<%
	}
%>

<%
List <CWRequest> approvedReq = reqSummaryMap.get("approved");
if(approvedReq != null && approvedReq.size() > 0){
	%> 
<label for="approvedtable">Approved Requests:</label>
<table class="table table-condensed table table-hover" id="approvedtable">
<thead>
<tr>
<th>Request Number </th>
<th>Child's Name</th>
<th>Request Date</th>
<th>Requested By</th>
<th>Request Type</th>
<th>Receipt</th>
<th>Amount</th>
<th>Vendor</th>
</tr>
</thead>
<tbody>	
	
	<%
	
	for (CWRequest req : approvedReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
			receiptYN = "Yes";
		}
%>

<tr class="success">
<td><a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>">#<%= req.getRequestNumber() %></a></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
</tr>

<%
	} %>
	
</tbody>
</table>	
	<%}
%>




<%
List <CWRequest> overdueReq = reqSummaryMap.get("overdue");
if(overdueReq != null && overdueReq.size() > 0){
	
	%> 
<label for="overduetable">Delivered Requests:</label>
<table class="table table-condensed table table-hover" id="overduetable">
<thead>
<tr>
<th>Request Number </th>
<th>Child's Name</th>
<th>Request Date</th>
<th>Requested By</th>
<th>Request Type</th>
<th>Receipt</th>
<th>Amount</th>
<th>Vendor</th>
</tr>
</thead>
<tbody>	
	<%

	for (CWRequest req : overdueReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
			receiptYN = "Yes";
		}
%>

<tr class="warning">
<td><a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>">#<%= req.getRequestNumber() %></a></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
</tr>
<%
	} %>
</tbody>
</table>
	
	<%
	}
%>



<%
List <CWRequest> deniedReq = reqSummaryMap.get("denied");
if(deniedReq != null && deniedReq.size() > 0){ 
	%>
	<label for="deniedtable">Denied Requests (last 90 days): </label>
<table class="table table-condensed table table-hover" id="deniedtable">
<thead>
<tr>
<th>Request Number </th>
<th>Child's Name</th>
<th> Request Date</th>
<th>Requested By</th>
<th>Request Type</th>
<th>Receipt</th>
<th>Amount</th>
<th>Vendor</th>
</tr>
</thead>
<tbody>
	<%
	for (CWRequest req : deniedReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
			receiptYN = "Yes";
		}
%>

<tr class="danger">
<td><a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>">#<%= req.getRequestNumber() %></a></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
</tr>

<%
	} %>
<tr>
<td> <a href="/admin/list/denied/0">Show All</a></td>
</tr>

</tbody>
</table>	
	<%
	}
%>




<%
List <CWRequest> closedReq = reqSummaryMap.get("closed");
if(closedReq != null && closedReq.size() > 0){
%>
<label for="closedtable">Closed Requests (last 60 days): </label>
<table class="table table-condensed table table-hover" id="closedtable">
<thead>
<tr>
<th>Request Number </th>
<th>Child's Name</th>
<th>Request Date</th>
<th>Requested By</th>
<th>Request Type</th>
<th>Receipt</th>
<th>Amount</th>
<th>Vendor</th>
</tr>
</thead>
<tbody>

<% 
	for (CWRequest req : closedReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel()) ||
				req.getStatus().equalsIgnoreCase(RequestStatusTypes.CLOSED.getLabel())){
			receiptYN = "Yes";
		}
%>

<tr class="closed">
<td><a href="/cwrequest/details/<%=KeyFactory.keyToString(req.getKey())%>">#<%= req.getRequestNumber() %></a></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
</tr>


<%
	} %>
	
<tr>
<td> <a href="/admin/list/closed/-90">Show Last 90 days</a></td>
<td> <a href="/admin/list/closed/-180">Show Last 180 days</a></td>
<td> <a href="/admin/list/closed/0">Show All</a></td>
</tr>
</tbody>
</table>	
	<%
	}
%>
</div>

</div>



    	 

 <div class="navbar navbar-fixed-bottom">   
     <div id="footer">
       <h6 class="text-center"> For questions or problems please contact Foster Angels at</h6>
         <h6 class="text-center"> <a class="text-center" href="mailto:info@fosterangelsctx.org">info@fosterangelsctx.org</a> </h6>
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
