<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Signin to Foster Angels Request Site</title>

    <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/signup.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
  </head>
	<script src="./../js/validations/singup-form-validation.js"></script>
	
  <body>
  
  	<%
  		String validSignin = "-1";
		if(session.getAttribute("isValidSignIn") != null){
		
			validSignin = (String)session.getAttribute("isValidSignIn");
			
		}
		
	%>
	
	
    
  <!-- <div id="wrap">  --> 
   
   <div class="container col-lg-4 col-md-6 col-sm-8 col-xs-12">
    	<div class="signin-header">
    <h1 class="titletext"> Foster Angels of Central Texas</h1>
			</div>
      <form class="form-signin" method="post" action="744646">
        <h2 class="titletext">Welcome</h2>
        <br />
        <p class="textfrontpagemessage">Foster Angels mission is to improve the lives of children in foster care, ensuring that each child has his or her basic needs met, and to provide life-enriching and life-enhancing experiences whenever possible.</p>
        <% if (validSignin.equals("0")) { %>
        <div class="cotainer" > <p style="font-style: italic;color: red;">Error : Invalid sign-in details</p>
        </div>
        <%} %>
        
        <label>User Name :</label>
        <input type="text" class="form-control" id="uID" name="uID" onchange="return ValidateEmail(this);" autofocus/>
        <label>Password	:</label>
        <input type="password" class="form-control" id="uPwd" name="uPwd" onchange="return passid_validation(this, 5 ,12);"/>       
      
         <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button> 
      </form>
   <!--  	</div> -->   
    <!--  </div>  -->
    
    <!-- <div id="footer">  --> 
      <div class="container">
        <h4 class="footertextSI"> <a href="/profile/add">First time?  Then sign up!</a></h4>
      <!-- </div>  --> 
    </div>

   </div>
     
     <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>
