function formValidation() {
	//alert("Hi 1");
	var uid = null;
	if(document.registration.email){
		uid = document.registration.email;
	}
	var passid = document.registration.password;
	var ufirstname = document.registration.fname;
	var ulastname = document.registration.lname;
	var uprefername = document.registration.pname;
	var unitnumber = document.registration.unumber;
	var spvrfname = document.registration.svrfirstname;
	var spvrlname = document.registration.svrlastname;
	var spvremail = document.registration.svrEmail;
	var uemail = document.registration.cpsEmail;
	//var usedfa = document.registration.usedfa;
	var currentPwd = document.registration.uOldPassword;
	
	var title = document.registration.title;
	//alert("usedfa "  + usedfa.value);
	if (ValidateEmail(uid)){
		if (allLetter(ufirstname) && allLetter(ulastname) && allLetter(uprefername)) {
			if(passid_validation(currentPwd, 5, 12)){
			if (passid_validation(passid, 5, 12)) {
				if(alphanumeric(unitnumber)) {
					if (allLetter(spvrfname) && allLetter(spvrlname) ) {
						if(ValidateEmail(spvremail)) {
							if(validateTitle(title, uid)){
								return true;	
							}
							
						}
					
					}
				} 
			}
			}
		}
	}
	alert("Please enter all fields.");
	return false;

}

function adminEditProfileFormValidation() {
	//alert("Hi 1");
	var uid = document.registration.email;
	var passid = document.registration.password;
	var ufirstname = document.registration.fname;
	var ulastname = document.registration.lname;
	var uprefername = document.registration.pname;
	var uemail = document.registration.cpsEmail;
	var usedfa = document.registration.usedfa;
	var title = document.registration.title;
	var currentPwd = document.registration.uOldPassword;
	if (ValidateEmail(uid)){
		if (allLetter(ufirstname) && allLetter(ulastname) && allLetter(uprefername)) {
			if(passid_validation(currentPwd, 5, 12)){

			if (passid_validation(passid, 5, 12)) {
							if(validateTitle(title, uid)){
								return true;	
							}
					}
		}
			}
	}
	alert("Please enter all fields.");
	return false;
}

function userid_validation(uid, mx, my) {
	var uid_len = uid.value.length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx) {
		alert("User Id should not be empty / length be between " + mx + " to "
				+ my);
		//uid.focus();
		return false;
	}
	return true;
}

function passid_validation(passid, mx, my) {
	if(passid == undefined){
		return true;
	}
	var passid_len = passid.value.length;
	if (passid_len == 0 || passid_len >= my || passid_len < mx) {
		alert("Password should not be empty / length be between " + mx + " to "
				+ my);
		passid.style.borderColor ='red';
		//passid.focus();
		return false;
	}
	passid.style.borderColor ='';
	return true;
}

function passid_validation_edit(passid, mx, my) {
	var passid_len = passid.value.length;
	if(passid_len > 0 ) {
		return passid_validation(passid, mx, my);
	} else {
		return true;
	}
}

function allLetter(uname) {
	var letters = /^[A-Z- a-z]+$/;
	if(uname == undefined || uname.value == "NA"){
		return true;
	}
	if (uname.value.match(letters)) {
		uname.style.borderColor ='';
		return true;
	} else {
		alert('Field can not be empty and must have alphabet characters only');
		uname.style.borderColor ='red';
		//uname.focus();
		return false;
	} 
}
function alphanumeric(uadd) {
	var letters = /^[0-9a-zA-Z]+$/;
	if(uadd == undefined || uadd.value == "NA"){
		return true;
	}
	if (uadd.value.match(letters)) {
		uadd.style.borderColor ='';
		return true;
	} else {
		alert('Field must have alphanumeric characters only');
		uadd.style.borderColor ='red';
		//uadd.focus();
		return false;
	}
}
function countryselect(ucountry) {
	if (ucountry.value == "Default") {
		alert('Select your country from the list');
		//ucountry.focus();
		return false;
	} else {
		return true;
	}
}
function allnumeric(uzip) {
	var numbers = /^[0-9]+$/;
	if (uzip.value.match(numbers)) {
		uzip.style.borderColor ='';
		return true;
	} else {
		alert('Field must have numeric characters only');
		uzip.style.borderColor ='red';
		//uzip.focus();
		return false;
	}
}

function ValidateEmail(uemail) {	
	//var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var mailformat = /^\w+([\.-]?\w+)*@dfps.state.tx.us$/;
	var factmailformat = /^\w+([\.-]?\w+)*@fosterangelsctx.org$/;
	//var factmailformat = mailformat;
	if(uemail == undefined || uemail.value == "NA"){
		return true;
	}
	if (uemail.value.match(mailformat) ||uemail.value.match(factmailformat)) {
		uemail.style.borderColor ='';
		return true;
	} else {
		alert("You have entered an invalid email address!");
		uemail.style.borderColor ='red';
		uemail.focus();
		return false;
	}
}

function validsex(umsex, ufsex) {
	var x = 0;

	if (umsex.checked) {
		x++;
	}
	if (ufsex.checked) {
		x++;
	}

	if (x == 2) {
		alert('Both Male/Female are checked');
		ufsex.checked = false
		umsex.checked = false
		umsex.focus();
		return false;
	}

	if (x == 0) {
		alert('Select Male/Female');
		//umsex.focus();
		return false;
	} else {
		alert('Form Succesfully Submitted');
		window.location.reload()
		return true;
	}
}

function validateConfirmPassword(passid, confpass) {
	var confpassval = confpass.value;
	var passval = passid.value;
	var confpass_len = confpassval.length;
	
	if(confpass_len == 0 ) {
		alert('Confirm password can not be empty');
		confpass.style.borderColor ='red';
		confpass.value='';
		//confpass.focus();
		return false;
	}
	
	if(confpassval != passval) {
		alert('Confirm password not matching with password');
		passid.style.borderColor ='red';
		passid.value='';
		confpass.value = '';
		//passid.focus();
		return false;
		
	}
	
	confpass.style.borderColor ='';
	return true;
}


function validateEditPassword(passid, confpass) {
	var confpassval = confpass.value;
	var passval = passid.value;
	var passval_len = passval.length;
	var confpass_len = confpassval.length;
	
	if(passval_len == 0 ) {
		return true;
	} else {
		return validateConfirmPassword(passid, confpass);
	}	
}

function validateTitle(title, userid){
	//<TODO> Uncomment this
	var factmailformat = /^\w+([\.-]?\w+)*@fosterangelsctx.org$/;
	if(title == undefined || title == ""){
		return true;
	} else {
		//var factmailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	
		var email = userid.value;
		var titlev = title.value;
		if (!email.match(factmailformat) && titlev == "fa") {		
			alert("Only user with FACT Email ID can be FACT Administrator!");
			title.style.borderColor ='red';
			title.focus();
		    return false;
		//	<TODO> - fix me
			//return true;
		} else {
			title.style.borderColor ='';		
			return true;
		}
			return true;
	}
	
}