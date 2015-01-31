<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@page import="com.fact.common.RequestStatusTypes"%>
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="com.fact.wps.model.AdminReport" %>
<%@ page import="com.fact.common.ReportTypes" %>
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
    <link href="./../dist/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="./../css/signup.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
    <script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>  
  
<script type='text/javascript'> 
$(window).load(function(){
$(document).ready(function() {
    $("#btnExport").click(function(e) {
        //getting values of current time for generating the file name
        var dt = new Date();
        var day = dt.getDate();
        var month = dt.getMonth() + 1;
        var year = dt.getFullYear();
        var hour = dt.getHours();
        var mins = dt.getMinutes();
        var postfix = day + "." + month + "." + year + "_" + hour + "." + mins;
        //creating a temporary HTML link element (they support setting file names)
        var a = document.createElement('a');
        //getting data from our div that contains the HTML table
        var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('dvData');
        var table_html = table_div.outerHTML.replace(/ /g, '%20');
        a.href = data_type + ', ' + table_html;
        //setting the file name
        a.download = 'exported_table_' + postfix + '.xls';
        //triggering the function
        a.click();
        //just in case, prevent default behaviour
        e.preventDefault();
    });
});
});  
$(function() {
	$('#year').selectedYear(function() {
		this.form.submit();
	});
});

$(function(){
    $("#dropdown").change( function(e) {
        this.form.submit();
    });
});

$(function(){
    $('#testform').on( "change", "#dropdown_year", function(e) {
        this.form.submit();
    });
});
</script>

</head>

    <%
    List<AdminReport> reportList = null;
    Profile uProfile= null;
	
	if(request.getSession().getAttribute("signinuser")!=null){
	
		uProfile = (Profile) request.getSession().getAttribute("signinuser");			
	} 	else {
		uProfile = new Profile();
	}
				
		Map <String, List<AdminReport>> adminReports = null;
		if (request.getAttribute("reports") != null){
			adminReports = (Map<String, List<AdminReport>>) request.getAttribute("reports");		
		}
	%>


  <body>
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">

<div class="container"> 

    <img class="img-left" src="./../img/FA.png" alt="Foster Angles logo" height="30" width="30"><h4 class="headertext"> Foster Angels</h4>

</div>

<div class="container">
<ul class="nav nav-pills">
 <li><a href="/cpshome"><img class="" src="./../img/Home.png" alt="" height="20" width="20"></a></li>
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

 
<label class="Alerts" for="AlertSection">Reports</label>
<div class="AlertSection">

<form class="form-inline" role="form">

<div class="form-group" >

<div class="form-group">
<label for="checkboxes">Select:</label>

<label class="checkbox-inline" id="filterby">
  <input type="checkbox" id="all" value="all"> All
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="countyamount" value="countyamount"> County and Amount
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="categoryamount" value="categoryamount"> Category and Amount
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="countyrequests" value="countyrequests"> County and Number of requests
</label>
<label class="checkbox-inline">
  <input type="checkbox" id="categoryrequests" value="categoryrequests"> Category and Number of requests
</label>
</div>

<form action="/admin/reports" method="post">
<label for="year">Select Year</label>
<!--     -->
<select name="SELECTED_YEAR" class="form-control small" id="SELECTED_YEAR" title="Select the year" onchange="this.form.submit()">
 <%
   Calendar c = Calendar.getInstance();
	int cYear = c.get(Calendar.YEAR);
	int optionValue = cYear;	
	String selectedYr = request.getParameter("SELECTED_YEAR");
	for (int i = 0 ; i < 5; i++){
	  optionValue = cYear - i;
	  if(selectedYr != null && selectedYr.equals(String.valueOf(optionValue))){ %>
	  <option selected value="<%= optionValue %>"><%= optionValue %></option>
<%	  } else{
 %>
   <option value="<%= optionValue %>"><%= optionValue %></option>

 <%
 }
 }
 
 %>
 <!-- <option selected value="none"></option>
   -->
</select>


</form>
 <br></br>
<!--
<label for="searchentry">By:</label>
    <input type="text" class="form-control small" id="searchentry" placeholder="a name">
-->
</div>
</form>
<%if (request.getParameter("SELECTED_YEAR") != null) { %>
<label>REPORTS FOR YEAR "<%= request.getParameter("SELECTED_YEAR") %>"</label>
<% } %>
<div id="dvData">
<label for="countyamount">County and Amount:</label>
<table class="table table-condensed table table-hover" id="countyamount">
<thead>
<tr>
<th>County Name</th>
<th>Jan</th>
<th>Feb</th>
<th>Mar</th>
<th>Apr</th>
<th>May</th>
<th>Jun</th>
<th>Jul</th>
<th>Aug</th>
<th>Sep</th>
<th>Oct</th>
<th>Nov</th>
<th>Dec</th>
<th>Total</th>
</tr>
</thead>
<tbody>
<%
reportList = adminReports.get(ReportTypes.COUNTYANDAMOUNT.toString());

if(reportList != null)
{
	for (AdminReport report : reportList) 
	{
%>
<tr class="pending">
<td><%= report.getCountyName() %></td>
<% 
		for (int i=0; i<12; i++)
		{
%>
<td><%= report.getMonthlyAmount().get(i) %></td>
<%  	}
%> 

  <td><%= report.getYearTotalAmount() %></td>
<%
	}
}
%>
</tbody>
</table>

<label for="categoryamount">Category and Amount:</label>
<table class="table table-condensed table table-hover" id="categoryamount">
<thead>
<tr>
<th>Category Name</th>
<th>Jan</th>
<th>Feb</th>
<th>Mar</th>
<th>Apr</th>
<th>May</th>
<th>Jun</th>
<th>Jul</th>
<th>Aug</th>
<th>Sep</th>
<th>Oct</th>
<th>Nov</th>
<th>Dec</th>
<th>Total</th>
</tr>
</thead>
<tbody>
<%
reportList = adminReports.get(ReportTypes.CATEGORYANDAMOUNT.toString());

if(reportList != null)
{
	for (AdminReport report : reportList) 
	{
%>
<tr class="pending">
<td><%= report.getCategoryName() %></td>
<% 
		for (int i=0; i<12; i++)
		{
%>
<td><%= report.getMonthlyAmount().get(i) %></td>
<%  	} %>
<td><%= report.getYearTotalAmount() %></td>
<%
	}
}
%>
</tbody>
</table>

<label for="countyrequests">County and Number of Requests:</label>
<table class="table table-condensed table table-hover" id="countyrequests">
<thead>
<tr>
<th>County Name</th>
<th>Jan</th>
<th>Feb</th>
<th>Mar</th>
<th>Apr</th>
<th>May</th>
<th>Jun</th>
<th>Jul</th>
<th>Aug</th>
<th>Sep</th>
<th>Oct</th>
<th>Nov</th>
<th>Dec</th>
<th>Total</th>
</tr>
</thead>
<tbody>
<%
reportList = adminReports.get(ReportTypes.COUNTYANDNUMBEROFREQUESTS.toString());

if(reportList != null)
{
	for (AdminReport report : reportList) 
	{
%>
<tr class="pending">
<td><%= report.getCountyName() %></td>
<% 
		for (int i=0; i<12; i++)
		{
%>
<td><%= report.getMonthlyCount().get(i) %></td>
<%  	}
%>
 <td><%= report.getTotalMonthlyCount() %></td>

<%
	}
}
%>
</tbody>
</table>

<label for="categoryrequests">Category and Number of Requests:</label>
<table class="table table-condensed table table-hover" id="categoryrequests">
<thead>
<tr>
<th>Category Name</th>
<th>Jan</th>
<th>Feb</th>
<th>Mar</th>
<th>Apr</th>
<th>May</th>
<th>Jun</th>
<th>Jul</th>
<th>Aug</th>
<th>Sep</th>
<th>Oct</th>
<th>Nov</th>
<th>Dec</th>
<th>Total</th>
</tr>
</thead>
<tbody>
<%
reportList = adminReports.get(ReportTypes.CATEGORYANDNUMBEROFREQUESTS.toString());

if(reportList != null)
{
	for (AdminReport report : reportList) 
	{
%>
<tr class="pending">
<td><%= report.getCategoryName() %></td>
<% 
		for (int i=0; i<12; i++)
		{
%>
<td><%= report.getMonthlyCount().get(i) %></td>
<%  	}
%>
<td><%= report.getTotalMonthlyCount() %></td>
<%
	}
}
%>
</tbody>
</table>
</div>

<button id="btnExport">Click for Save</button>

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
