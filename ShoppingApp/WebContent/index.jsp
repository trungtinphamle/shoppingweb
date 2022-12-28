<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/home.css">
</head>
<body>
<div class="wrap">
	<!-- Import header -->
	<jsp:include page="header.jsp"></jsp:include>
	<section class="body">
		<div class="container">	
			<c:choose>
				<c:when test="${action == null }">
					<jsp:include page="/cart.jsp"></jsp:include>
					<%session.setAttribute("search",null); %>
				</c:when>	
				<c:when test='${action == "detail" }'>
					<jsp:include page="/infoProduct.jsp"></jsp:include>
				</c:when>	
				<c:otherwise>
					<jsp:include page="/pay.jsp"></jsp:include>
				</c:otherwise>
			</c:choose>
		</div>
	</section>
	<!-- Import footer -->
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>