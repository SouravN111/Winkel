package com.controller;

import java.io.IOException;

import org.json.*;

import com.bean.User;
import com.razorpay.*;
@jakarta.servlet.annotation.WebServlet("/OrderCreation")
public class OrderCreation extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, IOException {
		RazorpayClient client=null;
		String orderId=null;
		System.out.println(request.getParameter("amount"));
		try {
			client=new RazorpayClient("rzp_test_X1iIiNlM3wnoAM","uN9i7rKeH46USrxliKu2kwJn");
			JSONObject options=new JSONObject();
			options.put("amount", request.getParameter("amount"));
			options.put("currency", "INR");
			options.put("receipt", "zxr456");
			options.put("payment_capture", true);
			Order order=client.orders.create(options);
			orderId=order.get("id");
			System.out.println("Order Id : "+orderId);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		response.getWriter().append(orderId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, IOException {
		RazorpayClient client=null;
		try {
			client=new RazorpayClient("rzp_test_X1iIiNlM3wnoAM","uN9i7rKeH46USrxliKu2kwJn");
			JSONObject options=new JSONObject();
			options.put("razorpay_payment_id",request.getParameter("razorpay_payment_id"));
			options.put("razorpay_order_id",request.getParameter("razorpay_order_id"));
			options.put("razorpay_signature",request.getParameter("razorpay_signature"));
			boolean SigRes=Utils.verifyPaymentSignature(options,"Secret");
			if(SigRes)
			{
				jakarta.servlet.http.HttpSession session=request.getSession();
				User u=(User) session.getAttribute("u");
				System.out.println(u.getFname());
				response.getWriter().append("Payment Successfull");
			}
			else
			{
				response.getWriter().append("Payment Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
