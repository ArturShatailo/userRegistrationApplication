
<%--
  Created by IntelliJ IDEA.
  User: Arthu
  Date: 8/1/2022
  Time: 5:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="<c:url value="/resources/style/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
    <script src="<c:url value="/resources/script/script-cookies.js" />"></script>

    <title>Home</title>
</head>
<body>
<header>
    <section class="header-top-section">
        <h1 class="home-header-title">HOME PAGE</h1>
    </section>
    <section class="header-bottom-section">
        <h3 class="home-header-description">PLEASE CHOOSE THE ACTION TO FOLLOW FURTHER</h3>
    </section>

</header>
<main>
    <section class="main-first-section">
        <a class="button" href="registration.jsp">Sigh up</a>
        <a class="button" href="login.jsp">Sigh in</a>
    </section>
</main>
</body>

</html>
