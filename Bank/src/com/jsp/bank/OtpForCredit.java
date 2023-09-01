package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/OtpForCredit")
public class OtpForCredit extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String sotp = req.getParameter("otp");
		int iotp = Integer.parseInt(sotp);
		
		HttpSession session = req.getSession();
		Integer otp = (Integer) session.getAttribute("otp");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
				
		if (otp!=null) {
			
			if (iotp==otp) {
				
				RequestDispatcher rd = req.getRequestDispatcher("AmountForCredit.html");
				rd.include(req, resp);
				
			} else {
				
				RequestDispatcher rd = req.getRequestDispatcher("OtpForCredit.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid OTP</h1></center>");				
			}
			
		} else {
			
			RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
			rd.include(req, resp);
			writer.println("<center><h1>session time out</h1></center>");
		}
	}
}