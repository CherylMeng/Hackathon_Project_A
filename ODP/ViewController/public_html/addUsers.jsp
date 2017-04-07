<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
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
            name_msg.innerHTML="<font color='red'>*Name is requred!</font>";  
            name.focus();  
            return ;  
        }          
        document.forms[0].submit();  
    }  
    </script>
    <body>
    <h1 align="center">New User Info</h1>  
    <form action="addUsersAction.jsp" method="post" >  
        <table align="center" width="50%">  
            <!-- 姓名 -->  
            <tr>  
                <td align="right" width="37%">Name:</td>  
                <td align="left" width="25%"><input type="text" name="user_name" ></td>   
                <td>  
                    <div id="name_msg" align="left"></div>  
                </td>  
            </tr>  
            <!-- id -->  
            <tr>  
                <td align="right" width="37%">ID:</td>  
                <td align="left" width="25%"><input type="text" name="user_id" ></td>   
                <td>  
                    <div id="id_msg" align="left"></div>  
                </td>  
            </tr> 

            <!-- 按钮 -->  
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