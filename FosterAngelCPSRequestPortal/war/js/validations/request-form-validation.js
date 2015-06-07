function formValidation() {
	//alert("Hi 1");	
	var childname = document.cpsrequest.childname;
	var gender = document.cpsrequest.gender;
	var age = document.cpsrequest.age;
	var pidNumber = document.cpsrequest.pidNumber;
	var numUsedFact = document.cpsrequest.numUsedFact;
	var county = document.cpsrequest.county;
	var requestDate = document.cpsrequest.requestDate;
	var requestAmount = document.cpsrequest.requestAmount;
	var requestVendor = document.cpsrequest.requestVendor;
	var requestDescription = document.cpsrequest.requestDescription;
	var reqType = document.cpsrequest.reqType;
	
	if (alphanumeric(childname) && alphanumeric(requestVendor) && alphanumeric(requestDescription)){
		if (allnumeric(age) && allnumeric(numUsedFact) && allnumeric(requestAmount) && allnumeric(pidNumber)) {
			if(ValidateRequestedDate(requestDate)) {
			
							return true;	
			}
		}
	}
	
	alert("Please enter all fields.");
	return false;
}

function adminFormValidation(){
	var status = document.cpsrequest.status.value;
	var emailContent = document.cpsrequest.adminemailnotes;
	var deliveredDate = document.cpsrequest.deliveredDate;

	if (formValidation()) {
		if(status == "Approved"){
			if(emailContent.value == null || emailContent.value == '') {
				alert("Please provide email content for case worker");
				emailContent.style.borderColor ='red';
				//emailContent.focus();
				return false;
			} else {
				emailContent.style.borderColor ='';
			}
		} else if (status == "Delivered") {
			
			return ValidateDeliveredDate(deliveredDate);
		}
		
	} else {
		return false;
	}
}

function allLetter(uname) {
	var letters = /^[A-Za-z]+$/;
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
	var letters = /^[-.`'!@#$%^&*()_:,\?\n 0-9a-zA-Z]+$/;
	if (uadd.value.match(letters)) {
		uadd.style.borderColor ='';
		return true;
	} else {
		alert('Field can have alphanumeric characters only');
		uadd.style.borderColor ='red';
		//uadd.focus();
		return false;
	}
}

function allnumeric(uzip) {
	//alert("allnumeric");
	//var numbers = /^[.0-9]+$/;
	var numbers = /^[0-9]+(.[0-9]{1,2})?$/;
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
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var factmailformat = mailformat;
	//<TODO> Uncomment this
	//var mailformat = /^\w+([\.-]?\w+)*@dfps.state.tx.us$/;
	//var factmailformat = /^\w+([\.-]?\w+)*@fosterangelsctx.org$/;
	if (uemail.value.match(mailformat) ||uemail.value.match(factmailformat)) {
		uemail.style.borderColor ='';
		return true;
	} else {
		alert("You have entered an invalid email address!");
		uemail.style.borderColor ='red';
		//uemail.focus();
		return false;
	}
	
	
}

function ValidateRequestedDate(reqDate) {	
    var date_regex_us = /^\d{2}\/\d{2}\/\d{4}$/ ;
    var date_regex = /^[0-9]{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])/ ;


	var rDate = reqDate.value;
	if (rDate == '' ) {
		alert("Please provide requested date in mm/dd/yyyy format!");
		reqDate.style.borderColor ='red';
		//reqDate.focus();
		return false;
		
	} else {
		if (!(date_regex_us.test(rDate) || date_regex.test(rDate)))
		{
		  
			alert("Requested date format is wrong. Please provide date in mm/dd/yyyy format.")
			reqDate.style.borderColor ='red';
			return false
		}		
		reqDate.style.borderColor ='';
		return true;			
	}
	
}


function ValidateDeliveredDate(aDate) {	
    var date_regex_us = /^\d{2}\/\d{2}\/\d{4}$/ ;
    var date_regex = /^[0-9]{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])/ ;


	var rDate = aDate.value;
	if (rDate == '' ) {
		alert("Please provide delivered date in mm/dd/yyyy format!");
		aDate.style.borderColor ='red';
		//reqDate.focus();
		return false;
		
	} else {
		if (!(date_regex_us.test(rDate) || date_regex.test(rDate)))
		{
		  
			alert("Delivered date format is wrong. Please provide date in mm/dd/yyyy format.")
			aDate.style.borderColor ='red';
			return false
		}		
		aDate.style.borderColor ='';
		return true;			
	}
	
}