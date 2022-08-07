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

    <title>Password reset page</title>
</head>
<body>

<header>

    <div class="cookies">

    </div>

    <h1>PASSWORD RESET</h1>
</header>

<main>
    <p>Please fill out email that was used during registration, and we will send you a new password.</p>
    <form class="form login-form" action="resetPassword" method="post">
        <div class="form-group field">
            <input type="email" class="form-field" placeholder="Email" name="email" id='email' required />
            <label for="email" class="form-label">Email</label>
        </div>
        <label class="form-button-label">
            <input class="button" type="submit" name="signup" value="Login"/>
        </label>
    </form>
</main>

</body>
</html>

