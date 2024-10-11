<%@page import="com.dao.CartDao"%>
<%@page import="com.bean.Product"%>
<%@page import="com.dao.ProductDao"%>
<%@page import="java.util.List"%>
<%@page import="com.bean.Cart"%>
<%@page import="com.dao.WishlistDao"%>
<%@ include file="header.jsp"%>

<%
	int pid = Integer.parseInt(request.getParameter("pid"));
	int uid = u.getUid();
	Cart c = new Cart();
	c.setUid(uid);
	c.setPid(pid);
	Product p = ProductDao.getProduct(pid);
	c.setProduct_price(p.getProduct_price());
	c.setProduct_qty(Integer.parseInt(request.getParameter("product_qty")));
	c.setTotal_price(p.getProduct_price()*Integer.parseInt(request.getParameter("product_qty")));
	CartDao.addToCart(c);
	List<Cart> list = CartDao.getCartByUser(u.getUid());
	session.setAttribute("cart_count", list.size());	
	response.sendRedirect("cart.jsp");
%>