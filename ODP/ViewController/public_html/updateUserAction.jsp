<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="oracle.model.*"%>
<%
 request.setCharacterEncoding("UTF-8");  
 String user_name=request.getParameter("user_name");  
 int user_id=Integer.parseInt(request.getParameter("user_id"));  

  System.out.println("***UpdateAction*****");
 System.out.println(user_name);
  System.out.println(user_id);
  
  User user=new User();  
  user.setUserId(user_id);  
  user.setUserName(user_name);  
  UserBean ub = new UserBean();
  ub.updateUser(user);  
  response.sendRedirect("index.jsp");
%>