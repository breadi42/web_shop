<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/30
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>并 夕 夕</title>
    <jsp:include page="/static/header.jsp"/>

    <style type="text/css">
        .box-title {
            margin-bottom: 25px;
        }

        img {
            height: 500px;
        }

        .goods-name {
            margin-top: 20px;
            display: block;
        }

        .details-box {
            width: 105%;
        }
    </style>

    <script>
        let isnum = /^[0-9]*$/
        let tPhone = /^([1][3,4,5,6,7,8,9])\d{9}$/

        $(function () {
            $('#number').blur(function () {
                if (!isnum.test($('#number').val()) || $('#number').val() <= 0) {
                    toastr.warning('商品数量请输入非负的整数!')
                    $('#number').val(${requestScope.goods.number})
                }
                let totalPrice = ${requestScope.goods.price} * $('#number').val()
                $('#totalPrice').val(totalPrice)
            })

            $('#phone').blur(function () {
                if (!tPhone.test($('#phone').val())) {
                    toastr.warning('请输入正确的手机号码!')
                    $('#phone').val(${requestScope.user.phone})
                }
            })

            $('#address').blur(function () {
                if ($('#address').val() === '') {
                    toastr.warning('收货地址不能为空!')
                }
            })

            $('#cancel').click(function () {
                window.history.back()
            })
        })

        function checkAd() {
            if ($('#address').val() === '') {
                toastr.error('请填写收货地址!')
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
        <div class="col-md-2"></div>
        <div class="col-md-4 text-center">
            <img src="${pageContext.request.contextPath}/static/img/goods/${requestScope.goods.image}" alt="">
            <strong class="goods-name">${requestScope.goods.goodsName}</strong>
        </div>
        <div class="col-md-4">
            <div class="box-title text-center">
                <h3>订&nbsp单&nbsp详&nbsp情</h3>
            </div>

            <div class="details-box">
                <form action="${pageContext.request.contextPath}/buyGoods.order"
                      class="form-horizontal" method="post" onsubmit="return checkAd()">
                    <input type="hidden" name="userId" value="${requestScope.user.userId}">
                    <input type="hidden" name="goodsId" value="${requestScope.goods.goodsId}">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${requestScope.user.username}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-2 control-label">收货电话</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="phone" name="phone"
                                   value="${requestScope.user.phone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">收货地址</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="address" name="address"
                                   value="${requestScope.user.address}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="number" class="col-sm-2 control-label">购买数量</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="number" name="number"
                                   value="${requestScope.goods.number}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="totalPrice" class="col-sm-2 control-label">总价</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="totalPrice" name="totalPrice"
                                   value="${requestScope.goods.price * requestScope.goods.number}" readonly>
                        </div>
                    </div>
                    <input type="hidden" name="goodsname" value="${requestScope.goods.goodsName}">
                    <div class="btn-group btn-group-justified" role="group">
                        <div class="btn-group" role="group">
                            <input type="button" class="btn btn-default" id="cancel" value="取消订单">
                        </div>
                        <div class="btn-group" role="group">
                            <input type="submit" class="btn btn-default" id="confirm" value="确认购买">
                        </div>
                    </div>
                </form>
            </div>

        </div>
        <div class="col-md-2"></div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
