<%@page import="java.util.List"%>
<%@page import="com.dao.WishlistDao"%>
<%@page import="com.bean.Wishlist"%>
<%@ include file="header.jsp"%>

<%
int pid = Integer.parseInt(request.getParameter("pid"));
	int uid = u.getUid();
	Wishlist w = new Wishlist();
	w.setUid(uid);
	w.setPid(pid);
	WishlistDao.removeFromWishlist(w);
	List<Wishlist> list = WishlistDao.getWishlistByUser(u.getUid());
	session.setAttribute("wishlist_count", list.size());
	response.sendRedirect("wishlist.jsp");
%>