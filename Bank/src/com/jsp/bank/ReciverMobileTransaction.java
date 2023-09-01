package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ReciverMobileTransaction")
public class ReciverMobileTransaction extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mb = req.getParameter("mb");
		
		String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		
		String select = "select * from bank where mobilenumber=?";
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mb);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				double rdamount = rs.getDouble(3);
				session.setAttribute("rdamount", rdamount);
				session.setAttribute("rmb", mb);
				RequestDispatcher rd = req.getRequestDispatcher("TransactionAmount.html");
				rd.include(req, resp);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("ReciverMobileTransaction.html");
				rd.include(req, resp);
				writer.println("<center><h1>InValid Mobile Number</h1></center>");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
