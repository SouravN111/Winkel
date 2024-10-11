<%
	session.removeAttribute("u");
	session.invalidate();
	request.setAttribute("msg", "User logged out successfully");
	request.getRequestDispatcher("login.jsp").forward(request, response);

%>