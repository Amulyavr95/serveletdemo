package com.demos.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/addproduct")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	 private 	PreparedStatement preparedStatement ;
  
    public void init(ServletConfig config) {
    	
    	try {
    		
			ServletContext context=config.getServletContext();
			String dburl=context.getInitParameter("dburl");
			String dbuser=context.getInitParameter("dbuser");
			String dbpassword=context.getInitParameter("dbpassword");
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection(dburl, dbuser, dbpassword);
			preparedStatement=connection.prepareStatement("insert into product1 values(?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id=request.getParameter("id");
		String pname=request.getParameter("pname");
		String description=request.getParameter("description");
		String price=request.getParameter("price");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		if(!isValidInput(id,true)||!isValidInput(pname,false)||!isValidInput(description,false)||!isValidInput(price,true)) {
			out.println("<h1> plz enter valid input</p>");
			return;
		}
		try{
			preparedStatement.setInt(1,Integer.parseInt(id));
			preparedStatement.setString(2, pname);
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4,Integer.parseInt(price));
			preparedStatement.executeUpdate();
			       out.println("Product Created. ");
			} catch (SQLException e) {
				out.println("Product not created.Error message="+e.getMessage());
				e.printStackTrace();
			}
		}	
		
	
	private boolean isValidInput(String inputValue,boolean isNumber) {
		if(inputValue==null||inputValue.isBlank()||inputValue.isEmpty()) {
			return false;
		}else if(isNumber) {
			try {
				Integer.parseInt(inputValue);
				return true;
			}catch(NumberFormatException nfe) {
				return false;
			}
		}else {
				
			return true;
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
