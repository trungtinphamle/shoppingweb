<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  

<meta charset="UTF-8">

<fmt:setLocale value = "vi_VN"/>
			
			<!-- Truy van du lieu san pham da chon -->
			<sql:transaction dataSource="jdbc/db">
				<sql:query sql="select * from products where product_id = ?" var="rs">
					<sql:param>${id }</sql:param>
				</sql:query>
			</sql:transaction>
			
			<!-- Thanh phan thong tin chi tiet 1 san pham -->
			<p class="product_name_dt">${rs.rows[0].product_name }</p>
			<div class="detail" style="margin:10px 20px;">
				<img class="image_dt" alt="image" src="${rs.rows[0].product_img_source }"/>
				<div style="max-width: 500px; margin-left: 10px;">
					<p class="product_price_dt"><fmt:formatNumber type="number" pattern="###,###" value="${rs.rows[0].product_price * 1000000}"/> VND</p>
					<p class="product_des_dt">${rs.rows[0].product_des }</p>
					<a href ="<%= response.encodeUrl(request.getContextPath()) + "/CartController?id="%>${id }" class="home-btn"><strong>Add to cart</strong></a>
				</div>								
			</div>