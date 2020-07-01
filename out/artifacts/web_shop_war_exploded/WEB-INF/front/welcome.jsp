<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/29
  Time: 12:45
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

        #hot-goods img {
            width: 100px;
            height: 120px;
            margin-right: 20px;
        }

        .navbar {
            margin-bottom: 50px;
        }

        .hot-txt {
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

        #ppt-1, #ppt-2, #ppt-3 {
            cursor: pointer;
        }
    </style>

    <script>
        $(function () {
            $('#ppt-1').click(function () {
                window.location.href = '${pageContext.request.contextPath}/queryClothing.goods?page=1'
            })
            $('#ppt-2').click(function () {
                window.location.href = '${pageContext.request.contextPath}/queryElectronic.goods?page=1'
            })
            $('#ppt-3').click(function () {
                window.location.href = '${pageContext.request.contextPath}/queryFood.goods?page=1'
            })

        })

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
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                商品分类 <strong class="caret"></strong>
                            </a>
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

            <%-- 轮播图 --%>
            <div class="carousel slide" id="carousel-392831">
                <ol class="carousel-indicators">
                    <li class="active" data-slide-to="0" data-target="#carousel-392831"></li>
                    <li data-slide-to="1" data-target="#carousel-392831"></li>
                    <li data-slide-to="2" data-target="#carousel-392831"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="item active">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/ppt/ppt-1.jpg" id="ppt-1"/>
                    </div>
                    <div class="item">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/ppt/ppt-2.jpg" id="ppt-2"/>
                    </div>
                    <div class="item">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/ppt/ppt-3.jpg" id="ppt-3"/>
                    </div>
                </div>
                <a class="left carousel-control" href="#carousel-392831" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#carousel-392831" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
