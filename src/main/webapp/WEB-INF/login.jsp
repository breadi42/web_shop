<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/28
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>并 夕 夕 - 欢迎登录</title>
    <jsp:include page="../static/header.jsp"/>

    <style type="text/css">
        #login-box {
            background: #556a89;
            border-radius: 5px;
            width: 400px;
            height: 400px;
            position: absolute;
            left: 0;
            top: 80px;
        }

        #login-form {
            width: 300px;
            position: relative;
            left: 12%;
            top: 20%;
        }

        .input-group {
            margin-bottom: 20px;
        }

        #captcha-addon {
            padding: 0;
        }

        #login-btn {
            margin-top: 15px;
        }

        #signup-box {
            text-align: right;
        }

        #signup-a {
            color: #eeeeee;
            text-decoration: none;
        }

        #signup-a:hover {
            color: #556a89;
            text-decoration: none;
            background: #eeeeee;
            border-radius: 3px;
        }

        .captcha {
            background-color: #eeeeee;
            color: brown;
            font-size: 20px;
            border: 0;
            padding: 3px;
            letter-spacing: 3px;
            font-weight: bolder;
            width: 100px;
            height: 20px;
        }

        #box-title {
            text-align: center;
            color: #eeeeee;
            position: relative;
            top: 25px;
            height: 0;
        }

        h3 {
            margin: 0;
        }
    </style>

    <script>
        let captcha

        function message() {
            let href = window.location.href
            href = href.split('/')
            if (href[4] === 'front_cart.go') {
                toastr.info('登录后更多精彩!')
            }
        }

        // 生成验证码
        function createCaptcha() {
            // 清空验证码输入框
            $('#input-captcha').val('')
            captcha = ''
            // 验证码的长度
            let captchaLength = 5
            let checkCaptcha = document.getElementById("checkCaptcha")
            // 验证码可以是数字和大小写字母
            let captchaChars = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
            for (let i = 0; i < captchaLength; i++) {
                // 随机生成
                let charNum = Math.floor(Math.random() * 52)
                captcha += captchaChars[charNum]
            }
            // 在页面上显示验证码
            if (checkCaptcha) {
                checkCaptcha.className = "captcha"
                checkCaptcha.innerHTML = captcha
            }
        }

        // 登录验证
        $(function () {
            // 用户登录
            $('#user-login').click(function () {
                if ($('#username').val() === '') {
                    toastr.warning('用户名不能为空!')
                    createCaptcha()
                } else if ($('#password').val() === '') {
                    toastr.warning('密码不能为空!')
                    createCaptcha()
                } else {
                    if ($('#input-captcha').val().toUpperCase() === captcha.toUpperCase()) {
                        $.post('${pageContext.request.contextPath}/login.user', $('#login-form').serialize(),
                            function (msg, status) {
                                if (status === 'success') {
                                    if (msg === 200) {
                                        window.location.href = '${pageContext.request.contextPath}/front_welcome.go'
                                    } else {
                                        toastr.error('登录失败!请检查用户名和密码!')
                                        createCaptcha()
                                    }
                                } else {
                                    toastr.error('请求服务器失败!')
                                    createCaptcha()
                                }
                            })
                    } else if ($('#input-captcha').val() === '') {
                        toastr.warning('请输入验证码!')
                        createCaptcha()
                    } else {
                        toastr.error('验证码错误!')
                        createCaptcha()
                    }
                }
            })

            // 管理员登录
            $('#manage-login').click(function () {
                if ($('#username').val() === '') {
                    $('#input-status').html('用户名不能为空!')
                    createCaptcha()
                } else if ($('#password').val() === '') {
                    $('#input-status').html('密码不能为空!')
                    createCaptcha()
                } else {
                    if ($('#input-captcha').val().toUpperCase() === captcha.toUpperCase()) {
                        $.post('${pageContext.request.contextPath}/login.manage', $('#login-form').serialize(),
                            function (msg, status) {
                                if (status === 'success') {
                                    if (msg === 200) {
                                        window.location.href = '${pageContext.request.contextPath}/manage_welcome.go'
                                    } else {
                                        alert('登录失败!请检查用户名和密码!')
                                        createCaptcha()
                                    }
                                } else {
                                    alert('请求服务器失败!')
                                    createCaptcha()
                                }
                            })
                    } else if ($('#input-captcha').val() === '') {
                        $('#input-status').html('请输入验证码!')
                        createCaptcha()
                    } else {
                        $('#input-status').html('验证码错误!')
                        createCaptcha()
                    }
                }
            })
        })
    </script>

</head>
<body onload="createCaptcha(); message()">
<div class="container">
    <div class="row clearfix" id="login-bg">
        <div class="col-md-4 column"></div>
        <div class="col-md-4 column">
            <div id="login-box">
                <div id="box-title">
                    <h3>用&nbsp户&nbsp登&nbsp录</h3>
                </div>
                <form id="login-form">
                    <div class="input-group">
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    </span>
                        <input type="text" class="form-control" placeholder="用户名" name="username" id="username">
                    </div>
                    <div class="input-group">
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                    </span>
                        <input type="password" class="form-control" placeholder="密码" name="password"
                               id="password">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon" id="captcha-addon">
                            <span class="captcha" id="checkCaptcha" onclick="createCaptcha()"></span>
                        </span>
                        <input type="text" placeholder="请输入验证码" name="captcha" class="form-control" id="input-captcha">
                    </div>
                    <div class="input-group" id="login-btn">
                        <div class="btn-group btn-group-justified" role="group">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default" id="user-login">用户登录</button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default" id="manage-login">管理员登录</button>
                            </div>
                        </div>
                    </div>
                    <div id="signup-box">
                        <a href="${pageContext.request.contextPath}/signup.go" id="signup-a">还没有账号？马上注册</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-md-4 column"></div>
    </div>
</div>

<jsp:include page="../static/footer.jsp"/>
</body>
</html>
