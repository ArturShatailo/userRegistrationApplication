<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 7/31/2022
  Time: 8:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/style/style-c-m.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/style/style-login.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
    <script src="<c:url value="/resources/script/script-cookies.js" />"></script>

    <title>Login page</title>
</head>
<body>

<header>

    <div class="cookies">

    </div>

    <h1>LOGIN</h1>
</header>

<main>
    <form class="form login-form" action="loginUser" method="post">
        <div class="form-group field">
            <input type="email" class="form-field" placeholder="Email" name="email" id='email' required />
            <label for="email" class="form-label">Email</label>
        </div>
        <div class="form-group field">
            <input type="password" class="form-field" placeholder="Password" name="password" id='password' required />
            <label for="password" class="form-label">Password</label>
        </div>
        <label class="form-button-label">
            <input class="button" type="submit" name="signup" value="Login"/>
        </label>
    </form>
    <section class="login-additional-links">
        <h5><a href="registration.jsp">Register instead</a></h5>
        <h5><a href="#">Forgot password</a></h5>
    </section>
</main>

</body>
</html>

