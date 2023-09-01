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

@WebServlet("/OtpVerifyForCheckblnc")

public class OtpVerifyForCheckblnc extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String sotp = req.getParameter("otp");
		int iotp = Integer.parseInt(sotp);
		
		HttpSession session = req.getSession();
		Integer otp = (Integer) session.getAttribute("otpp");
		Double damount = (Double) session.getAttribute("amount");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		
		if (otp!=null) {
			
			if (iotp == otp) {
//				RequestDispatcher rd = req.getRequestDispatcher("WelComePage.html");
//				rd.include(req, resp);
				writer.println("<center><h1> Your Balance is :"+damount+"</h1></center>" );
			} else {
				
				RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
				rd.include(req, resp);
				writer.println("<center><h1>In-Correct OTP</h1></center>");
			}
			
		} else {

			RequestDispatcher rd = req.getRequestDispatcher("Welcome.html");
			rd.include(req, resp);
			writer.println("<center><h1>Session Time Out</h1></center>");
			
		}
		
	}

}