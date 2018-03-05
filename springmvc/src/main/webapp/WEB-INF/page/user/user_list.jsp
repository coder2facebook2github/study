<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="../common/header.jsp" %>
</head>
<body>
<%@include file="../common/nav.jsp" %>
<div class="container theme-showcase" role="main">
    <div class="panel panel-success">
        <div class="panel-heading" style="padding-bottom: 20px;">

                <span class="">用户列表</span>

                <span class="btn btn-default btn-sm pull-right" id="export-user">用户导出</span>

        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>用户Id</th>
                    <th>用户姓名</th>
                    <th>用户密码</th>
                    <th>用户手机号</th>
                    <th>用户创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td><a href="javascript:void(0);" style="text-decoration: none;"><span id="user_id_${user.id}" class="user_id">${user.id}</span></a></td>
                        <td>
                            <a href="javascript:void(0);" onclick="autoDownload('/get/user/in')" style="text-decoration: none;">${user.nickname}</a>
                        </td>
                        <td>${user.password}</td>
                        <td>${user.mobile}</td>
                        <td><fmt:formatDate value="${user.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                        <td>
                            <span href="" class="btn btn-warning btn-sm user-modify" user-id="${user.id}">修改</span>
                            <span class="btn btn-danger btn-sm user-delete" user-id="${user.id}">删除</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="bootstrap-modal">
        <div class="modal fade" id="user-modify-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <h4 class="modal-title">修改用户——<span id="user-name"></span></h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="user-modify-form" action="/user/modify" method="post">
                            <div class="form-group">
                                <input type="hidden" name="id" id="userId"/>
                                <label class="control-label col-sm-2" for="nickname">昵称</label>
                                <div class="col-sm-8">
                                    <input type="text" id="nickname" name="nickname" class="form-control" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="password">密码</label>
                                <div class="col-sm-8">
                                    <input type="text" id="password" name="password" class="form-control"
                                           required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="mobile">手机号</label>
                                <div class="col-sm-8">
                                    <input type="text" id="mobile" name="mobile" class="form-control"
                                           required="required"/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <span class="btn btn-success" id="submit">提交</span>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $(".user-delete").click(function () {
            var userId = $(this).attr("user-id");
            if (confirm("确认删除该用户 ？")) {
                $.ajax({
                    url: '/delete/user',
                    type: 'post',
                    dataType: 'json',
                    data: {userId: userId},
                    success: function (data) {
                        if (data.success) {
                            window.location.href = "/user/list";
                        }
                    }
                });
            }
        });

        $(".user-modify").click(function () {
            var userId = $(this).attr('user-id');
            var user = {};
            $.ajax({
                type: 'get',
                url: '/get/user/info',
                async: false,
                data: {userId: userId},
                success: function (data) {
                    user = data.user;
                }
            });
            $("#userId").val(userId);
            $("#user-name").text(user.nickname);
            $("#nickname").val(user.nickname);
            $("#mobile").val(user.mobile);
            $("#password").val(user.password);
            $("#user-modify-modal").modal('show');
        });
        $("#submit").click(function () {
            $("#user-modify-form").submit();
        });
        $("#export-user").click(function(){
            autoDownload("/export/user");
        });
        $(".user_id").click(function() {
            var userId = $(this).attr("id").split("_")[2];
            $.ajax({
                type: 'get',
                url: '/get/user/id',
                async: false,
                dataType: "json",
                data: {userId: userId},
                success: function (data) {
                    console.log(JSON.stringify(data));
                    alert(JSON.stringify(data));
                }
            });
        });
    });
    function autoDownload(url) {
        var formEle = $('<form action="'+url+'" method="post"></form>');
        formEle.appendTo('body');
        formEle.submit().remove();
    }
</script>
</body>
</html>
