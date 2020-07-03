<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/1
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕 - 商品详情</title>
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

        .hot-txt {
            display: inline-block;
        }

        .name, .price, .detail {
            display: block;
        }

        .price {
            color: #ff0000;
        }

        .detail-price {
            line-height: 35px;
        }

        .detail, .detail:hover {
            text-decoration: none;
        }

        #bigimg {
            height: 300px;
            margin-right: 20px;
        }

        #details {
            display: inline-block;
            width: 500px;
            overflow: hidden;
        }

        .btn-group-justified {
            width: 50%;
        }

        #addCart {
            background: #ee8625;
            color: #eeeeee;
        }

        #buyNow {
            background: #d22719;
            color: #eeeeee;
        }

        .goods-num-box {
            width: 190px;
            margin: 10px 0 15px 0;
        }

        #goods-num {
            height: 28px;
        }

    </style>

    <script>
        let isnum = /^[0-9]*$/

        $(function () {
            $('#plus').click(function () {
                $('#goods-num').val(parseInt($('#goods-num').val()) + 1)
            })
            $('#minus').click(function () {
                if ($('#goods-num').val() <= 1) {
                    $('#goods-num').val(1)
                } else {
                    $('#goods-num').val(parseInt($('#goods-num').val()) - 1)
                }
            })
            $('#goods-num').blur(function () {
                if (!isnum.test($('#goods-num').val())) {
                    $('#goods-num').val(1)
                }
            })

            $('#addCart').click(function () {
                $.post('${pageContext.request.contextPath}/addCart.goods',
                    {number : $('#goods-num').val(), id : $('#id').val()}, function (msg, status) {
                        if (status === 'success') {
                            if (msg === 200) {
                                toastr.success('添加成功!快去看看吧!')
                            } else if (msg === 300) {
                                toastr.warning('登录后就可以愉快地购物啦!')
                            } else {
                                toastr.error('添加失败!请重试!')
                            }
                        } else {
                            toastr.error('请求服务器失败!')
                        }
                    })
            })

            $('#buyNow').click(function () {
                window.location.href = '${pageContext.request.contextPath}/front_orderDetails.page?id=${requestScope.goods.goodsId}&num='
                    + $('#goods-num').val()
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
                <img src="${pageContext.request.contextPath}/static/img/goods/${requestScope.goods.image}" alt="" id="bigimg">
                <div id="details">
                    <div class="detail-txt">
                        <span>${requestScope.goods.details}</span>
                    </div>
                    <strong class="price detail-price">
                        <span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
                        ${requestScope.goods.price}
                    </strong>
                    <span>库存:${requestScope.goods.stock}</span>

                    <div>
                        <div class="input-group goods-num-box">
                            <span class="input-group-addon">数量</span>
                            <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="minus">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            </button>
                            </span>
                            <input type="text" class="form-control text-center" value="1" id="goods-num" name="number">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" id="plus">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                </button>
                            </span>
                        </div>
                    </div>

                    <input type="hidden" name="id" id="id" value="${requestScope.goods.goodsId}">
                    <div class="btn-group btn-group-justified" role="group">
                        <a href="#" class="btn" id="addCart">加入购物车</a>
                        <a href="#" class="btn" id="buyNow">立即购买</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
