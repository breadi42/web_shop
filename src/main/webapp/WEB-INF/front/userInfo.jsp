<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/30
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>并 夕 夕 - 个人中心</title>
    <jsp:include page="/static/header.jsp"/>

    <style type="text/css">
        .box-title {
            margin-bottom: 25px;
        }

        .info-box {
            width: 105%;
        }
    </style>

    <script>
        let tPhone = /^([1][3,4,5,6,7,8,9])\d{9}$/

        $(function () {
            $('.update-do').click(function () {
                $('.form-control').removeAttr('disabled')
                $('#password').attr('type', 'text')
                $('#cancel').attr('type', 'button')
                $('#confirm').attr('type', 'submit')
                $('.update-do').attr('type', 'hidden')
            })

            $('#cancel').click(function () {
                window.location.href = '${pageContext.request.contextPath}/front_userInfo.page'
            })

            $('#confirm').click(function () {
                if (!tPhone.test($('#phone').val())) {
                    toastr.warning('请输入正确的手机号!')
                } else if ($('#username').val() === '') {
                    toastr.warning('用户名不能为空!')
                } else if ($('#password').val() === '') {
                    toastr.warning('密码不能为空!')
                } else {
                    $.post('${pageContext.request.contextPath}/updateUser.user', $('#update-form').serialize(),
                        function (msg, status) {
                        if (status === 'success') {
                            if (msg === 200) {
                                window.location.href = '${pageContext.request.contextPath}/front_userInfo.page'
                            } else {
                                toastr.error('修改个人信息失败!')
                            }
                        } else {
                            toastr.error('请求服务器失败!')
                        }
                    })
                }
            })
        })
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <div class="box-title text-center">
                <h3>用&nbsp户&nbsp信&nbsp息</h3>
            </div>
            <div class="info-box">
                <form class="form-horizontal" id="update-form" method="post">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${sessionScope.userCart.user.username}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" name="password"
                                   value="${sessionScope.userCart.user.password}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-2 control-label">手机号码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="phone" name="phone"
                                   value="${sessionScope.userCart.user.phone}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">收货地址</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="address" name="address"
                                   value="${sessionScope.userCart.user.address}" disabled>
                        </div>
                    </div>
                    <div class="btn-group btn-group-justified" role="group">
                        <div class="btn-group" role="group">
                            <input type="hidden" class="btn btn-default" id="cancel" value="取消修改">
                        </div>
                        <div class="btn-group" role="group">
                            <input type="hidden" class="btn btn-default" id="confirm" value="确认修改">
                        </div>
                    </div>
                </form>
                <div class="btn-group btn-group-justified" role="group">
                    <div class="btn-group" role="group">
                        <input type="button" class="btn btn-default update-do" value="修改信息">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
