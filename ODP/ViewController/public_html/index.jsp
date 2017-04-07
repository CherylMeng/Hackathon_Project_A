<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="oracle.model.*"%>
<!DOCTYPE html>
    <script type="text/javascript">  
    function myDelete(id){  
        window.location.href='deleteUser.jsp?user_id='+id
        //window.location.href="index.jsp";  
    } 
    function myUpdate(id){  
        window.location.href='updateUser.jsp?user_id='+id
        //window.location.href="index.jsp";  
    }
    </script>
<body>
    <%@ include file="navigation.jsp"%>
    <div id="main-content" class="clearfix"> 
        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <h3 class="header smaller lighter blue"></h3> 
                <table id="table_report" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Operation</th>
                        </tr>
                    </thead>
                     
                    <tbody>
                        <%
                    UserBean ub = new UserBean();
                    List<User> list = ub.getAllUser();
                    for(User user:list){
                %>
                        <tr>
                            <td>
                                <%=user.getUserId()%>
                            </td>
                            <td>
                                <%=user.getUserName()%>
                            </td>
                            <td>
                                  <button class="btn btn-info" onclick="myUpdate(<%=user.getUserId()%>)" style="button" type="submit">Update</button>  
                                  <button class="btn btn-info" onclick="myDelete(<%=user.getUserId()%>)" style="button" type="submit">Delete</button>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <!--/row--> 
                     <button class="btn btn-info" onclick="window.location.href='addUsers.jsp'" style="button" type="submit">Add</button>
             
        </div>
        <!--/#page-content-->
    </div>
    <!--/#main-content-->
    <a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i></a>
    <script src="../../../../ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript">
      window.jQuery || document.write("<script src='assets/js/jquery-1.9.1.min.js'>" + "<" + "/script>");
    </script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.dataTables.min.js"></script>
    <script src="assets/js/jquery.dataTables.bootstrap.js"></script>
    <!--page specific plugin scripts-->
    <!--ace scripts-->
    <script src="assets/js/ace-elements.min.js"></script>
    <script src="assets/js/ace.min.js"></script>
    <script>
      function deleteFarm(id) {
		var str = "appCode=00000000&serviceName=UserController&operation=sayhello";
		$.ajax({
				url:"controller",
				type:"post",
				data:str,
				dataType:"json",
				success:function(data){alert(data.returnMsg);} 
				});
        
      }
    </script>
        <script>
      function myNewDelete(id) {
		var str = "user_id="+id;
		$.ajax({
				url:"deleteUser.jsp",
				type:"post",
				data:str,
				success:function(data){alert(data.returnMsg);} 
				});
        
      }
    </script>
</body>
<script type="text/javascript">
      $(function () {
          var oTable1 = $('#table_report').dataTable( {
              "aoColumns" : [null, null, null]
          });
      })
</script>
<!-- Mirrored from easy-themes.tk/themes/preview/ace/blank.html by HTTrack Website Copier/3.x [XR&CO'2010], Wed, 29 May 2013 02:41:18 GMT -->