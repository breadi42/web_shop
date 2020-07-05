<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/5
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕 - 管理员</title>
    <jsp:include page="/static/header.jsp"/>

    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })

        // 使用Ajax请求删除用户
        function deleteUser(userId) {
            $.post('${pageContext.request.contextPath}/deleteUser.user', {id : userId}, function (msg, status) {
                if (status === 'success') {
                    if (msg === 200) {
                        location.reload()
                    } else {
                        toastr.error('删除用户失败!')
                    }
                } else {
                    toastr.error('请求服务器失败!')
                }
            })
        }
    </script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-pills nav-stacked text-center">
                <li role="presentation"><a href="${pageContext.request.contextPath}/manage_welcome.page">Home</a></li>
                <li role="presentation" class="active">
                    <a href="${pageContext.request.contextPath}/listUsers.user?page=1">用户管理</a>
                </li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listOrders.order?page=1">订单管理</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listGoods.goods?page=1">商品管理</a></li>
            </ul>
        </div>
        <div class="col-md-8">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/manage_welcome.page">Home</a></li>
                <li class="active">用户管理</li>
            </ol>

            <table class="table table-striped">
                <tr>
                    <th>用户编号</th>
                    <th>用户名</th>
                    <th>用户密码</th>
                    <th>收货电话</th>
                    <th>收货地址</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${requestScope.userList}" var="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.phone}</td>
                        <td>${user.address}</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-danger" href="#" onclick="deleteUser(${user.userId})"
                               role="button" data-toggle="tooltip" data-placement="top" title="删除用户">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            </a>
                        </td>
                    </tr>
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
                                    <li><a href="${pageContext.request.contextPath}/listUsers.user?page=${i}">${i}</a></li>
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
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
