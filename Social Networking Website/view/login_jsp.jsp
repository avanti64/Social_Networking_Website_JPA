<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOGIN</title>
<link rel="stylesheet" type="text/css" href="Login.css">

<script type="text/javascript">
		function validate()
		{
			var x=document.forms["LoginForm"]["txtUserName"].value;
			
			if(x==null || x=="")
				{
				alert("Please enter a username.");
				return false;
				}	
			else
		{
				return validatepwd(document.forms["LoginForm"]["txtPassword"].value);
		}
				
		}
		function validatepwd(pwd)
		{
			if(pwd==null || pwd=="")
				{
				alert("Please enter a password.");
				return false;
				}
			
		}
		
		
		
		</script>
</head>
<body onload='document.LoginForm.txtUserName.focus()'>

<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<form name="LoginForm" action="loginServlet" method="post">

<table id="outer" align=center>

<tr>
<td><h2 align="center">LOGIN</h2></td>
</tr>
<tr>
<td>
<% String value = new String();
			value = request.getParameter("chkuser");
			System.out.println("value = " + value);
			%>
			<font color="red"><% 
			if (value!= null)
				{
				if (value.equals("true"))
				{
				out.println("Wrong username and password, please try again!"); 
				}
				}
			%></font>
			</td>
</tr>
<tr>
<td>
<table>
	<tr>
		<td><h4>User Name:</h4></td>
		<td><input type="text" name="txtUserName" id="txtUserName"></input>
		</td>
	</tr>

	<tr>
		<td><h4>Password:</h4></td>
		<td><input type="password" name="txtPassword" id="txtPassword"></input>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
		<input type="submit" name="subLogin" id="subSubmit" value = "Login" onclick="return validate();">
		</td>
		<td>
			<a href="SignUpForm.jsp">New User?</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>