<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="oracle.model.*"%>
<%
 request.setCharacterEncoding("UTF-8");  
 int user_id=Integer.parseInt(request.getParameter("user_id"));  

  UserBean ub = new UserBean();
  System.out.println("***delete*****");
  System.out.println(user_id);
  ub.deleteUser(user_id);
  response.sendRedirect("index.jsp");
%>