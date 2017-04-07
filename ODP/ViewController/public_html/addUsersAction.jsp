<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="oracle.model.*"%>
<%
 request.setCharacterEncoding("UTF-8");  
 String user_name=request.getParameter("user_name");  
 int user_id=Integer.parseInt(request.getParameter("user_id"));  

 UserBean ub = new UserBean();

  System.out.println("***add*****");
 System.out.println(user_name);
  System.out.println(user_id);
 ub.addUser(user_name,user_id);
 response.sendRedirect("index.jsp");
%>