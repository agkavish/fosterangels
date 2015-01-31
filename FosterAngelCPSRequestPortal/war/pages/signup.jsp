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

    <title>Sign up for Foster Angels' Request Site</title>

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
 <script src="./../js/validations/singup-form-validation.js"></script>  
 <script src="./../js/validations/phoneno-+international-format.js"></script>
 
 <script>
 function onTitle(title, email) {
	 var t = title.value;
	 //alert("Req Type " + rType);
	 if(t == 'fa'){
		// alert("In if Req Type " + rType);
		 document.registration.unumber.disabled = true;
		 document.registration.unumber.value = "NA";
		 document.registration.svrfirstname.disabled = true;
		 document.registration.svrfirstname.value = "NA";
		 document.registration.svrlastname.disabled = true;
		 document.registration.svrlastname.value = "NA";
		 document.registration.svrEmail.disabled = true;
		 document.registration.svrEmail.value = "NA";
		 document.registration.svrPhone.disabled = true;
		 document.registration.svrPhone.value = "NA";
	 } else {
		 
		 document.registration.unumber.disabled = false;
		 document.registration.unumber.value = "";
		 document.registration.svrfirstname.disabled = false;
		 document.registration.svrfirstname.value = "";

		 document.registration.svrlastname.disabled = false;
		 document.registration.svrlastname.value = "";

		 document.registration.svrEmail.disabled = false;
		 document.registration.svrEmail.value = "";

		 document.registration.svrPhone.disabled = false;
		 document.registration.svrPhone.value = "";

	 }
	 return validateTitle(title, email);
 }
 </script>
 
 <%
		Profile profile = null;
	
		if(request.getAttribute("profile")!=null){
		
			profile = (Profile)request.getAttribute("profile");
			
		} else {
			profile = new Profile();
		}
		
		boolean userIDAvailable = true;
		if(request.getAttribute("isUserIDAvailable")!=null){
		
			userIDAvailable = (Boolean)request.getAttribute("isUserIDAvailable");
			
		} else {
			profile = new Profile();
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
			var stitle = "<%=profile.getTitle()%>";
			setSelectedIndex(document.getElementById("title"), stitle);
			return;
		}
</script>
  <body onload="setDropDownValues()">
  
 
  
<div class="container col-lg-4 col-md-6 col-sm-8 col-xs-12">
  
<div class="container">
    <img class="img-left" src="./../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext"> Foster Angels Sign Up</h4>
  </div>
   <div class="container">
  <ol class="breadcrumb">
  <li class="active">Step 1</li>
  <li>Step 2</li>
  <li>Step 3</li>
</ol>
</div>
 
    <div class="container">
   
			</div>
  
   <div id="wrap">
</div>

   <div class="container">
   <p> All fields are required </p>
<label for="relatedFormSection1">Your Information</label>
   <div class="relatedFormSection">

<form role="form" name='registration' onSubmit="return formValidation();" method="post" action="add">
<%
	if(userIDAvailable == false) {
%>
<div class="form-group">
 <label for="cpsEmail" style="color: red">Message : Email ID already registered </label>
 </div>
<%
}
%>
  <div class="form-group">
    <label for="cpsEmail">CPS Email address</label> 
    <input type="email" class="form-control" name="email" id="email" placeholder="xyz@dfps.state.tx.us/@fosterangelsctx.org" onblur= "return ValidateEmail(this);" value="<%=profile.getEmail()%>"/>
  </div>
  
  <div class="form-group">
    <label for="fname">First Name</label>
    <input type="text" class="form-control" placeholder="First" name="fname" id="fname" onblur="return allLetter(this);" value="<%=profile.getFirstName()%>"/>
  </div>
  
    <div class="form-group">
    <label class="lname" for="lastName">Last Name</label>
    <input type="text" class="form-control" name="lname" id="lname"  placeholder="Last" onblur="return allLetter(this);" value="<%=profile.getLastName()%>"/>
  </div>
  
  	<div class="form-group">
    <label for="pname">Preferred First Name</label>
    <input type="text" class="form-control" name="pname" id="pname" placeholder="Preferred Name" onblur="return allLetter(this);" value="<%=profile.getPreferedName()%>"/>
  </div>
  
  <div class="form-group">
  <label for="title">Work Title</label>
	<select id="title" name="title" class="form-control" onchange="return onTitle(this, email)">
				<option value="cw">Case Worker</option>
				<option value="ss">Support Staff</option>
				<!--  <option value="sv">Supervisor</option>
				<option value="pd">Program Director</option> -->
				<option value="fa">FACT Administrator</option> 
	</select>
  </div>
        <div class="form-group">
    <label for="mobileNumber">Work Mobile Phone Number</label>
    <input type="tel" class="form-control" name="mphone" id="mphone" placeholder="512xxxxxxx" onblur="return checkPhone(this);" value="<%=profile.getCellPhone()%>"/>
  </div>
  
          <div class="form-group">
    <label for="mobileNumber">Work Phone Number</label>
    <input type="tel" class="form-control"name="wphone" id="wphone"  placeholder="512xxxxxxx" onblur="return checkPhone(this);" value="<%=profile.getWorkPhone()%>"/>
  </div>
  
      <div class="form-group">
    <label for="supervisor">Unit Number</label>
    <input type="text" class="form-control" name="unumber" id="unumber" placeholder="123" onblur="return alphanumeric(this);" value="<%=profile.getUnitNumber()%>"/>
  </div>
  
</div>
  
  
  
<label for="relatedFormSection1">Your Supervisor's Information</label>
   <div class="relatedFormSection">
  
	<div class="form-group">
    <label for="supervisor">First Name</label>
    <input type="text" class="form-control" name="svrfirstname" id="svrfirstname" placeholder="First Name" onblur="return allLetter(this);" value="<%=profile.getSupervisorFirstName()%>">
  </div>
  
    <div class="form-group">
    <label class="svrlastname" for="lastName">Last Name</label>
    <input type="text" class="form-control" name="svrlastname" id="svrlastname" placeholder="Last Name" onblur="return allLetter(this);" value="<%=profile.getSupervisorLastName()%>"/>
  </div>
  
  
  <div class="form-group">
    <label for="supervisor">Email</label>
    <input type="email" class="form-control" name="svrEmail" id="svrEmail" placeholder="supervisor@dfps.state.tx.us" onblur="return ValidateEmail(this);" value="<%=profile.getSupervisorEmail()%>"/>
  </div>
  
    <div class="form-group">
    <label for="supervisor">Work Phone Number</label>
    <input type="tel" class="form-control" name="svrPhone" id="svrPhone" placeholder="512xxxxxxx" onblur="return checkPhone(this);" value="<%=profile.getSupervisorPhone()%>"/>
  </div>
  
</div>

  
  <label for="relatedFormSection1">Create a Password</label>
   <div class="relatedFormSection">
    <div class="form-group">
    <label for="exampleInputPassword1">Enter Password</label>
    <input type="password" class="form-control" name="uPassword" id="uPassword" placeholder="Password" onblur="return passid_validation(this, 5 ,12)"/>
  </div>
  
    <div class="form-group">
    <label for="exampleInputPassword1">Confirm Password</label>
    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" onblur="return validateConfirmPassword(uPassword, this)"/>
  </div>
  </div>




<div class="checkbox">  
	<label for="usedfa"><input type="checkbox" name="usedfa" id="usedfa" value="true" align="left" />
	<strong> Have used Foster Angels before  </strong> </label>  </br> </br>
</div>
<br />
<div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
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
