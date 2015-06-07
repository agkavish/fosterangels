<%@page import="com.fact.common.RequestStatusTypes"%>
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

 <script src="./../js/validations/request-form-validation.js"></script>  
 
  <%
		Profile uProfile= null;
	
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
		
		
	%>
 
 <script>
		function setSelectedIndex(s, valsearch)
		{
		// Loop through all the items in drop down list
		for (i = 0; i< s.options.length; i++)
		{ 
		if (s.options[i].value == valsearch)
		{
		// Item is found. Set its property and exit
		s.options[i].selected = true;
		break;
		}
		}
		return;		
		}
		
		function setDropDownValues(){
			setSelectedIndex(document.getElementById("county"),"<%=cwRequest.getCountyInRegion7() %>");
			setSelectedIndex(document.getElementById("gender"),"<%=cwRequest.getGender()%>");
			setSelectedIndex(document.getElementById("status"),"<%=cwRequest.getStatus()%>");
			setSelectedIndex(document.getElementById("category"),"<%=cwRequest.getCategory()%>");

			return;
		}
</script>
 
 <body onload="setDropDownValues()">
 
    
 
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">
  
<div class="container">
    <img class="img-left" src="/../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext"> Foster Angels Request</h4>
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
</div>
 
    <div class="container">
   
			</div>
  
   <div id="wrap">
</div>

   <div class="container">
   <p> All fields are required </p>
<label for="relatedFormSection1">Child's Information</label>
   <div class="relatedFormSection">

<form role="form" name="cpsrequest" method="post" action="update" onSubmit="return adminFormValidation();">

<input type="hidden" name="key" id="key" 
			value="<%=KeyFactory.keyToString(cwRequest.getKey()) %>" />

  <div class="form-group">
    <label for="firstName">First Name</label>
    <input type="text" class="form-control" name ="childname" id="childname" placeholder="Child Name" onblur="return alphanumeric(this);"  value="<%=cwRequest.getChildName() %>">
  </div>

        <div class="form-group">
    <label for="County in Region 7">Gender</label>
    <select class="form-control" name="gender" id="gender">
  <option>male</option>
  <option>female</option>
  </select>
  </div>
  
    <div class="form-group">
    <label for="firstName">Age</label>
    <input type="number" class="form-control" id="age" name = "age" placeholder="00" onblur="return allnumeric(this);" value="<%=cwRequest.getAge() %>">
  </div>
  
  	<div class="form-group">
    <label for="lastName">Child PID #</label>
    <input type="number" class="form-control" name="pidNumber" id="pidNumber" placeholder="1234" onblur="return allnumeric(this);" value="<%=cwRequest.getPid() %>">
  </div>
  
  <div class="form-group">
    <label for="lastName">Number of Children in request</label>
    <input type="number" class="form-control" name="numChildSupported" id="numChildSupported" placeholder="1" onblur="return allnumeric(this);" value="<%=cwRequest.getNumOfChildernSupported() %>">
  </div>
  
    	<div class="form-group">
    <label for="lastName">Number of Times Family Has Used FACT</label>
    <input type="number" class="form-control" name="numUsedFact" id="numUsedFact" placeholder="00" onblur="return allnumeric(this);" value="<%=cwRequest.getNumberOfTimesUsedFact() %>">
  </div>
  
        <div class="form-group">
    <label for="County in Region 7">Region 7 County</label>
    <select class="form-control" name="county" id="county" >
		<option>Bastrop</option>
		<option>Bell</option>
		<option>Blanco</option>
		<option>Bosque</option>
		<option>Brazos</option>
		<option>Burleson</option>
		<option>Burnet</option>
		<option>Caldwell</option>
		<option>Coryell</option>
		<option>Falls</option>
		<option>Fayette</option>
		<option>Freestone</option>
		<option>Grimes</option>
		<option>Hamilton</option>
		<option>Hays</option>
		<option>Hill</option>
		<option>Lampasas</option>
		<option>Lee</option>
		<option>Leon</option>
		<option>Limestone</option>
		<option>Llano</option>
		<option>Madison</option>
		<option>Mclennan</option>
		<option>Milam</option>
		<option>Mills</option>
		<option>Robertson</option>
		<option>San Saba</option>
		<option>Travis</option>
		<option>Washington</option>
		<option>Williamson</option>
  </select>
  </div>

  
</div>
  
  
  
<label for="relatedFormSection1">Request Information</label>
   <div class="relatedFormSection">
  
	<div class="form-group">
    <label for="supervisor">Date Needed (mm/dd/yyyy)</label>
    <input type="date" class="form-control" name="requestDate" id="requestDate" placeholder="mm/dd/yyyy" onblur ="return ValidateRequestedDate(this);" value="<%=cwRequest.getRequestedDateString() %>"><%=cwRequest.getRequestedDateString() %>
  </div>
  
    <div class="form-group">
    <label for="lastName">Dollar Amount</label>
    <input type="text" class="form-control" name="requestAmount" id="requestAmount" placeholder="00" onblur="return allnumeric(this);" value="<%=cwRequest.getReqestedAmount() %>">
  </div>
  
  
  <div class="form-group">
    <label for="supervisor">Desired Vendor</label>
    <input type="text" class="form-control" name="requestVendor" id="requestVendor" placeholder="Pleas enter vendor name. Example:Walmart" onblur="return alphanumeric(this);" value="<%=cwRequest.getRequestedVendor() %>">
  </div>
  
    <div class="form-group">
    <label for="supervisor">Description</label>
    <textarea class="form-control" placeholder="What is the request for and why is it raised? and Child information such as name, age and PID if request is for more than one child" rows="3" name="requestDescription" id="requestDescription" onblur="return alphanumeric(this);"><%=cwRequest.getRequestDescription() %></textarea>

  </div>
  
</div>

<label for="relatedFormSection1">FACT Admin details</label>
   <div class="relatedFormSection">
  
	<div class="form-group">
    <label for="supervisor">Category</label>
     <select class="form-control" name="category" id="category" >
		<option value = "Adoption Assistance">Adoption Assistance</option>
		<option value="Clothing/Personal Care">Clothing/Personal Care</option>
		<option value="Education">Education</option>
		<option value="Food">Food</option>
		<option value = "Housing/Shelter">Housing/Shelter</option>
		<option value = "Medical">Medical</option>
		<option value = "Miscellaneous">Miscellaneous</option>
		<option value = "Recreation">Recreation</option>
		<option value = "Transportation">Transportation</option>
  </select>
  </div>
  
   <div class="form-group">
    <label for="lastName">Purchased Dollar Amount</label>
    <input type="text" class="form-control" name="purchasedAmount" id="purchasedAmount" placeholder="00" onblur="return allnumeric(this);" value="<%=cwRequest.getPurchasedAmount() %>">
  </div>
  
    <div class="checkbox">
    <label for="checkbox">
    <input type="checkbox" name="isSampleReq" id="isSampleReq" value="true"/>
    <strong>Flag as Sample Request </strong>
    </label>
    </br> </br>
  </div>
  
   
  
   <div class="form-group">
    <label for="notes">Admin Notes</label>
    <textarea class="form-control" placeholder="Approved/rejection notes" rows="3" name="adminnotes" id="adminnotes" onblur="return alphanumeric(this);"><%=cwRequest.getAdminNotes() %></textarea>

  </div>
  
  <div class="form-group">
    <label for="notes">Message for Approval Email</label>
    <textarea class="form-control" placeholder="Include pick up date and time and any special instructions." rows="3" name="adminemailnotes" id="adminemailnotes" ><%=cwRequest.getAdminEmailNotes() %></textarea>

  </div>
  
  <div class="form-group">
    <label for="status">Status</label>
     <select class="form-control" name="status" id="status" >
		<option value = "<%=RequestStatusTypes.APPROVED.getLabel() %>">Approved</option>
		<option value = "<%=RequestStatusTypes.DENIED.getLabel() %>">Denied</option>		
		<option value = "<%=RequestStatusTypes.CLOSED.getLabel() %>">Closed</option>
		<% 
			if (!cwRequest.getStatus().equals(RequestStatusTypes.DENIED.getLabel())){
		%>
		<option value = "<%=RequestStatusTypes.DELIVERED.getLabel() %>">Delivered</option>
		<%
			}
		%>
		
  </select>
  </div>
  
  <div class="form-group">
    <label for="supervisor">Date Delivered (mm/dd/yyyy)</label>
    <input type="date" class="form-control" name="deliveredDate" id="deliveredDate" placeholder="mm/dd/yyyy" onblur ="return ValidateDeliveredDate(this);" value="<%=cwRequest.getDeliveredDateString() %>"><%=cwRequest.getDeliveredDateString() %>
  </div>
    
</div>

  

<br />


<div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
  </div>
   <br />  
  <div class="container pagination-centered">
  <button class="btn btn-lg btn-defult btn-block " type="reset" onclick="location.href='/admin/reqsummary'">Cancel</button>
  </div>
</form>

<br />

</div>

    	 

 <div class="container">   
     <div id="footer">
     <br />
        <h6 class="text-center"> For questions or problems please contact Foster Angels at:</h6>
           <h6 class="text-center"> <a class="text-center" href="mailto:info@fosterangelsctx.org">info@fosterangelsctx.org</a></h6>
      </div> 
    </div>
</div>
  
     
     <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>
