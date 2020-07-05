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

    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })

        // 删除订单
        function deleteOrder(orderId) {
            $.post('${pageContext.request.contextPath}/deleteOrder.order',
                {id : orderId}, function (msg, status) {
                if (status === 'success') {
                    if (msg === 200) {
                        location.reload()
                    } else {
                        toastr.error('删除订单失败!')
                    }
                } else {
                    toastr.error('请求服务器失败!')
                }
            })
        }

        // 确认收货
        function checkOrder(orderId) {
            $.post('${pageContext.request.contextPath}/changeOrderStatus.order',
                {id : orderId, status : '已签收'}, function (msg, status) {
                if (status === 'success') {
                    if (msg === 200) {
                        location.reload()
                    } else {
                        toastr.error('确认收货失败!')
                    }
                } else {
                    toastr.error('请求服务器失败!')
                }
            })
        }
    </script>

    <style type="text/css">
        th {
            min-width: 90px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <table class="table table-striped">
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
                    <c:choose>
                        <c:when test="${order.status == '待发货'}">
                            <tr class="warning">
                                <td>${order.orderId}</td>
                                <td>${order.goodsname}</td>
                                <td>${order.userPhone}</td>
                                <td>${order.address}</td>
                                <td>${order.number}</td>
                                <td>${order.totalPrice}</td>
                                <td><fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${order.status}</td>
                                <td>
                                    <a class="btn btn-default btn-sm btn-danger" href="#"
                                       onclick="deleteOrder(${order.orderId})"
                                       role="button" data-toggle="tooltip" data-placement="top" title="删除订单">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${order.status == '待收货'}">
                            <tr class="info">
                                <td>${order.orderId}</td>
                                <td>${order.goodsname}</td>
                                <td>${order.userPhone}</td>
                                <td>${order.address}</td>
                                <td>${order.number}</td>
                                <td>${order.totalPrice}</td>
                                <td><fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${order.status}</td>
                                <td>
                                    <a class="btn btn-default btn-sm btn-primary" href="#"
                                       onclick="checkOrder(${order.orderId})"
                                       role="button" data-toggle="tooltip" data-placement="top" title="确认收货">
                                        <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                    </a>
                                    <a class="btn btn-default btn-sm btn-danger" href="#"
                                       onclick="deleteOrder(${order.orderId})"
                                       role="button" data-toggle="tooltip" data-placement="top" title="删除订单">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${order.status == '已签收'}">
                            <tr class="success">
                                <td>${order.orderId}</td>
                                <td>${order.goodsname}</td>
                                <td>${order.userPhone}</td>
                                <td>${order.address}</td>
                                <td>${order.number}</td>
                                <td>${order.totalPrice}</td>
                                <td><fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${order.status}</td>
                                <td>
                                    <a class="btn btn-default btn-sm btn-danger" href="#"
                                       onclick="deleteOrder(${order.orderId})"
                                       role="button" data-toggle="tooltip" data-placement="top" title="删除订单">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </table>

            <c:if test="${requestScope.array != null}">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li class="disabled">
                    <span aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </span>
                        </li>
                        <c:forEach items="${requestScope.array}" var="i" end="${requestScope.total - 1}">
                            <c:choose>
                                <c:when test="${requestScope.page == i}">
                                    <li class="active"><span>${i}</span></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${pageContext.request.contextPath}/listUserOrders.order?page=${i}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <li class="disabled">
                    <span aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </span>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
