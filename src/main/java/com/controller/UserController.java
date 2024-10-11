package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bean.Cart;
import com.bean.User;
import com.bean.Wishlist;
import com.dao.CartDao;
import com.dao.UserDao;
import com.dao.WishlistDao;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 512, maxFileSize = 1024 * 1024 * 512, maxRequestSize = 1024 * 1024 * 512) //512mb
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String extractfilename(Part file) {
		String cd = file.getHeader("content-disposition");
		System.out.println(cd); // form-data; name="product_image"; filename="shoes.jpg"
		String[] items = cd.split(";");
		for(String string : items) {
			if(string.trim().startsWith("filename")) {
				return string.substring(string.indexOf("=") + 2, string.length() - 1);
			}
		}
		return "";
	}
	
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
 		String action = request.getParameter("action");
 		
 		if(action.equalsIgnoreCase("signup")) {
 			boolean flag = UserDao.checkMail(request.getParameter("email"));
 			if(flag==false) {
 				if(request.getParameter("password").equals(request.getParameter("cpassword"))) {
 					User u = new User();
 					u.setUsertype(request.getParameter("usertype"));
 					u.setFname(request.getParameter("fname"));
 					u.setLname(request.getParameter("lname"));
 					u.setEmail(request.getParameter("email"));
 					u.setMobile(Long.parseLong(request.getParameter("mobile")));
 					u.setAddress(request.getParameter("address"));
 					u.setPassword(request.getParameter("password"));
 					
 					String savePath = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\profile_picture";
 					File fileSaveDir = new File(savePath);
 					if(!fileSaveDir.exists()) {
 						fileSaveDir.mkdir();
 					}
 					Part file1 = request.getPart("profile_picture");
 					String filename = extractfilename(file1);
 					file1.write(savePath + File.separator + filename);
 					String savePath2 = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\profile_picture";
 					File imgSaveDir = new File(savePath2);
 					if(!imgSaveDir.exists()) {
 						imgSaveDir.mkdir();
 					}
 					u.setProfile_picture(filename);
 					
 					UserDao.signupUser(u);
 					
 					request.setAttribute("msg", "User registered successfully");
 					request.getRequestDispatcher("login.jsp").forward(request, response);
 				}
 				else {
 					request.setAttribute("msg", "Password and confirm password does not match");
 					request.getRequestDispatcher("signup.jsp").forward(request, response);
 				}
 			}
 			else {
 				request.setAttribute("msg", "User already resgistered");
 				request.getRequestDispatcher("login.jsp").forward(request, response);
 			}
 		}
 		
 		else if(action.equalsIgnoreCase("login")) {
 			User u = UserDao.loginUser(request.getParameter("email"));
 			if(u!=null) {
 				if(u.getPassword().equals(request.getParameter("password"))) {
 					HttpSession session = request.getSession();
 					session.setAttribute("u", u);
 					if(u.getUsertype().equals("Buyer")) {
 						List<Wishlist> list = WishlistDao.getWishlistByUser(u.getUid());
 						session.setAttribute("wishlist_count", list.size());
 						
 						List<Cart> listCart = CartDao.getCartByUser(u.getUid());
 						session.setAttribute("cart_count", listCart.size());
 						request.getRequestDispatcher("index.jsp").forward(request, response);
 					}
 					else {
 						request.getRequestDispatcher("seller-index.jsp").forward(request, response);
 					}
 				}
 				else {
 					request.setAttribute("msg", "Incorrect password");
 					request.getRequestDispatcher("login.jsp").forward(request, response);
 				}
 			}
 			else {
 				request.setAttribute("msg", "User does not exist. Please sign up first");
				request.getRequestDispatcher("signup.jsp").forward(request, response);
 			}
 		}
 		
 		else if(action.equalsIgnoreCase("change password")) {
 			HttpSession session = request.getSession();
 			User u =(User) session.getAttribute("u");
 			if(u.getPassword().equals(request.getParameter("old_password"))) {
 				if(request.getParameter("new_password").equals(request.getParameter("cnew_password"))) {
 					if(!request.getParameter("new_password").equals(request.getParameter("old_password"))) {
 						UserDao.changePassword(u.getEmail(), request.getParameter("new_password"));
 						request.setAttribute("msg", "Password changed successfully");
 						session.removeAttribute("u");
 						session.invalidate();
 						request.getRequestDispatcher("login.jsp").forward(request, response);
 					}
 					else {
							request.setAttribute("msg", "New password cannot be from one of the old passwords");

 						if(u.getUsertype().equals("Buyer")) {
 							request.getRequestDispatcher("change-password.jsp").forward(request, response);
 						}
 						else {
 							request.getRequestDispatcher("seller-change-password.jsp").forward(request, response);
 						}
 					}
 				}
 				else {
 					request.setAttribute("msg", "New password and confirm new password does not match");
 					if(u.getUsertype().equals("Buyer")) {
							request.getRequestDispatcher("change-password.jsp").forward(request, response);
						}
						else {
							request.getRequestDispatcher("seller-change-password.jsp").forward(request, response);
						}
 					}
 			}
 			else {
 				request.setAttribute("msg", "Incorrect old password");
 				if(u.getUsertype().equals("Buyer")) {
						request.getRequestDispatcher("change-password.jsp").forward(request, response);
					}
					else {
						request.getRequestDispatcher("seller-change-password.jsp").forward(request, response);
					}
 				}
 		}
 		
 		else if(action.equalsIgnoreCase("update profile")) {
 			HttpSession session = request.getSession();
 			User u =(User) session.getAttribute("u");
 			u.setUsertype(request.getParameter("usertype"));
 			u.setFname(request.getParameter("fname"));
 			u.setLname(request.getParameter("lname"));
 			u.setMobile(Long.parseLong(request.getParameter("mobile")));
 			u.setAddress(request.getParameter("address"));
 			
 			String savePath = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\profile_picture";
			File fileSaveDir = new File(savePath);
			if(!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			Part file1 = request.getPart("profile_picture");
			String filename = extractfilename(file1);
			if (filename != null && !filename.isEmpty()) {
			    file1.write(savePath + File.separator + filename);
			    u.setProfile_picture(filename);
			}
 			
 			UserDao.updateProfile(u);
 			session.setAttribute("u", u);
 			request.setAttribute("msg", "Profile updated successfully");
 			if(u.getUsertype().equals("Buyer")) {
 				request.getRequestDispatcher("profile.jsp").forward(request, response);
 			}
 			else {
 				request.getRequestDispatcher("seller-profile.jsp").forward(request, response);
 			}
 		}
	}
}
