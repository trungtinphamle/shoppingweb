<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/register.css"/>
</head>
<body>
	<div class="wrap">
		<div class="container">
				<a id="exit" title="exit" href="<%= response.encodeUrl(request.getContextPath() + "/register") %>">x</a>
				<h2>Register</h2>
				<c:if test='${error != "" }'>
					<p style="font-style:italic; color: orange; text-align: center;">${error }</p>
				</c:if>

				<!-- Form dang ky-->
				<form id="form" action="<%= response.encodeUrl(request.getContextPath() + "/register?action=register") %>" method="post">
					<input class="login-input" type="text" name="email" value="${email }" placeholder="email" required/>
					<input class="login-input" type="password" name="password" value="${password }" minlength="6" placeholder="password" required/>
					<input class="login-input" type="password" name="repassword" value="" minlength="6" placeholder="retype password" required/>
					<input class="login-input" type="text" name="name" value="${name }" placeholder="name" required/>
					<input class="login-input" type="text" name="address" value="${address }" placeholder="address" required/>
					<input class="login-input" type="text" name="phone" value="${phone }" placeholder="phone" required/>
					<button id="login-submit" type="submit">REGISTER</button>
				</form>
			</div>
	</div>
</body>
</html>