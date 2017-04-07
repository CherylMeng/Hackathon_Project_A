<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="oracle.model.*"%>
<%
 request.setCharacterEncoding("UTF-8");  
 int user_id=Integer.parseInt(request.getParameter("user_id"));  

  System.out.println("***Update*****");
  System.out.println(user_id);
  UserBean ub = new UserBean();
  User user=ub.getUserById(user_id);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>addUsers</title>
    </head>
    <script type="text/javascript">  
    function myBack(){  
        location.href="index.jsp";  
        //window.location.href="index.jsp";  
    }  
    function mySubmit(){  
        var name=document.forms[0].user_name;  
        var name_msg=document.getElementById("name_msg");  
        var id_msg=document.getElementById("id_msg");  
        name_msg.innerHTML="";  
        id_msg.innerHTML="";  
        if(name.value.length==0){  
            name_msg.innerHTML="<font color='red'>*Name is required</font>";  
            name.focus();  
            return ;  
        }          
        document.forms[0].submit();  
    }  
    </script>
    <body>
    <h1 align="center">Update Info</h1>  
    <form action="updateUserAction.jsp" method="post" >  
        <table align="center" width="50%">  
            <!-- name -->  
            <tr>  
                <td align="right" width="37%">Name:</td>  
                <td align="left" width="25%"><input type="text" name="user_name" value="<%=user.getUserName()%>" ></td>   
                <td>  
                    <div id="name_msg" align="left"></div>  
                </td>  
            </tr>  
            <!-- id -->  
            <tr>  
                <td align="right" width="37%">ID:</td>  
                <td align="left" width="25%"><input type="text" name="user_id"  value="<%=user.getUserId()%>"></td>   
                <td>  
                    <div id="id_msg" align="left"></div>  
                </td>  
            </tr> 

            <!-- button -->  
            <tr>  
                <td align="center" colspan="3">  
                    <input type="button" value="Submit" onclick="mySubmit()">  
                         
                    <input type="button" value="Back"  onclick="myBack()">  
                </td>  
            </tr>  
        </table>  
    </form>
    </body>
</html>