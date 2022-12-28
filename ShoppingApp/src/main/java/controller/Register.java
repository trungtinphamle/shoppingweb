package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import account.Account;
import dataSource.AccountDS;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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

		String action = request.getParameter("action");
		if(action == null) {
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/homepage"));
		}
		// Handle register
		else if(action.equals("register")) {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String repassword = request.getParameter("repassword");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
		
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			session.setAttribute("name", name);
			session.setAttribute("address", address);
			session.setAttribute("phone", phone);
			
			if(email == null) {
				session.setAttribute("error", "");
				response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/register.jsp"));
			}
			else if(!password.equals(repassword)) {
				session.setAttribute("error", "retyped password is different from password");
				response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/register.jsp"));
			} else {
				Account account = new Account(email, password, name, address, phone);
				// Login is invalid then redirect to register.jsp
				if(!account.validate()) {
					session.setAttribute("error", account.getMessage());
					response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/register.jsp"));
				} else {
					AccountDS accDS = new AccountDS(conn);
					// Login is valid then redirect to home page
					try {
						if(!accDS.exist(account)) {						
							accDS.addAccount(account);
							session.setAttribute("message", "successfully register");
							session.setAttribute("email", "");
							session.setAttribute("password", "");
							session.setAttribute("name", "");
							session.setAttribute("address", "");
							session.setAttribute("phone", "");
							response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() +"/homepage"));
						} else {
							// redirect to register.jsp
							session.setAttribute("error", "Email existed. Choose another email");
							response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() +"/register.jsp"));
						}
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
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
