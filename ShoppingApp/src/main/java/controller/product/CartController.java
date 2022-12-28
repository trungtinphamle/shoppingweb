package controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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

import dataSource.ListProductDS;
import dataSource.OrdersDS;
import model.Cart;
import model.Orders;
import model.Product;

/**
 * Servlet implementation class CartController
 */
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
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
		session.setMaxInactiveInterval(3600);
		PrintWriter out = response.getWriter();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		// Xử lý thêm sản phẩm vào Cart
		int productId = Integer.parseInt(request.getParameter("id"));
		ListProductDS listProduct = new ListProductDS(conn);
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			cart = new Cart();
		}
		
		// Thêm sản phẩm thành công -> chuyển đến home page
		try {
			Product product = listProduct.getProduct(productId);
			cart.add(product);
			session.setAttribute("cart", cart);
			session.setAttribute("message", "successfully add to cart");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			session.setAttribute("message", "add to cart failed");
			e1.printStackTrace();
		}
		
		session.setAttribute("action", null);	

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
