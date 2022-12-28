package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import account.Account;
import model.Product;

public class AccountDS {
	Connection conn;

	public AccountDS(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public boolean exist(Account account) throws SQLException{
		String sql = "select count(*) as num from account where user_mail = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,account.getEmail());
		ResultSet rs = stmt.executeQuery();
		int count = 0;
		if(rs.next()) {
			count = rs.getInt("num");
		}
		stmt.close();
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validate(Account account) throws SQLException {
		String sql = "select count(*) as num from account where user_mail = ? and password = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,account.getEmail());
		stmt.setString(2,account.getPassword());
		ResultSet rs = stmt.executeQuery();
		int count = 0;
		if(rs.next()) {
			count = rs.getInt("num");
		}
		stmt.close();
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addAccount (Account account) throws SQLException {
		String sql = "insert into account values (?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,account.getEmail());
		stmt.setString(2,account.getPassword());
		stmt.setInt(3,account.getRole());
		stmt.setString(4,account.getName());
		stmt.setString(5,account.getAddress());
		stmt.setString(6,account.getPhone());
		stmt.executeUpdate();
		stmt.close();
}
	
	public void update (Account account) throws SQLException {
		String sql = "select * from account where user_mail = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, account.getEmail());
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			account.setName(rs.getString("user_name"));
			account.setAddress(rs.getString("user_address"));
			account.setPhone(rs.getString("user_phone"));
		}
	}
}
