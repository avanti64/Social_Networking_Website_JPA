<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.*"
    import= "java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
<link rel="stylesheet" type="text/css" href="successfullogin.css">

</head>
<body>
<form name="Welcome" action="" method="post">

<table id="alignment" border = "0" align="center">
		<tr> <td><h2 align = "center"> Welcome,  <%= request.getAttribute("user_name") %> </h2></td></tr> 
		<tr> <td><font size=5>Your groups are:</font></td>  </tr>

        <% 
        
        List<Object[]> obj1 =(List<Object[]>)request.getAttribute("ObjectList");
		for (Object[] objs: obj1){ %>
		        <tr>
		        
			<td align="center">
			<a href = "GroupServlet?group_id=<%= objs[1]%>&group_name=<%= objs[0]%>"> <font size=3><%= objs[0]%> </font></a> </td>
		</tr>
		<%} %>
        
        </table>
		<table align="right">
		<tr>
		<td>
		<a id = "pos" href="login_jsp.jsp">Log Out<img src="logout.png" alt="Log Out"></a>
		</td>
	</tr>
	</table>
    </form>

</body>
</html>