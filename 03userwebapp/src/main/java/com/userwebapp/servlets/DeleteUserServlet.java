package com.userwebapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	  
    public void init(ServletConfig config) {
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			ServletContext context=config.getServletContext();
			String dburl=context.getInitParameter("dburl");
			String dbuser=context.getInitParameter("dbuser");
			String dbpassword=context.getInitParameter("dbpassword");
			connection =DriverManager.getConnection(dburl, dbuser, dbpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		String emailid=request.getParameter("emailid");
		
		
		
		try(Statement statement=connection.createStatement();) {
			int result=statement.executeUpdate("delete from user  where email = '"+emailid+"'");
			PrintWriter out=response.getWriter()	;
			if(result>0) {
				out.println("<h1>user deleted</h1>");
			}else {
				out.println("<h1>error deleting user </h1>");
			}
			out.println("<a href=\"index.html\">Home</a>");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}

	public void destroy() {
		 
			try {if(connection != null) {
				connection.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
}

