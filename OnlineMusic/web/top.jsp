<%--
  Created by IntelliJ IDEA.
  User: 戴尔
  Date: 2020/12/26
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@ page import="com.jsu.Bean.User"%>
<html>
<head>
    <title>网上音乐</title>
    <link rel="stylesheet" href="css/top.css" type="text/css">
</head>
<body>
<div class="top">
    <a href="index.jsp"><img class="icon" src="/images/icon.png"></a>
    <div class="welcome">网上音乐</div>
    <p>分享我的所爱</p>
    <div class="head_user">
        <%
            User user = (User) session
                    .getAttribute("SESSION_User");
            if (user == null) {
        %>
        <a href="login.jsp" target="_parent"><span class="type1">登录</span></a>
        <a href="register.jsp" target="_parent"><span class="type2">注册</span></a>
        <%
        } else {
        %>
        <a href="User.jsp" class="iconfont"><%=user.getName()%>&#xe62f;</a>
        <a href="/UserLogoutServlet">退出</a>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
