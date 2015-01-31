<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.Profile" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
<title> CRON JOB STATUS</title>
</head>
<body>
     <%
         List<String> uRequests= null;
     if(request.getSession().getAttribute("emails_sent")!=null){
    	 uRequests = (List<String>)request.getSession().getAttribute("emails_sent");
     }
     else
     {
    	 uRequests = new ArrayList<String>();
     }
	 if(uRequests.size() == 0)
	 {
	%>
<h3>No Users are Active</h3>
<%
	  }
	  else {
%>
<table border = 1><tr><th>Emails Sent</th></tr> 
<%
for (String i:uRequests) {

%>
<tr><td><%=i %> </td></tr>
<%
}
	  }
%>
</table>
</body>
</html>