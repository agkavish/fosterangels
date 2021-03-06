<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@page import = "com.fact.common.RequestStatusTypes" %>
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
       	String supervisorApproval="";
       	String fosterAngelsApproval="";
       	String attachReceipts="";
       	String closeOut = "";
	
		if(request.getSession().getAttribute("signinuser")!=null){
		
			uProfile = (Profile) request.getSession().getAttribute("signinuser");			
		} 	else {
			uProfile = new Profile();
		}
		
		CWRequest cwRequest = null;
		if(request.getSession().getAttribute("cwrequestinuse")!=null){
			
			cwRequest = (CWRequest) request.getSession().getAttribute("cwrequestinuse");			
		} 	else {
			cwRequest = new CWRequest();
		}
		
		switch(RequestStatusTypes.valueOf(cwRequest.getStatus().toUpperCase()))
		{
		case NEW: case PENDINGSUPERVISORAPPROVAL:
			supervisorApproval = "Pending";
			fosterAngelsApproval = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		case PENDINGFACTAPPROVAL:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		case APPROVED: case PENDINGRECEIPTS:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;			
		case DELIVERED: case CLOSED:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			attachReceipts = "Complete";
			closeOut = "Complete";
			break;	
		case RECEIPTAVAILABLE:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			attachReceipts = "Complete";
			closeOut = "Pending";
			break;	
		case DENIED:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Denied";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;	
		case SUPERVISORDENIED:
			supervisorApproval = "Denied";
			fosterAngelsApproval = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;		
		default:
			supervisorApproval = "Pending";
			fosterAngelsApproval = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
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

 
<label class="Alerts" for="AlertSection"><a href="/cwrequest/summary" class="alert-link">Requests</a> / <a href="/cwrequest/list/pending" class="alert-link">Pending</a> / Request <%= cwRequest.getRequestNumber() %></label>

<div class="AlertSection">


<label class="Alerts" for="AlertSection">Child's Information</label>
<ul class="list-group">
  <li class="list-group-item">First Name: <%= cwRequest.getChildName() %></li>
  <li class="list-group-item">Age: <%= cwRequest.getAge() %></li>
  <li class="list-group-item">PID: <%= cwRequest.getPid() %></li>
  <li class="list-group-item">Gender: <%= cwRequest.getGender() %></li>
  <li class="list-group-item"># Child in request: <%= cwRequest.getNumOfChildernSupported() %></li>
  <li class="list-group-item"># Times family has used: <%= cwRequest.getNumberOfTimesUsedFact() %></li>
    <li class="list-group-item">County: <%= cwRequest.getCountyInRegion7() %></li>
 </ul>  
 
 <label class="Alerts" for="AlertSection">Request Information</label>   
      <ul class="list-group">
        <li class="list-group-item">Date Needed: <%= cwRequest.getRequestedDateString() %></li>
          <li class="list-group-item">Amount: $<%= cwRequest.getReqestedAmount() %></li>
          <li class="list-group-item">Vendor: <%= cwRequest.getRequestedVendor() %></li>
          <li class="list-group-item">Description: <%= cwRequest.getRequestDescription() %></li>

   </ul>         
          
<label for="cpsworkerstable">Steps:</label>
<table class=" table table-condensed  table table-striped table table-hover" id="cpsworkerstable">

<thead>
<tr>
<th>Step</th>
<th>Status</th>
</tr>
</thead>
<tbody>
<tr>
<td>Supervisor approval</td>
<td><%= supervisorApproval %></td>
</tr>
<tr>
<td>Foster Angels Approval</td>
<td><%= fosterAngelsApproval %></td>
</tr>
<tr>
<td>Attach Receipt(s)</td>
<td><%= attachReceipts %></td>
</tr>
<tr>
<td>Closeout</td>
<td><%= closeOut %></td>
</tr>
</tbody>
</table>



</div>

<% 
	if (cwRequest.getStatus().equals(RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel())) {
%>
 <div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-block " type="button" onclick="location.href='/cwrequest/edit'">Edit Request</button>
  </div>   
  <%
	}
  %>
  <br />  
  <div class="container pagination-centered">
  <button class="btn btn-lg btn-defult btn-block " type="button" onclick="location.href='/cwrequest/list/pending'">Back</button>
  </div>
   	 

 <div class="container">   
     <div id="footer">
     <br />
        <h6 class="text-center"> For questions or problems please contact Foster Angels at</h6>
          <h6 class="text-center"><a class="text-center" href="mailto:info@fosterangelsctx.org">info@fosterangelsctx.org</a></h6>
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
