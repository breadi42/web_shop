<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/7/6
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>并 夕 夕 - 管理员</title>
    <jsp:include page="/static/header.jsp"/>

    <script>
        // 非负整数
        let isnum = /^\d+$/
        // 非负浮点数
        let isfloat = /^\d+(\.\d+)?$/

        // 验证商品价格和商品库存
        $(function () {
            $('#price').blur(function () {
                if ($('#price').val() === '') {
                    return
                }
                if (!isfloat.test($('#price').val())) {
                    toastr.warning('商品价格必须是非负的整数或浮点数!')
                    $('#price').val('')
                }
            })

            $('#stock').blur(function () {
                if ($('#stock').val() === '') {
                    return
                }
                if (!isnum.test($('#stock').val())) {
                    toastr.warning('商品库存必须是非负整数!')
                    $('#stock').val('')
                }
            })
        })

        // 重置表单
        function formReset() {
            document.getElementById('add-goods-form').reset()
        }

        // 提交表单
        function addGoods() {
            if ($('#goodsName').val() === '') {
                toastr.warning('请输入商品名!')
            } else if (!$('input:radio[name="type"]:checked').val()) {
                toastr.warning('请选择商品分类!')
            } else if ($('#details').val() === '') {
                toastr.warning('请输入商品详情!')
            } else if ($('#price').val() === '') {
                toastr.warning('请输入商品价格!')
            } else if ($('#stock').val() === '') {
                toastr.warning('请输入商品库存!')
            } else if (!$('#image').get(0).files[0]) {
                toastr.warning('请上传商品图片!')
            } else {
                $.ajax({
                    url : '${pageContext.request.contextPath}/addGoods.goods',
                    type : 'POST',
                    async : false,
                    cache : false,
                    data : new FormData($('#add-goods-form')[0]),
                    processData : false,
                    contentType : false,
                    success : function (msg) {
                        if (msg === 200) {
                            toastr.success('添加商品成功!')
                            formReset()
                        } else if (msg === 300) {
                            toastr.error('请上传JPG类型的图片!')
                        } else {
                            toastr.error('添加商品失败!请重试!')
                        }
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
                <li class="active">添加商品</li>
            </ol>
        </div>
        <div class="col-md-8">
            <form class="form-horizontal" enctype="multipart/form-data" method="post" id="add-goods-form">
                <div class="form-group">
                    <label for="goodsName" class="col-sm-2 control-label">商品名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="goodsName" name="goodsName"
                               placeholder="请输入商品名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="type" class="col-md-2 control-label">商品分类</label>
                    <div class="col-md-6" id="type">
                        <label class="radio-inline">
                            <input type="radio" name="type" value="电子产品"> 电子产品
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="服装"> 服装
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="食品"> 食品
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="details" class="col-sm-2 control-label">商品详情</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" id="details" rows="3" name="details"
                                  placeholder="请输入商品详情"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="col-sm-2 control-label">商品价格</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="price" name="price"
                               placeholder="请输入非负浮点数或整数">
                    </div>
                </div>
                <div class="form-group">
                    <label for="stock" class="col-sm-2 control-label">商品库存</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="stock" name="stock"
                               placeholder="请输入非负整数">
                    </div>
                </div>
                <div class="form-group">
                    <label for="image" class="col-sm-2 control-label">商品图片</label>
                    <div class="col-sm-6">
                        <input type="file" id="image" name="image">
                        <p class="help-block">请上传JPG图片文件</p>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-default" onclick="addGoods()">确认添加</button>
                        <button type="button" class="btn btn-default" onclick="formReset()">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/static/footer.jsp"/>
</body>
</html>
