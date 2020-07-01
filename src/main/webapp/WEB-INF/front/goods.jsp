<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/1
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕</title>
    <jsp:include page="/static/header.jsp"/>

    <style type="text/css">
        h3 {
            color: #000000;
        }

        #hot-goods img, .goods img {
            width: 100px;
            height: 120px;
            margin-right: 20px;
        }

        .hot-txt {
            display: inline-block;
        }

        .goods {
            margin: 0 10px 10px 0;
            padding: 0;
            width: 200px;
            height: 200px;
            display: inline-block;
        }

        .name, .price, .detail {
            display: block;
        }

        .price {
            color: #ff0000;
        }

        .detail, .detail:hover {
            text-decoration: none;
        }
    </style>

    <script>
        function checkKey() {
            if ($('#key').val() === '') {
                toastr.warning('请输入关键字!')
                return false
            } else {
                return true
            }
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-3">
            <div class="page-header text-center">
                <h3>为你推荐</h3>
            </div>
            <div>
                <c:forEach items="${requestScope.hotGoods}" var="goods">
                    <div class="hot-goods" id="hot-goods">
                        <img src="${pageContext.request.contextPath}/static/img/goods/${goods.image}" alt="">
                        <div class="hot-txt">
                            <span class="name">${goods.goodsName}</span>
                            <strong class="price">
                                <span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
                                ${goods.price}
                            </strong>
                            <a href="${pageContext.request.contextPath}/details.goods?id=${goods.goodsId}" class="detail">查看详情</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-md-8">
            <nav class="navbar navbar-default navbar-static-top" role="navigation">
                <div class="navbar-header">
                    <div class="navbar-brand">
                        <span class="glyphicon glyphicon-tags" aria-hidden="true"></span>
                    </div>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <c:choose>
                                <c:when test="${requestScope.type.equals('Goods')}">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        全部商品 <strong class="caret"></strong>
                                    </a>
                                </c:when>
                                <c:when test="${requestScope.type.equals('Electronic')}">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        电子产品 <strong class="caret"></strong>
                                    </a>
                                </c:when>
                                <c:when test="${requestScope.type.equals('Clothing')}">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        服装 <strong class="caret"></strong>
                                    </a>
                                </c:when>
                                <c:when test="${requestScope.type.equals('Food')}">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        食品 <strong class="caret"></strong>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        商品分类 <strong class="caret"></strong>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/queryGoods.goods?page=1">全部商品 </a></li>
                                <li><a href="${pageContext.request.contextPath}/queryElectronic.goods?page=1">电子产品 </a></li>
                                <li><a href="${pageContext.request.contextPath}/queryClothing.goods?page=1">服装 </a></li>
                                <li><a href="${pageContext.request.contextPath}/queryFood.goods?page=1">食品 </a></li>
                            </ul>
                        </li>
                    </ul>

                    <form class="navbar-form navbar-left" role="search"
                          action="${pageContext.request.contextPath}/queryByKey.goods" onsubmit="return checkKey()">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="输入关键字查找" name="key" id="key"/>
                        </div>
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </form>
                </div>
            </nav>

            <div>
                <c:forEach items="${requestScope.goodsList}" var="goods">
                    <div class="goods text-center">
                        <img src="${pageContext.request.contextPath}/static/img/goods/${goods.image}" alt="">
                        <span class="name">${goods.goodsName}</span>
                        <strong class="price">
                            <span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
                            ${goods.price}
                        </strong>
                        <a href="${pageContext.request.contextPath}/details.goods?id=${goods.goodsId}" class="detail">查看详情</a>
                    </div>
                </c:forEach>
            </div>

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
                                    <li><a href="${pageContext.request.contextPath}/query${requestScope.type}.goods?page=${i}">${i}</a></li>
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
