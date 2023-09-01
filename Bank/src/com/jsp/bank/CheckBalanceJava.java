package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CheckBalanceJava")

public class CheckBalanceJava extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mb = req.getParameter("mb");
		String pass = req.getParameter("password");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select = "select * from bank where mobileNumber=? and pin=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mb);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				Random r = new Random();
				int otp = r.nextInt(10000);
				if (otp<1000) {
					
					otp = otp + 1000;
				}
				double damount = rs.getDouble("Amount");
				
				HttpSession session = req.getSession();
				session.setAttribute("otpp", otp);
				session.setAttribute("amount", damount);
				
				session.setMaxInactiveInterval(10);

				writer.println("<center><h1>"+otp+"</h1></center>");
				RequestDispatcher rd = req.getRequestDispatcher("OtpForCheckBalance.html");
				rd.include(req, resp);
				
			} else {

				RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid Details</h1></center>");				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}