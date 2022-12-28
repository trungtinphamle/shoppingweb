<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/login.css"/>
</head>
<body>
	<div class="wrap">
		<div class="left">
			<div class="container">
				<h2>Sign in</h2>
				<% if(!session.getAttribute("error").equals("")) {%>
				<p style="font-style:italic; color: orange">${error }</p>
				<% } %>
				<!-- Form dang nhap-->
				<form id="form" action="<%= response.encodeUrl(request.getContextPath() + "/log?action=login") %>" method="post">
					<input class="login-input" type="text" name="email" value="${email }" placeholder="email" required/>
					<input class="login-input" type="password" name="password" value="${password }" minlength="6" placeholder="password" required/>
					<div class="remerber">
						<input type="checkbox" id="remember-user" name="remember" value="yes">
	  					<label for="remember-user">Remember me</label>
					</div>
					<a id="forget" href="#">Forgot your password?</a>
					<button id="login-submit" type="submit">LOGIN</button>
					<a title="exit" href="<%= response.encodeUrl(request.getContextPath() + "/SearchController") %>" style="color: #151f73;">EXIT</a>
				</form>
			</div>
		</div>
		
		<div class="right">
			<div class="container">
				<h2 class="white center">Welcome Back!</h2>
				<p class="white center">To keep connected with us please login with your personal info</p>
			</div>
		</div>		
	</div>

</body>
</html>