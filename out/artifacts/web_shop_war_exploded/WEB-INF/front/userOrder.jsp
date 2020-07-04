<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/3
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>并 夕 夕 - 我的订单</title>
    <jsp:include page="/static/header.jsp"/>

    <style type="text/css">
        th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <table class="table text-center">
                <tr>
                    <th>订单编号</th>
                    <th>商品</th>
                    <th>收货电话</th>
                    <th>收货地址</th>
                    <th>商品数量</th>
                    <th>总价</th>
                    <th>下单时间</th>
                    <th>订单状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${requestScope.orderList}" var="order">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.goodsname}</td>
                        <td>${order.userPhone}</td>
                        <td>${order.address}</td>
                        <td>${order.number}</td>
                        <td>${order.totalPrice}</td>
                        <td><fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <td>${order.status}</td>
                        <td>操作</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
