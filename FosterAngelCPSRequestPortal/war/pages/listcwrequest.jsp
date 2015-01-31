<%@page import="com.fact.wps.model.CWRequest"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>FACT CW Request Portal</h1>

	Function : <a href="add">Add CW Request</a>
	<hr />

	<h2>All Requests</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Child Name</td>
				<td>PID</td>
				<td>Created Date</td>
				<td>Status</td>
				<td>Action</td>
			</tr>
		</thead>
		
		<%
			
			if(request.getAttribute("cwrequestList")!=null){
				
				List<CWRequest> cwrequests = (List<CWRequest>)request.getAttribute("cwrequestList");
				
				if(!cwrequests.isEmpty()){
					 for(CWRequest r : cwrequests){
						 
		%>
					<tr>
					  <td><%=r.getChildName() %></td>
					  <td><%=r.getPid() %></td>
					  <td><%=r.getDate() %></td>
					  <td><%=r.getStatus() %></td>
					  <td><a href="update/<%=KeyFactory.keyToString(r.getKey())%>">Update</a> 
		                   | <a href="delete/<%=KeyFactory.keyToString(r.getKey()) %>">Delete</a></td>
					</tr>
		<%	
			
					}
		    
				}
			
		   	}
		%>
         
        </tr>
     
	</table>

</body>
</html>