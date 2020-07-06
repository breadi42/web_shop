<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/5
  Time: 15:50
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

        function deleteGoods(goodsId) {
            $.post('${pageContext.request.contextPath}/deleteGoods.goods', {id : goodsId}, function (msg, status) {
                if (status === 'success') {
                    if (msg === 200) {
                        location.reload()
                    } else if (msg === 300) {
                        toastr.warning('删除了商品，但商品图片未删除!')
                    } else {
                        toastr.error('删除商品失败!')
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

        nav {
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav nav-pills nav-stacked text-center">
                <li role="presentation"><a href="${pageContext.request.contextPath}/manage_welcome.page">Home</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listUsers.user?page=1">用户管理</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/listOrders.order?page=1">订单管理</a></li>
                <li role="presentation" class="active">
                    <a href="${pageContext.request.contextPath}/listGoods.goods?page=1">商品管理</a>
                </li>
            </ul>
        </div>

        <div class="col-md-9">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/manage_welcome.page">Home</a></li>
                <li class="active">商品管理</li>
            </ol>
        </div>

        <div class="col-md-8">
            <table class="table table-striped">
                <tr>
                    <th>商品编号</th>
                    <th>商品名</th>
                    <th>商品分类</th>
                    <th>商品价格</th>
                    <th>商品详情</th>
                    <th>库存</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${requestScope.goodsList}" var="goods">
                    <tr>
                        <td>${goods.goodsId}</td>
                        <td>${goods.goodsName}</td>
                        <td>${goods.type}</td>
                        <td>${goods.price}</td>
                        <td>${goods.details}</td>
                        <td>${goods.stock}</td>
                        <td>
                            <a class="btn btn-default btn-sm btn-primary"
                               href="${pageContext.request.contextPath}/showGoods.goods?id=${goods.goodsId}"
                               role="button" data-toggle="tooltip" data-placement="top" title="修改商品信息">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                            </a>
                            <a class="btn btn-default btn-sm btn-danger" href="#" onclick="deleteGoods(${goods.goodsId})"
                               role="button" data-toggle="tooltip" data-placement="top" title="删除商品">
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
                                    <li><a href="${pageContext.request.contextPath}/listGoods.goods?page=${i}">${i}</a></li>
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

        <div class="col-md-1">
            <a class="btn btn-default btn-primary text-right"
               href="${pageContext.request.contextPath}/manage_addGoods.page" role="button">添加商品</a>
        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
