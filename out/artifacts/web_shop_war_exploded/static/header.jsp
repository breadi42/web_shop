<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/27
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>并 夕 夕</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-theme.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

    <style type="text/css">
        html, body {
            width: 100%;
            margin: 0;
            padding: 0;
            overflow-x: hidden;
        }

        .container {
            width: 100%;
            margin: 0;
            padding: 0;
        }

        .header-footer {
            background: #556a89;
            margin: 0;
        }

        .shop-title {
            text-align: center;
            color: aliceblue;
            line-height: 0;
        }

        .options {
            position: absolute;
            left: 70%;
            top: 10%;
            transform: translate(0, 400%);
            line-height: 0;
        }

        .user-options {
            margin-right: 15px;
            text-decoration: none;
            color: #eeeeee;
            font-size: 14px;
        }

        .user-options:hover {
            background: #eeeeee;
            color: #556a89;
            text-decoration: none;
            border-radius: 3px;
        }

        #shopping-cart {
            color: #eeeeee;
        }

        .page-header {
            height: 30px;
        }

        #demo-home {
            cursor: pointer;
        }

        #memo {
            position: relative;
            left: 150px;
        }

    </style>

    <script>
        $(function () {
            // 点击网站名称回到首页
            $('#demo-home').click(function () {
                window.location.href = '${pageContext.request.contextPath}'
            })

            // 注销
            $('#logout').click(function () {
                $.get('${pageContext.request.contextPath}/logout.user', function (msg, status) {
                    if (status === 'success') {
                        if (msg === 200) {
                            window.location.href = '${pageContext.request.contextPath}'
                        } else {
                            alert('请先登录!')
                        }
                    } else {
                        alert('请求服务器失败!')
                    }
                })
            })
        })
    </script>

</head>
<body>
<div class="container">
    <div class="row header-footer">
        <div class="col-md-4">
            <div class="page-header" id="demo-home">
                <h1 class="shop-title">
                    并&nbsp夕&nbsp夕
                </h1>
                <div class="shop-title" id="memo">全宇宙都在并的购物网站!!!</div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="options">
                <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true" id="shopping-cart"></span>
                <a href="#" class="user-options">购物车</a>
                <c:choose>
                    <c:when test="${sessionScope.userCart != null}">
                        <a href="#" class="user-options">欢迎，${sessionScope.userCart.username}</a>
                        <a href="#" class="user-options" id="logout">注销</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login.go" class="user-options">您好，请登录</a>
                        <a href="#" class="user-options">免费注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>
