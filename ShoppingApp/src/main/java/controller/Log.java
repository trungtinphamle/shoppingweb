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
 * Servlet implementation class Log
 */
public class Log extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log() {
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
		// Handle login
		else if(action.equals("login")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			// Only use cookie to login when redirect to homepage to loginpage
			boolean useCookie = false;
			if(email.equals("") || password.equals("")) {
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(Cookie cookie: cookies) {
						if(cookie.getName().equals("email")) {
							// get email from cookie
							email = cookie.getValue();
							password ="";
							useCookie = true;
						}
					}
				}
			}
			
			Account account = new Account(email, password);
			// Login is invalid then redirect to loginpage
			if(!account.validate()) {
				if(useCookie) {
					session.setAttribute("error", "");
				} else {
					session.setAttribute("error", account.getMessage());
				}
				session.setAttribute("email", account.getEmail());
				session.setAttribute("password", account.getPassword());
				response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/loginpage"));
			} else {
				AccountDS accDS = new AccountDS(conn);
				session.setAttribute("email", account.getEmail());
				session.setAttribute("password", account.getPassword());
				
				// Login is valid then redirect to admin page
				try {
					if(accDS.validate(account)) {
						if(request.getParameter("remember") != null) {
							Cookie emailCookie = new Cookie("email",email);
							emailCookie.setMaxAge(3600);
							response.addCookie(emailCookie);
						}
						accDS.update(account);
						
						session.setAttribute("name", account.getName());
						session.setAttribute("address", account.getAddress());
						session.setAttribute("error", null);
						response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() +"/admin"));
					} else {
						// redirect to loginpage
						session.setAttribute("error", "Wrong email or password");
						response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() +"/loginpage"));
					}
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (action.equals("logout")) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("name", "");
			// redirect to homepage when log out
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath() + "/homepage"));
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
