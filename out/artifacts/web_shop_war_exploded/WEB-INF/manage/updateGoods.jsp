<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/5
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>并 夕 夕 - 管理员</title>
    <jsp:include page="/static/header.jsp"/>

    <script>
        let isnum = /^\d+$/
        let isfloat = /^\d+(\.\d+)?$/

        $(function () {
            $('#price').blur(function () {
                if (!isfloat.test($('#price').val())) {
                    toastr.warning('商品价格必须是非负的整数或浮点数!')
                    let price = '${requestScope.goods.price}'
                    $('#price').val(price)
                }
            })

            $('#stock').blur(function () {
                if (!isnum.test($('#stock').val())) {
                    toastr.warning('商品库存必须是非负整数!')
                    let stock = '${requestScope.goods.stock}'
                    $('#stock').val(stock)
                }
            })
        })

        function updateGoods() {
            if ($('#goodsName').val() === '') {
                toastr.warning('商品名不能为空!')
                let goodsName = '${requestScope.goods.goodsName}'
                $('#goodsName').val(goodsName)
            } else if ($('#details').val() === '') {
                toastr.warning('商品详情不能为空!')
                let details = '${requestScope.goods.details}'
                $('#details').val(details)
            } else if ($('#price').val() === '') {
                toastr.warning('商品价格不能为空!')
                let price = '${requestScope.goods.price}'
                $('#price').val(price)
            } else if ($('#stock').val() === '') {
                toastr.warning('商品库存不能为空!')
                let stock = '${requestScope.goods.stock}'
                $('#stock').val(stock)
            } else {
                $.post('${pageContext.request.contextPath}/updateGoods.goods', $('#update-form').serialize(),
                    function (msg, status) {
                    if (status === 'success') {
                        if (msg === 200) {
                            toastr.success('修改商品信息成功!')
                        } else {
                            toastr.error('修改商品信息失败!')
                        }
                    } else {
                        toastr.error('请求服务器失败!')
                    }
                    })
            }
        }
    </script>
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
                <li><a href="${pageContext.request.contextPath}/listGoods.goods?page=1">商品管理</a></li>
                <li class="active">修改商品信息</li>
            </ol>
        </div>
        <div class="col-md-8">
            <div>
                <form class="form-horizontal" id="update-form">
                    <div class="form-group">
                        <label for="goodsName" class="col-sm-2 control-label">商品名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="goodsName" name="goodsName"
                                   value="${requestScope.goods.goodsName}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="type" class="col-md-2 control-label">商品分类</label>
                        <div class="col-md-6" id="type">
                            <c:choose>
                                <c:when test="${requestScope.goods.type == '电子产品'}">
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="电子产品" checked> 电子产品
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="服装"> 服装
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="食品"> 食品
                                    </label>
                                </c:when>
                                <c:when test="${requestScope.goods.type == '服装'}">
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="电子产品"> 电子产品
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="服装" checked> 服装
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="食品"> 食品
                                    </label>
                                </c:when>
                                <c:when test="${requestScope.goods.type == '食品'}">
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="电子产品"> 电子产品
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="服装"> 服装
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" value="食品" checked> 食品
                                    </label>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="details" class="col-sm-2 control-label">商品详情</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" id="details"
                                      rows="3" name="details">${requestScope.goods.details}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-2 control-label">商品价格</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="price"
                                   name="price" value="${requestScope.goods.price}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="stock" class="col-sm-2 control-label">商品库存</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="stock"
                                   name="stock" value="${requestScope.goods.stock}">
                        </div>
                    </div>
                    <input type="hidden" name="goodsId" value="${requestScope.goods.goodsId}">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="button" class="btn btn-default"
                                    onclick="updateGoods(${requestScope.goods.goodsId})">确认修改</button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
