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

   

 
  
  <script>
  	function generateFormAction(){
  		var pending = '';
  		var approved = '';
  		var overdue = '';
  		var denied = '';
  		var closed = '';
  		var deliverd = '';
  	
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
	   
	   if(document.reqfilter.delivered.checked){
	   		delivered = document.reqfilter.delivered.value;
		}
	   
	   var filtercriteria = pending + approved + overdue + denied + closed + delivered;
	   
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
	    Boolean checkDelivered = true;

	    if (filter != null) {
	    	checkPending = filter.contains("p");
	    	checkApproved = filter.contains("a");
	    	checkOverdue = filter.contains("o");
	    	checkClosed = filter.contains("c");
	    	checkDenied = filter.contains("d");
	    	checkDelivered = filter.contains("e");

     }
		
	%>


  <body>
  
   <%
        String exportToExcel = request.getParameter("exportToExcel");
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "requests.xls");
 
        }
    %>
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">


<div class="container">




  
   <div id="wrap">

 

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
<td><%= req.getRequestNumber() %></td>
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
<td><%= req.getRequestNumber() %></td>
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
<label for="overduetable">Overdue Requests:</label>
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
<th>Delivered Date</th>
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
<td><%= req.getRequestNumber() %></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
<td><%= req.getDeliveredDateString() %></td>
</tr>
<%
	} %>
</tbody>
</table>
	
	<%
	}
%>
<%
List <CWRequest> deliveredReq = reqSummaryMap.get("delivered");
if(deliveredReq != null && deliveredReq.size() > 0){
	
	%> 
<label for="deliveredtable">Delivered Requests:</label>
<table class="table table-condensed table table-hover" id="deliveredtable">
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
<th>Delivered Date</th>
</tr>
</thead>
<tbody>	
	<%

	for (CWRequest req : deliveredReq) {
		String receiptYN = "NO";
		if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
			receiptYN = "Yes";
		}
%>

<tr class="warning">
<td><%= req.getRequestNumber() %></td>
<td><%= req.getChildName() %></td>
<td><%= req.getRequestedDateString() %></td>
<td><%= req.getRequestorName() %></td>
<td><%= req.getRequestType() %></td>
<td><%= receiptYN %></td>
<td><%= req.getPurchasedAmount() %></td>
<td><%= req.getRequestedVendor() %></td>
<td><%= req.getDeliveredDateString() %></td>

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
<td><%= req.getRequestNumber() %></td>
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
<td><%= req.getRequestNumber() %></td>
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
