package com.userwebapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
  
    public void init() {
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			 connection=DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","Adiammu@vr1");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String emailid=request.getParameter("emailid");
		String password=request.getParameter("password");
		
		
		try(Statement statement=connection.createStatement();) {
			int result=statement.executeUpdate("insert into user values('"+firstname+"','"+lastname+"','"+emailid+"','"+password+"')");
			PrintWriter out=response.getWriter()	;
			if(result>0) {
				out.println("<h1>user created in db</h1>");
			}else {
				out.println("<h1>error creating user</h1>");
			}
				
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
