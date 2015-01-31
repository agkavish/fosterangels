<%@ page import="com.fact.wps.model.Profile" %>
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
 <script>
 function actOnRequestType(reqType) {
	 var rType = reqType.value;
	 //alert("Req Type " + rType);
	 if(rType == 'OTHER'){
		// alert("In if Req Type " + rType);
		 document.cpsrequest.cwFirstName.disabled = true;
		 document.cpsrequest.cwLastName.disabled = true;
		 document.cpsrequest.cwEmail.disabled = true;	 
	 } else {
		 document.cpsrequest.cwFirstName.disabled = false;
		 document.cpsrequest.cwLastName.disabled = false;
		 document.cpsrequest.cwEmail.disabled = false; 
	 }
	 return;
 }
 </script>
 
 <body>
 
     <%
		Profile uProfile= null;
	
		if(request.getSession().getAttribute("signinuser")!=null){
		
			uProfile = (Profile) request.getSession().getAttribute("signinuser");			
		} 	else {
			uProfile = new Profile();
		}
		
	%>
 
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">
  
<div class="container">
    <img class="img-left" src="./../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext"> Foster Angels Request</h4>
   </div>
   <div class="container">
<ul class="nav nav-pills">
 <li><a href="/cpshome"><img class="" src="./../img/Home.png" alt="" height="20" width="20"></a></li>
  <li><a href="/admin/reqsummary">Requests</a></li>
  <li><a href="/admin/usersummary">Users</a></li>
  <li><a href="/admin/reports">Reports</a></li>
  <li><a href="#">Create Request</a></li>
  <li><a href="/profile/view/<%=KeyFactory.keyToString(uProfile.getKey())%>">Profile</a></li>
  <ul class="nav navbar-right">
      <li><a href="/signout">Sign Out</a></li>
      </ul>
</ul>
</div>

   <div class="container">
  <ol class="breadcrumb">  
  <li class="active">Step 1</li>
  <li>Step 2</a></li>
</ol>
</div>
 
    <div class="container">
   
			</div>
  
   <div id="wrap">
</div>

   <div class="container">
   <p> All fields are required </p>
<label for="relatedFormSection1">Child's Information</label>
   <div class="relatedFormSection">
   <p> Please provide the information of the CPS case worker who will be responsible for this request.  The CPS case worker must all ready have an account with this Foster Angels request site. </p>

<form role="form" name="cpsrequest" method="post" action="add" onSubmit="return formValidation();">

<div class="form-group">
    <label for="firstName">Request Type</label>
    <select class="form-control" name="reqType" id="reqType" onchange="return actOnRequestType(this);">
 		 <option value="CPS">CPS</option>
  		 <option value="OTHER">Other</option>
  </select>
  </div>

   <div class="form-group">
    <label for="firstName">Caseworker's First Name</label>
    <input type="text" class="form-control" id="cwFirstName" name="cwFirstName" placeholder="First" onblur="return alphanumeric(this);">
  </div>
  
       <div class="form-group">
    <label for="lastName">Caseworker's Last Name</label>
    <input type="text" class="form-control" id="cwLastName" name="cwLastName" placeholder="Last" onblur="return alphanumeric(this);" >
  </div>
  
         <div class="form-group">
    <label for="email">Caseworker's Email</label>
    <input type="text" class="form-control" id="cwEmail"  name="cwEmail"placeholder="yourname@dfps.state.tx.us" onblur="return ValidateEmail(this);">
  </div>


  <div class="form-group">
    <label for="firstName"> First Name</label>
    <input type="text" class="form-control" name ="childname" id="childname" placeholder="Child Name" onblur="return alphanumeric(this);">
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
    <input type="number" class="form-control" id="age" name = "age" placeholder="00" onblur="return allnumeric(this);">
  </div>
  
  	<div class="form-group">
    <label for="lastName">Child PID #</label>
    <input type="number" class="form-control" name="pidNumber" id="pidNumber" placeholder="1234" onblur="return allnumeric(this);">
  </div>
  
  <div class="form-group">
    <label for="lastName">Number of Children in request</label>
    <input type="number" class="form-control" name="numChildSupported" id="numChildSupported" placeholder="1" onblur="return allnumeric(this);" value="1">
  </div>
    	<div class="form-group">
    <label for="lastName">Number of Times Family Has Used FACT</label>
    <input type="number" class="form-control" name="numUsedFact" id="numUsedFact" placeholder="00" onblur="return allnumeric(this);" value = "0">
  </div>
  
        <div class="form-group">
    <label for="County in Region 7">Region 7 County</label>
    <select class="form-control" name="county" id="county">
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
    <input type="date" class="form-control" name="requestDate" id="requestDate" placeholder="mm/dd/yy" onblur="return ValidateRequestedDate(this);">
  </div>
  
    <div class="form-group">
    <label for="lastName">Dollar Amount</label>
    <input type="number" class="form-control" name="requestAmount" id="requestAmount" placeholder="00" onblur="return allnumeric(this);">
  </div>
  
  
  <div class="form-group">
    <label for="supervisor">Desired vendor</label>
    <input type="text" class="form-control" name="requestVendor" id="requestVendor" placeholder="Walmart" onblur="return alphanumeric(this);">
  </div>
  
    <div class="form-group">
    <label for="supervisor">Description</label>
    <textarea class="form-control" placeholder="What is the request for and why is it raised? and Child information such as name, age and PID if request is for more than one child" rows="3" name="requestDescription" id="requestDescription" onblur="return alphanumeric(this);"></textarea>

  </div>
  
</div>

  

<br />


<div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-xlarge" type="submit">Submit</button>
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
