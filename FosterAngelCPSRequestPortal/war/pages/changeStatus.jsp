<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.fact.wps.model.CWRequest" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
<title> CRON JOB STATUS</title>
</head>
<body>
     <%
         List<Integer> uRequests= null;
     if(request.getSession().getAttribute("request_ids")!=null){
    	 uRequests = (List<Integer>)request.getSession().getAttribute("request_ids");
     }
     else
     {
    	 uRequests = new ArrayList<Integer>();
     }
	 if(uRequests.size() == 0)
	 {
	%>
<h3>No Requests to be approved</h3>
<%
	  }
	  else {
%>
<table border = 1><tr><th>Requests Approved</th></tr> 
<%
for (Integer i:uRequests) {

%>
<tr><td><%=i %> </td></tr>
<%
}
	  }
%>
</table>
</body>
</html>