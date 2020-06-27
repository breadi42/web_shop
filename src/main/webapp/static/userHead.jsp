<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/27
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>网上商城</title>

    <jsp:include page="head.jsp"/>

    <style type="text/css">
        .container {
            width: 100%;
            margin: 0;
            padding: 0;
        }

        .header-footer {
            background: #30488e;
            margin: 0;
        }

        #shop-title {
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
        }

        a {
            text-decoration: none;
            color: #eeeeee;
            font-size: 14px;
        }

        a:hover {
            background: #eeeeee;
            color: #30488e;
            text-decoration: none;
            border-radius: 3px;
        }

        .glyphicon {
            color: #eeeeee;
        }

        .page-header {
            height: 30px;
        }

    </style>

</head>
<body>
<div class="container">
    <div class="row header-footer">
        <div class="col-md-4 ">
            <div class="page-header">
                <h1 id="shop-title">
                    网&nbsp上&nbsp商&nbsp城
                </h1>
            </div>
        </div>
        <div class="col-md-8">
            <div class="options">
                <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                <a href="#" class="user-options">购物车</a>
                <a href="#" class="user-options">登录</a>
                <a href="#" class="user-options">注册</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
