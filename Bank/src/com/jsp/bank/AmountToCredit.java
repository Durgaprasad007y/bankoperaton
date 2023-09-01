package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/AmountToCredit")
public class AmountToCredit extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String samount = req.getParameter("amount");
		double uamount = Double.parseDouble(samount);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		HttpSession session = req.getSession();
		String mb = (String) session.getAttribute("mb");
		String pin  =(String) session.getAttribute("password");
		Double damount = (Double) session.getAttribute("damount");
		
		if (damount!=null) {
			
			if (uamount>0) {
				double add = damount+uamount;
				
				String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
				String update = "update bank set Amount=? where mobileNumber=? and pin=?";
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement ps = connection.prepareStatement(update);
					ps.setDouble(1, add);
					ps.setString(2, mb);
					ps.setString(3, pin);
					int num = ps.executeUpdate();
					
					if (num!=0) {
						RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
						rd.include(req, resp);
						writer.println("<center><h1>Credited Successfull..</h1></center>");
					} else {
						
						RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
						rd.include(req, resp);
						writer.println("<center><h1>404</h1></center>");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
					
				RequestDispatcher rd = req.getRequestDispatcher("AmountForCredit.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid Amount</center></h1>");
			}
			
		} else {

			RequestDispatcher rd = req.getRequestDispatcher("WelcomePage.html");
			rd.include(req, resp);
			writer.println("<center><h1>Session Time Out</h1></center>");
			
		}
	}

}