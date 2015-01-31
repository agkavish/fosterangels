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
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Sign up for Foster Angels' Request Site</title>

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
  <script src="/../js/validations/singup-form-validation.js"></script>  
 <script src="/../js/validations/phoneno-+international-format.js"></script>

      <%
		Profile uProfile = null;
        int errorPage = 0;
	
		if(request.getSession().getAttribute("signinuser")!=null){
		
			uProfile = (Profile) request.getSession().getAttribute("signinuser");			
		} 	else {
			uProfile = new Profile();
		}
		
		
		// Profile that need edit
		
		Profile profile= null;
        	
		if(request.getAttribute("profile")!=null){
		
			profile = (Profile) request.getAttribute("profile");			
		} 	else {
			profile = new Profile();
		}
		
		if(request.getSession().getAttribute("passwdFlag")!=null)
		{
			errorPage = 1;
			//profile = (Profile)request.getSession().getAttribute("UpdatedProfile");
		}
		
		boolean isProfileForSignInUser = false;
		if(uProfile.getEmail().equalsIgnoreCase(profile.getEmail())) {
			isProfileForSignInUser = true;	
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
  <body>
  
<div class="container col-lg-10 col-md-12 col-sm-12 col-xs-12">

<div class="container"> 

    <img class="img-left" src="/../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext">Foster Angels</h4>

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
 
    <div class="container">
   
			</div>
  
   <div id="wrap">
</div>

   <div class="container">
<br />
<%
  if(errorPage == 1) {
%>
<div class="cotainer" > <p style="color:red">Error:You have entered incorrect old password</p>
        </div>
<%
  }
%>
<label for="relatedFormSection1">Your Information</label>
   <div class="relatedFormSection">

<form role="form" name='registration' onSubmit="return adminEditProfileFormValidation();" method="post" action="/profile/update" >

  <div class="form-group">
    <label for="cpsEmail1">CPS Email address</label>
    <input type="email" class="form-control" name="email" id="email" placeholder="yourname@dfps.state.tx.us/@fosterangelsctx.org" onblur="return ValidateEmail(this);" value="<%=profile.getEmail()%>" readonly="readonly" />
  </div>
  
  <div class="form-group">
    <label for="firstName">First Name</label>
    <input type="text" class="form-control" placeholder="First" name="fname" id="fname" onblur="return allLetter(this);" value="<%=profile.getFirstName() %>" />
  </div>
  
    <div class="form-group">
    <label class="sr-only" for="lastName">Last Name</label>
    <input type="text" class="form-control" name="lname" id="lname"  placeholder="Last" onblur="return allLetter(this);" value="<%=profile.getLastName() %>" />
  </div>
  
  	<div class="form-group">
    <label for="lastName">Preferred First Name</label>
    <input type="text" class="form-control" name="pname" id="pname" placeholder="Preferred Name" onblur="return allLetter(this);" value="<%=profile.getPreferedName() %>" />
  </div>
  
        <div class="form-group">
    <label for="mobileNumber">Work Mobile Phone Number</label>
    <input type="tel" class="form-control" name="mphone" id="mphone" placeholder="512-xxx-xxxx" onblur="return checkPhone(this);" value="<%=profile.getCellPhone()%>" />
  </div>
  
          <div class="form-group">
    <label for="mobileNumber">Work Phone Number</label>
    <input type="tel" class="form-control"name="wphone" id="wphone" placeholder="512-xxx-xxxx" onblur="return checkPhone(this);" value="<%=profile.getWorkPhone()%>" />
  </div>  
  
</div>  


<label for="relatedFormSection1">Your Password Information</label>
   <div class="relatedFormSection">
<label for="relatedFormSection1">Modify Password</label>
   <div class="relatedFormSection">
    <div class="form-group">
    <% 
    	if (isProfileForSignInUser){
    %>
    <label for="exampleInputPassword1">Enter Old Password</label>
    <input type="password" class="form-control" name="uOldPassword" id="uOldPassword" placeholder="Old Password" onchange="return passid_validation(this, 5 ,12)"/>
    <%
    	}
    %>
    <label for="exampleInputPassword1">Enter New Password</label>
    <input type="password" class="form-control" name="uPassword" id="uPassword" placeholder="Password" onchange="return passid_validation_edit(this, 5 ,12)"/>
  </div>
  
    <div class="form-group">
    <label for="exampleInputPassword1">Confirm Password</label>
    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" onblur="return validateEditPassword(uPassword, this)"/>
  </div>
  </div>
  </div>
  

  </div>
<input type="hidden" name="key" id="key" 
			value="<%=KeyFactory.keyToString(profile.getKey()) %>" />

<div class="container pagination-centered">
  <button class="btn btn-lg btn-primary btn-block " type="submit">Update Info</button>
  </div>
  <br />
  <div class="container pagination-centered">
  <button class="btn btn-lg btn-defult btn-block " type="button" onclick="location.href='/cpshome'">Cancel</button>
  </div>
  </form>

<br />

</div>

    	 

 <div class="container">   
     <div id="footer">
     <br />
        <h6 class="text-center"> For questions or problems please contact Foster Angels at:</h6>
         <h6 class="text-center"><a class="text-center" href="mailto:info@fosterangelsctx.org">info@fosterangelsctx.org</a></h6>
      </div> 
    </div>
</div>
  
     
     <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>
