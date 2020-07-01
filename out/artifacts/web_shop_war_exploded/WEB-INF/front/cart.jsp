<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/29
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕</title>
    <jsp:include page="/static/header.jsp"/>

    <style>
        .cart-goods {
            display: inline-block;
            margin: 0 20px 20px 0;
        }

        .cart-goods img {
            width: 100px;
            height: 120px;
            margin-right: 20px;
            margin-top: -90px;
        }

        .cart-goods-info {
            display: inline-block;
            margin-top: 15px;
        }

        .cart-goods-txt {
            display: block;
        }

        .price {
            color: #ff0000;
        }

        .goods-num-box {
            width: 110px;
            margin: 10px 0 15px 0;
        }

        #goods-num {
            height: 28px;
        }

        #remove, #buyNow {
            text-decoration: none;
            color: #556a89;
            margin-right: 15px;
        }

        #remove:hover, #buyNow:hover {
            color: #eeeeee;
            background: #556a89;
            text-decoration: none;
        }

        .no-cart {
            margin-left: 140px;
        }
    </style>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-8 text-left">
            <c:choose>
                <c:when test="${sessionScope.userCart.cartGoodsList != null}">
                    <c:forEach items="${sessionScope.userCart.cartGoodsList}" var="goods">
                        <div class="cart-goods">
                            <img src="${pageContext.request.contextPath}/static/img/goods/${goods.image}" alt="">
                            <div class="cart-goods-info">
                                <span class="cart-goods-txt">${goods.goodsName}</span>
                                <span class="cart-goods-txt">
                                    <strong class="price">
                                        <span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
                                        ${goods.price * goods.number}
                                    </strong>
                                </span>

                                <div>
                                    <div class="input-group goods-num-box">
                                        <span class="input-group-addon">数量</span>
                                        <input type="text" class="form-control text-center" value="${goods.number}" id="goods-num" name="number" disabled>
                                    </div>
                                </div>

                                <div>
                                    <a href="#" id="remove">移除</a>
                                    <a href="#" id="buyNow">立即购买</a>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-cart">
                        <img src="${pageContext.request.contextPath}/static/img/cart.jpg" alt="">
                        <strong>购物车空空如也~~~快去选购吧!</strong>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
