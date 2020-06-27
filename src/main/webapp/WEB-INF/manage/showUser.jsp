<%--
  Created by IntelliJ IDEA.
  User: applepieme@yeah.net
  Date: 2020/6/26
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>用户名</th>
        <th>密码</th>
        <th>电话</th>
        <th>地址</th>
    </tr>
    <c:forEach items="${requestScope.userList}" var="user">
        <tr>
            <td>${user.userId}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.phone}</td>
            <td>${user.address}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
