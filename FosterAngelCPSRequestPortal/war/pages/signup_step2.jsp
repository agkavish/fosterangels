<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Sign up for Foster Angels Request Site</title>

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

  <body>
  
<div class="container col-lg-4 col-md-6 col-sm-8 col-xs-12">
  
<div class="container">
    <img class="img-left" src="./../img/FA.png" alt="Foster Angels logo" height="30" width="30"><h4 class="headertext"> Foster Angels Sign Up</h4>
   </div>
   <div class="container">
  <ol class="breadcrumb">
  <li >Step 1</li>
  <li class="active">Step 2</a></li>
  <li>Step 3</li>
</ol>
</div>
  
   <div id="wrap">


   <div class="container">
   <h5 class="textonlymessage">We have sent a confirmation email to your email address. Please open the email we sent you and select the confirmation link and provide verification code.</h5>
<form action="/profile/verify" name="userverificationform" method="post">
<div>
 <label>Email Address :</label>
        <input type="text" class="form-control" id="uID" name="uID" onchange="return ValidateEmail(this);" autofocus/>
        <br />
  <label>Verification Code :</label>
<input class="form-control" type="text" id="verificationcode" name="verificationcode" />

</div>
<br /><br />
<div> <button class="btn btn-lg btn-primary btn-block" type="submit">Verify</button> 
</div>
 
</form>
</div>

    	 

 <div class="container">   
     <div id="footer">
     <br />
        <h6 class="text-center"> For questions or problems please contact Foster Angels at</h6>
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
