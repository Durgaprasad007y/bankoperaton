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
@WebServlet("/OtpValidation")
public class OtpValidation  extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tuotp = req.getParameter("otp");
		int uotp=Integer.parseInt(tuotp);
		
		HttpSession session = req.getSession();
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		Integer otp = (Integer)session.getAttribute("otp");
		
		if (otp!=null) {
			
			if (uotp==otp) {
				RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
				rd.include(req, resp);
			} else {
				writer.println("<center><h1>"+otp+"</h1></center>");
				RequestDispatcher rd = req.getRequestDispatcher("Otp.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid OTP</h1></center>");
			}
			
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("WithDraw.html");
			rd.include(req, resp);
			writer.println("<center><h1>session time out </h1></center>");
		}
	}
}
