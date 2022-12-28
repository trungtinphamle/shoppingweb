<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
	<div class="head">
		<div class="container">
			<div class="logo">
				<h2>Shopping</h2>
				<p>Welcome to my Website</p>
			</div>
			<div class="search">
				<form class="search-form" action="${pageContext.request.contextPath }/SearchController" method ="get">
					<select name="category" id="category">
						<option value= null>Categories</option>
						<option value="apple">Apple</option>
					</select>
					<input id="seach-text" type="text" name="search" value = "${search }" placeholder="What are you looking for?"/>
					<button type="submit" id="search-button"></button>
				</form>				
			</div>
		</div>
	</div>
	
	<div class="nav-bar">
		<div class="container">
			<div class="logo">
			</div>
			<div class="nav">
				<a class="nav-item" href="<%= response.encodeUrl(request.getContextPath()) + "/SearchController" %>">Home</a>
				<a class="nav-item" href="<%= response.encodeUrl(request.getContextPath()) + "/SearchController" %>">Products</a>
				<a class="nav-item" href="#">About Us</a>
				<a class="nav-item" href="<%= response.encodeUrl(request.getContextPath() + "/PayController?action=mycart") %>">My Cart</a>
				<div style="float:right">
					<a class="nav-item" href="<%= response.encodeUrl(request.getContextPath() + "/register?action=register") %>"\>Register</a>
					<form style="display:inline" action="<%= response.encodeUrl(request.getContextPath()) + "/log?action=login" %>" method ="post">
						<input  style="display:none" type="text" name="email" value="<c:if test='${email  != null}'>${email}</c:if>">
						<input  style="display:none" type="text" name="password" value="<c:if test='${password  != null}'>${password}</c:if>">
						<button id ="login-btn" type="submit" class="<c:choose><c:when test='${name != null && name != ""}'>active</c:when><c:otherwise></c:otherwise></c:choose>">
							<c:choose><c:when test='${name != null && name != ""}'>${name },</c:when><c:otherwise>Login</c:otherwise></c:choose>
						</button>
					</form>							
				</div>
				
			</div>
		</div>
	</div>
</header>