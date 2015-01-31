
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>


<html>
<head>
<meta charset="ISO-8859-1">
<title>Update CW Request</title>
</head>
<body>

<%
		CWRequest cwrequest = null;
	
		if(request.getAttribute("cwrequest")!=null){
		
			cwrequest = (CWRequest)request.getAttribute("cwrequest");
			
		}
		
	%>
<h1>Update a Request</h1>

<form method="post" action="../update">
<input type="hidden" name="key" id="key" 
			value="<%=KeyFactory.keyToString(cwrequest.getKey()) %>" />
		<table>
			<tr>
				<td>Child Name :</td>
				<td><input type="text" style="width: 185px;" maxlength="30"
					name="childname" id="childname" value="<%=cwrequest.getChildName() %>"/></span></td>
					<td>Age :</td>
				<td><input type="number" style="width: 185px;" maxlength="30"
					name="age" id="age" value="<%=cwrequest.getAge() %>"/></span></td>
					<td>Sex :</td>
				<td><select id="sex">
  					<option value="m" selected>Male</option>
  					<option value="f">Female</option>
  					<!--  <option value="x">Supervisor</option>  -->  					
				</select></td>
					
			</tr>
			<tr>
				<td>Child PID #:</td>
				<td>
				<input type="number" style="width: 185px;" maxlength="30"
					name="pidNumber" id="pidNumber" value="<%=cwrequest.getPid() %>"/></span>
				</td>				
				<td>County in region 7:</td>
				<td><input type="text" style="width: 185px;" maxlength="30"
					name="county" id="county" value="<%=cwrequest.getCountyInRegion7() %>"/></span></td>
				<td>Request Amount:</td>
				<td><input type="number" style="width: 185px;" maxlength="30"
					name="requestAmount" id="requestAmount" value="<%=cwrequest.getReqestedAmount() %>"/></span></td>
			</tr>
			
			<tr>
				<td>Description:</td>
				<td>
				<input type="text" style="width: 185px;" maxlength="80"
					name="requestDescription" id="requestDescription" value="<%=cwrequest.getRequestDescription() %>"/></span>
				</td>		
				<td>Request Date:</td>
				<td><input type="text" style="width: 185px;" maxlength="30"
					name="requestDate" id="requestDate" value="<%=cwrequest.getRequestedDate() %>"/> mm/dd/yyyy </span></td>
				
			</tr>
			<tr>
				<td># Child/Family Used Fact :</td>
				<td>
				<input type="text" style="width: 185px;" maxlength="30"
					name="numUsedFact" id="numUsedFact" value="<%=cwrequest.getNumberOfTimesUsedFact() %>"/></span>
				</td>				
				<td>Requested Vendor:</td>
				<td><input type="text" style="width: 185px;" maxlength="30"
					name="requestVendor" id="requestVendor" value="<%=cwrequest.getRequestedVendor() %>"/></span></td>
			</tr>
			
			<tr>
				<td>Category:</td>
				<td>
				<input type="text" style="width: 185px;" maxlength="30"
					name="category" id="category" value="<%=cwrequest.getCategory() %>"/></span>
				</td>				
				<td>Flag as a sample request:</td>
				<td><input type="checkbox" style="width: 185px;" maxlength="30"
					name="isSampleReq" id="isSampleReq" value="<%=cwrequest.isSampleReqFlag() %>"/></span></td>
			</tr>
			
		</table>
		<input type="submit" class="update" title="Update" value="Update" />
	</form>
</body>
</html>