<%@page import="com.bean.Cart"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.CartDao"%>
<%
	int uid = Integer.parseInt(request.getParameter("uid"));
	CartDao.updatePayment(uid);
	List<Cart> list = CartDao.getCartByUser(uid);
	session.setAttribute("cart_count", list.size());
	response.sendRedirect("cart.jsp");


%>