<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin.css">
</head>
<body>
	<div class="wrap">
	  <nav id="sidebar">
	  	<div class="container">
		  	<div class="sidebar-header">
		  	<!-- Display Welcome user -->
		  	  <h4>Hi ${name},</h4>
	          <h3 id="sidebar-title">1845 TEAM</h3>
	        </div>
	
	        <a class ="sidebar-item" href="<%= response.encodeUrl(request.getContextPath()) + "/SearchController" %>">Continue Shopping</a>
	        <a class ="sidebar-item" href="<%= response.encodeUrl(request.getContextPath() + "/log?action=logout") %>">Log Out</a>
	  	</div>
      </nav>
      
      <div class="body">
      	<div id = "banner"></div>
      	<div id ="info">
      		<div class="container">
      			<div class="wrap-member">
      				<div class="container">
      					<div class="title" style="text-align:center"><strong>My Transactions</strong></div>
      					<table>
      						<tr class="row">
      							<th class="col1">Order ID</th>
      							<th class="col2">Product ID</th>
      							<th class="col3">Product name</th>
      							<th class="col4">Product price</th>
      							<th class="col5">Total</th>
      							<th class="col6">Order date</th>
      						</tr>
      						
      						<sql:transaction dataSource="jdbc/db">
								<sql:query sql="select d.order_id, d.product_id, p.product_name, d.price_product, d.amount_product, o.order_date
												from Orders_detail as d left join orders as o on d.order_id = o.order_id
												left join Products as p on d.product_id = p.product_id
												where user_mail = ?
												order by o.order_date desc" var="rs">
									<sql:param>${email}</sql:param>
								</sql:query>
							</sql:transaction>
							
							<c:forEach var="mov" items="${rs.rows }" varStatus="status">
	      						<tr class="row">
	      							<td class="col1">${mov.order_id }</td>
	      							<td class="col2">${mov.product_id }</td>
	      							<td class="col3">${mov.product_name }</td>
	      							<td class="col4">$ ${mov.price_product }</td>
	      							<td class="col5">$ ${mov.amount_product }</td>
	      							<td class="col6">${mov.order_date }</td>
	      						</tr>
      						</c:forEach>

      					</table>
      				</div>
      			</div>
      		</div>
      	</div>
      </div>
	</div>
</body>
</html>