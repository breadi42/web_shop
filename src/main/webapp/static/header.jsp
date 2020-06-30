<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/30
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-theme.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/toastr.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/toastr.js"></script>

    <script>
        // toastr配置
        toastr.options = {
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-center",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "2000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }

        $(function () {
            // 点击网站名称回到首页
            $('#demo-home').click(function () {
                window.location.href = '${pageContext.request.contextPath}/index.go'
            })

            // 用户注销
            $('#user-logout').click(function () {
                $.get('${pageContext.request.contextPath}/logout.user', function (msg, status) {
                    if (status === 'success') {
                        if (msg === 200) {
                            window.location.href = '${pageContext.request.contextPath}/front_welcome.go'
                        } else {
                            alert('请先登录!')
                            window.location.href = '${pageContext.request.contextPath}/login.go'
                        }
                    } else {
                        alert('请求服务器失败!')
                    }
                })
            })

            // 管理员注销
            $('#manage-logout').click(function () {
                $.get('${pageContext.request.contextPath}/logout.manage', function (msg, status) {
                    if (status === 'success') {
                        if (msg === 200) {
                            window.location.href = '${pageContext.request.contextPath}/front_welcome.go'
                        } else {
                            alert('请先登录!')
                            window.location.href = '${pageContext.request.contextPath}/login.go'
                        }
                    }else {
                        alert('请求服务器失败!')
                    }
                })
            })

        })

    </script>

    <style type="text/css">
        body {
            margin-bottom: 60px;
        }

        .header-footer {
            background: #556a89;
        }

        .page-header {
            color: #eeeeee;
            margin: 0;
            border-bottom: 0;
            cursor: pointer;
        }

        .glyphicon-trash {
            margin-right: 10px;
            font-size: 50px;
        }

        .plah {
            height: 60px;
        }

        .glyphicon-shopping-cart, .glyphicon-leaf {
            color: #eeeeee;
            margin-right: -15px;
        }

        .options, .option {
            margin: 0 15px 0 15px;
            color: #eeeeee;
            text-decoration: none;
        }

        .option:hover {
            text-decoration: none;
            color: #556a89;
            background: #eeeeee;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row header-footer">
        <div class="col-md-4">
            <div class="page-header text-right" id="demo-home">
                <h1>
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    并 夕 夕 <small>全宇宙都在并的购物网站!</small>
                </h1>
            </div>
        </div>

        <div class="col-md-8">
            <div class="text-right plah"></div>
            <div class="text-right options">
                <c:choose>
                    <c:when test="${sessionScope.manage == null}">
                        <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                        <a href="${pageContext.request.contextPath}/front_cart.go" class="option">购物车</a>
                    </c:when>
                    <c:otherwise>
                        <span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.userCart != null}">
                        <a href="${pageContext.request.contextPath}/front_order.go" class="option">我的订单</a>
                        <a href="${pageContext.request.contextPath}/front_userInfo.go" class="option">
                            欢迎，${sessionScope.userCart.username}
                        </a>
                        <a href="#" class="option" id="user-logout">注销</a>
                    </c:when>
                    <c:when test="${sessionScope.manage != null}">
                        <span id="manage-font" class="option">管理员：${sessionScope.manage}</span>
                        <a href="#" class="option" id="manage-logout">注销</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login.go" class="option">您好，请登录</a>
                        <a href="${pageContext.request.contextPath}/signup.go" class="option">免费注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

</body>
</html>
