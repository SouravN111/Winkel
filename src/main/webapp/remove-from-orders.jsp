<%@page import="java.util.List"%>
<%@page import="com.dao.CartDao"%>
<%@page import="com.bean.Cart"%>
<%@page import="com.dao.WishlistDao"%>
<%@page import="com.bean.Wishlist"%>
<%@ include file="header.jsp"%>

<%
int pid = Integer.parseInt(request.getParameter("pid"));
	int uid = u.getUid();
	Cart c = new Cart();
	c.setUid(uid);
	c.setPid(pid);
	CartDao.removeFromCart(c);
	List<Cart> list = CartDao.getCartByUser(u.getUid());
	session.setAttribute("cart_count", list.size());
	response.sendRedirect("orders.jsp");
%>