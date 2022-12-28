<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

			<!-- Truy van du lieu tat ca san pham trong database -->
			<sql:transaction dataSource="jdbc/db">
				<sql:query sql="select * from products where product_name like ?" var="rs">
					<c:if test="${search == null }"><sql:param>%</sql:param></c:if>
					<c:if test="${search != null }"><sql:param>%${search }%</sql:param></c:if>
				</sql:query>
			</sql:transaction>
			
			<p style="color:#d49012; padding-top: 20px; text-align: center;">${message}</p>
			<c:if test="${rs.rows[0] == null}"><p style="text-align: center; color: red">There is no product found!</p></c:if>
			
			<!-- Tao carousel. 1 trang hien thi 6 san pham -->
			<div class="carousel">
					<c:forEach var="mov" items="${rs.rows }" varStatus="row">
						
						<!-- Tao table 2 hang 3 cot-->
						<c:if test="${row.index == 0 }">							
							<table class="table">						
						</c:if>							
						
						<c:if test="${row.index != 0 && row.index % 6 == 0 }">							
							<table class="table hidden">						
						</c:if>	
						
						<c:if test="${row.index % 3 == 0 }"><tr></c:if>
						<td class="td">
							<a href="<%= response.encodeUrl(request.getContextPath()) + "/DetailController?id="%>${mov.product_id }">
								<div style="margin:10px" class="td-info">
									<img class="image" alt="image" src="${mov.product_img_source }"/>
									<p class="product_type">${mov.product_type }</p>
									<p class="product_name">${mov.product_name }</p>
									<p class="product_price">$ ${mov.product_price }</p>				
								</div>
							</a>
						</td>
						<c:if test="${row.index % 3 == 2 }"></tr></c:if>
						
						<c:if test="${row.index % 6 == 5 || row.last}"></table></c:if>
					</c:forEach>
					
					<div>
						<!-- Tao nut danh so trang -->
						<c:forEach var="mov" items="${rs.rows }" varStatus="row">
							<c:if test="${row.index == 0 }">							
								<button type ="button" class="item on">${Integer.valueOf((row.index / 6) + 1) }</button>						
							</c:if>
							
							<c:if test="${row.index != 0 && row.index % 6 == 0 }">							
								<button type ="button" class="item">${Integer.valueOf((row.index / 6) + 1) }</button>						
							</c:if>
						</c:forEach>
					</div>
			</div>

<script>
	const table = document.querySelectorAll(".table");
	const item = document.querySelectorAll(".item");
	
	// Tạo event click cho nút đánh số trang
	for(let i = 0; i< item.length; i++){
		item[i].addEventListener("click", function(){
			for(let j = 0; j< table.length; j++){
				if(j === i){
					item[j].classList.add("on");
					table[j].classList.remove("hidden");
				} else {
					item[j].classList.remove("on");
					table[j].classList.add("hidden");
				}
			}
		})
	}
</script>