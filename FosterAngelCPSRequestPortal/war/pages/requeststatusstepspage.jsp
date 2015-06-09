<%@page import = "com.fact.common.RequestStatusTypes" %>
<%@ page import="com.fact.wps.model.CWRequest" %>
<%
    	String supervisorApproval ="";
    	String fosterAngelsApproval ="";
    	String attachReceipts ="";
    	String closeOut = "";
    	String delivered = "";
	
		
		
		CWRequest cwRequest1 = null;
		if(request.getSession().getAttribute("cwrequestinuse")!=null){
			
			cwRequest1 = (CWRequest) request.getSession().getAttribute("cwrequestinuse");			
		} 	else {
			cwRequest1 = new CWRequest();
		}
		
		switch(RequestStatusTypes.valueOf(cwRequest1.getStatus().toUpperCase()))
		{
		case NEW: case PENDINGSUPERVISORAPPROVAL:
			supervisorApproval = "Pending";
			fosterAngelsApproval = "Pending";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		case PENDINGFACTAPPROVAL:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Pending";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		case APPROVED: 
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		case PENDINGRECEIPTS:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			delivered = "Complete";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;			
		case DELIVERED: 
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			delivered = "Complete";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;	
		case CLOSED:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			delivered = "Complete";
			attachReceipts = "Complete";
			closeOut = "Complete";
			break;	
		case RECEIPTAVAILABLE:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Complete";
			delivered = "Complete";
			attachReceipts = "Complete";
			closeOut = "Pending";
			break;	
		case DENIED:
			supervisorApproval = "Complete";
			fosterAngelsApproval = "Denied";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;	
		case SUPERVISORDENIED:
			supervisorApproval = "Denied";
			fosterAngelsApproval = "Pending";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;	
		default:
			supervisorApproval = "Pending";
			fosterAngelsApproval = "Pending";
			delivered = "Pending";
			attachReceipts = "Pending";
			closeOut = "Pending";
			break;
		} 
%>


<label for="cpsworkerstable">Steps:</label>
<table class=" table table-condensed  table table-striped table table-hover" id="cpsworkerstable">

<thead>
<tr>
<th>Step</th>
<th>Status</th>
</tr>
</thead>
<tbody>
<tr>
<td>Supervisor approval</td>
<td><%= supervisorApproval %></td>
</tr>
<tr>
<td>Foster Angels Approval</td>
<td><%= fosterAngelsApproval %></td>
</tr>
<tr>
<td>Foster Angels Delivery</td>
<td><%= delivered %></td>
</tr>
<tr>
<td>Attach Receipt(s)</td>
<td><%= attachReceipts %></td>
</tr>
<tr>
<td>Closeout</td>
<td><%= closeOut %></td>
</tr>
</tbody>
</table>