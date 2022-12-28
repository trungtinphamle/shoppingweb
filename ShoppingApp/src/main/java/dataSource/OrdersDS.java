package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Cart;
import model.Orders;
import model.Product;

public class OrdersDS {
	Connection conn;

	public OrdersDS(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public void insertOrder(Orders order, Cart cart) throws SQLException {
		String sql = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE\n";
		sql+= "begin transaction\n";
		sql+= "SET IDENTITY_INSERT orders ON\n";
		// Tạo order_id mới
		sql+= "declare @id int = (select case when max(order_id) is null then 0 else max(order_id) + 1 end from orders)\n";
		sql+= "declare @price float\n";
		// Thêm dữ liệu vào bảng orders
		sql+= "insert into orders (user_mail, order_id, order_date, order_discount_code, order_address) values (?,@id,?,?,?)\n";
		List<Product> list = cart.getItems();
		// Thêm dữ liệu vào bảng orders_detail
		for(int i = 0; i< list.size(); ++i) {
			sql+= "set @price = (select product_price from products where product_id = " + list.get(i).getId() + ")\n";
			sql+= "insert into orders_detail values (@id,?,@price * ?,@price)\n";
		}		
		sql+= "SET IDENTITY_INSERT orders OFF\n";
		sql+= "commit transaction\n";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, order.getUserMail());
		stmt.setDate(2, order.getOrderDate());
		stmt.setString(3, order.getDiscount());
		stmt.setString(4, order.getAddress());
		
		int i = 0;
		while(i< list.size()) {
			stmt.setInt(i*2 + 5, list.get(i).getId());
			stmt.setInt(i*2 + 6, list.get(i).getNumber());
			++i;
		}

		stmt.executeUpdate();
		stmt.close();
	}
}
