<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="oracle.model.*"%>
<!DOCTYPE html>
<body>
    <%@ include file="navigation.jsp"%>
    <div id="main-content" class="clearfix"> 
        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <h3 class="header smaller lighter blue"></h3> 
                <table id="table_report" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>名称</th>
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
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <!--/row--> 
                    <button class="btn btn-info" onclick="deleteFarm(1)" style="button" type="submit">新增</button>
             
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
</body>
<script type="text/javascript">
      $(function () {
          var oTable1 = $('#table_report').dataTable( {
              "aoColumns" : [null, null, null]
          });
      })
		</script>
<!-- Mirrored from easy-themes.tk/themes/preview/ace/blank.html by HTTrack Website Copier/3.x [XR&CO'2010], Wed, 29 May 2013 02:41:18 GMT -->