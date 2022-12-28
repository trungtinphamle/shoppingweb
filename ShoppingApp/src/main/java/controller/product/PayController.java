package controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dataSource.OrdersDS;
import model.Cart;
import model.Orders;
import model.Product;

/**
 * Servlet implementation class InsertOrderController
 */
public class PayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			ds = (DataSource) env.lookup("jdbc/db");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// type Vietnamese
		response.setContentType("text/html:charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		// Cart không có sản phẩm -> gửi thông báo yêu cần thêm sản phẩm vào Cart
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			session.setAttribute("action", null);	
			session.setAttribute("message", "Cart is empty. Add products to cart");
	
		} else {
			// Xóa 1 sản phẩm trong cart và gửi lại cart mới vào homepage
			String action = request.getParameter("action");
			if(action != null && action.equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				cart.remove(id);
				session.setAttribute("cart", cart);
				session.setAttribute("action", "pay");	

			} else if(action == null || !action.equals("mycart")) {
				String [] quantity = request.getParameterValues("quantity");
				String email = request.getParameter("email");
				String address = request.getParameter("address");
				String discount = request.getParameter("discount");
				
				// Cập nhật đơn hàng đã mua vào database
				OrdersDS ordersDS = new OrdersDS(conn);
				Orders order = new Orders(email, discount, address );
				List<Product> list = cart.getItems();
				if(list.size() == 0) {
					session.setAttribute("action", null);	
					session.setAttribute("message", "Cart is empty. Add products to cart");
				} else {
					
					for(int i = 0; i< list.size(); ++i) {
						int number = Integer.parseInt(quantity[i]);
						list.get(i).setNumber(number);
					}
					cart = new Cart(list);
					
					try {
						ordersDS.insertOrder(order, cart);
						session.setAttribute("error", "Successfully Order");
						cart= new Cart();
						session.setAttribute("cart", cart);
					} catch (SQLException e1) {
						session.setAttribute("error", "Order failed");
						e1.printStackTrace();
					}
					session.setAttribute("action", "pay");	
					
				}
				
			} else {
				session.setAttribute("action", "pay");
			}  
		}
		
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()) + "/homepage");
					
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
