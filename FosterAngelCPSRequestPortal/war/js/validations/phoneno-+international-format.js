function phonenumber(inputtxt) {
	var phoneno = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
	if (inputtxt.value.match(phoneno)) {
		inputtxt.style.borderColor = '';
		return true;
	} else {
		alert("Not a valid Phone Number");
		inputtxt.style.borderColor = 'red';
		inputtxt.focus();
		return false;
	}
}

function checkPhone (obj) {
	  str = obj.value.replace(/[^0-9]+?/g, '');
	  if(obj == undefined || obj.value == "NA"){
			return true;
		}
	  switch (str.length) {
	   case 0:
	     alert('Please enter numbers only.');
	     obj.style.borderColor = 'red';
		 obj.focus();
	     return;
	   case 7:
	     str = str.substr(0,3)+"-"+str.substr(3,4);
	     break;
	   case 10:
	     str = "("+str.substr(0,3)+") "+str.substr(3,3)+"-"+str.substr(6,4);
	     break;
	   default:
	     alert('Please enter a 7 digit phone number (with area code, if applicable).');
	     obj.select();
	     obj.style.borderColor = 'red';
	     obj.focus();
	     return false;
	  }
	  obj.value = str;
	  obj.style.borderColor = '';
	  
	 }