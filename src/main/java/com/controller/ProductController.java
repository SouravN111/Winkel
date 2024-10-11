package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import com.bean.Product;
import com.dao.ProductDao;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 512, maxFileSize = 1024 * 1024 * 512, maxRequestSize = 1024 * 1024 * 512) //512mb
public class ProductController extends HttpServlet {
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
		
		if(action.equalsIgnoreCase("add product")) {
			
			Product p = new Product();
			p.setUid(Integer.parseInt(request.getParameter("uid")));
			p.setProduct_category(request.getParameter("product_category"));
			p.setProduct_name(request.getParameter("product_name"));
			p.setProduct_price(Integer.parseInt(request.getParameter("product_price")));
			p.setProduct_desc(request.getParameter("product_desc"));
			
			
			String savePath = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\product_picture";
			File fileSaveDir = new File(savePath);
			if(!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			Part file1 = request.getPart("product_picture");
			String filename = extractfilename(file1);
			file1.write(savePath + File.separator + filename);
			String savePath2 = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\product_picture";
			File imgSaveDir = new File(savePath2);
			if(!imgSaveDir.exists()) {
				imgSaveDir.mkdir();
			}
			p.setProduct_image(filename);
			ProductDao.addProduct(p);
			request.setAttribute("msg", "Product added successfully");
			request.getRequestDispatcher("seller-add-product.jsp").forward(request, response);
		}
			
		else if(action.equalsIgnoreCase("update product")) {
			
			Product p = new Product();
			p.setPid(Integer.parseInt(request.getParameter("pid")));
			p.setProduct_category(request.getParameter("product_category"));
			p.setProduct_name(request.getParameter("product_name"));
			p.setProduct_price(Integer.parseInt(request.getParameter("product_price")));
			p.setProduct_desc(request.getParameter("product_desc"));
			
			
			String savePath = "C:\\Users\\SOURAV\\eclipse-workspace\\Winkel\\src\\main\\webapp\\product_picture";
			File fileSaveDir = new File(savePath);
			if(!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			Part file1 = request.getPart("product_picture");
			String filename = extractfilename(file1);
			if (filename != null && !filename.isEmpty()) {
			    file1.write(savePath + File.separator + filename);
			    p.setProduct_image(filename);
			}
			
			
			ProductDao.updateProduct(p);
			request.setAttribute("msg", "Product updated successfully");
			request.getRequestDispatcher("seller-product-details.jsp?pid=" + p.getPid()).forward(request, response);
		}
		
		
	}
}
