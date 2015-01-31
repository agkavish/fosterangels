<%@ page import="java.util.List" %>
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>GAE + Spring 3 MVC REST + CRUD Example with JDO</h1>

	Function : <a href="add">Add Profile</a>
	<hr />

	<h2>All Customers</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Name</td>
				<td>Email</td>
				<td>Created Date</td>
				<td>Action</td>
			</tr>
		</thead>
		
		<%
			
			if(request.getAttribute("profileList")!=null){
				
				List<Profile> profiles = (List<Profile>)request.getAttribute("profileList");
				
				if(!profiles.isEmpty()){
					 for(Profile p : profiles){
						 
		%>
					<tr>
					  <td><%=p.getFirstName() %></td>
					  <td><%=p.getEmail() %></td>
					  <td><%=p.getDate() %></td>
					  <td><a href="update/<%=p.getFirstName()%>">Update</a> 
		                   | <a href="delete/<%=KeyFactory.keyToString(p.getKey()) %>">Delete</a></td>
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