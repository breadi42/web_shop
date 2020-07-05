<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/29
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>并 夕 夕 - 管理员</title>
    <jsp:include page="/static/header.jsp"/>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-pills nav-stacked text-center">
                <li role="presentation" class="active"><a href="#">Home</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listUsers.user?page=1">用户管理</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listOrders.order?page=1">订单管理</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listGoods.goods?page=1">商品管理</a></li>
            </ul>
        </div>
        <div class="col-md-8">
            <ol class="breadcrumb">
                <li class="active">Home</li>
            </ol>
            <div class="jumbotron">
                <h1>您好, 管理员!</h1>
                <p>在这里，您可以轻松管理并夕夕的用户</p>
                <p>在这里，您可以轻松管理并夕夕的订单</p>
                <p>在这里，您可以轻松管理并夕夕的商品</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
