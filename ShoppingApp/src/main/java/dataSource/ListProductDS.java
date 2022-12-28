package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Product;

public class ListProductDS {
	Connection conn;

	public ListProductDS(Connection conn) {
		this.conn = conn;
	}
	
	public List<Product> search (String characters){
		return null;
		
	}
	
	public Product getProduct(int id) throws SQLException {
		String sql = "select * from products where product_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,id);
		ResultSet rs = stmt.executeQuery();
		Product product = null;
		if(rs.next()) {
			product = new Product(id, rs.getString("product_name"), rs.getString("product_des"), rs.getFloat("product_price"),
					rs.getString("product_img_source"), rs.getString("product_type"), rs.getString("product_brand"));
		}
		
		stmt.close();
		return product;
		
	}
}
