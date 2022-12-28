<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  

<meta charset="UTF-8">

<fmt:setLocale value = "vi_VN"/>
			
			<form id = "pay-form" action="<%= response.encodeRedirectUrl(request.getContextPath()) + "/PayController" %>" method="get">
				<table id="pay-tbl-1">
				  <tr>
				    <th style="width: 40%; text-align: center">Products in cart</th>
				    <th style="width: 20%; text-align: center">Price</th>
				    <th style="width: 10%; text-align: center">Quantity</th>
				    <th style="width: 20%; text-align: center">Amount</th>
				    <th style="width: 10%; text-align: center">Delete</th>
				  </tr>
				  <!-- Lay du lieu tu cart va hien thi -->
				  <c:forEach var="mov" items="${cart.items }" varStatus="status">
					  <tr>
					    <td style="width: 40%">
					    	${mov.name }<br>
					    	ID: ${mov.id }
						</td>
					    <td style="width: 20%" class="price">($) ${mov.price }</td>
					    <td style="width: 10%"><input class ="quantity" type="number" name="quantity" min="1" value="${mov.number }" onchange="myFunction()"/></td>
					    <td style="width: 20%" class="amount">($) ${Math.round(mov.price * mov.number *100.00)/100.00 }</td>
					    <td style="width: 10%; text-align: center"><a href="<%= response.encodeUrl(request.getContextPath() + "/PayController?action=delete&id=") %>${mov.id}" class="home-btn"><strong>X</strong></a></td>
					  </tr>
				  </c:forEach>	
				  <!-- Hien thi tong so tien gio hang -->			  
				  <tr>
				  	<td style="border:none"></td>
				  	<td style="border:none"></td>
				  	<td style="border:none"></td>
				    <td id="total" style="border-left: none; text-align: right">Total: ${cart.amount }</td>
				  </tr>
				</table>
				<p style="color:#d49012; text-align: center;">${error }</p>
				<table id="pay-tbl-2">
					<tr>
						<td class="subject-td">Customer email</td>
						<td class="input-td"><input type ="text" id = "name" name ="email" value="<c:if test='${email != null }'>${email }</c:if>" required/></td>
					</tr>
					<tr>
						<td class="subject-td">Customer address</td>
						<td class="input-td"><input type ="text" id = "address" name ="address" value="<c:if test='${address != null }'>${address }</c:if>" required/></td>
					</tr>
					<tr>
						<td class="subject-td">Discount code (if any)</td>
						<td class="input-td"><input type ="text" id = "discount" name ="discount"/></td>
					</tr>
				</table>
				<input class="home-btn" type="submit" value="Submit"/>
			</form>
<script>
	const price = document.querySelectorAll(".price");
	const quantity = document.querySelectorAll(".quantity");
	const amount = document.querySelectorAll(".amount");
	const total = document.getElementById("total");

	// Tao event change khi quantity thay doi -> thanh tien tung san pham va tong tien thay doi
	function myFunction(){
		let sum = 0;
		for(let i = 0; i<quantity.length; i++){
			amount[i].innerHTML = "($) " + (parseFloat(price[i].innerHTML.slice(4,)) * quantity[i].value).toFixed(2);
			sum += parseFloat(price[i].innerHTML.slice(4,)) * quantity[i].value;
		}
		total.innerHTML = "Total: " + sum.toFixed(2);
	}

</script>