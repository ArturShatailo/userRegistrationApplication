<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 7/31/2022
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/style/style-c-m.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/style/style-register.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
    <script src="<c:url value="/resources/script/script-cookies.js" />"></script>

    <title>Registration page</title>
</head>
<body>

<header>

    <div class="cookies">

    </div>

    <h1>REGISTER</h1>
</header>
<main>
    <form class="form register-form" action="registerUser" method="post">

        <div class="form-group-wrapper">
            <div class="form-group field">
                <input type="text" class="form-field" placeholder="Name" name="name" id='name' required />
                <label for="name" class="form-label">Name</label>
            </div>
            <div class="form-group field">
                <input type="text" class="form-field" placeholder="Surname" name="surname" id='surname' required />
                <label for="surname" class="form-label">Surname</label>
            </div>
        </div>
        <div class="form-group-wrapper">
            <div class="form-group field">
                <input type="text" class="form-field" placeholder="Country" name="country" id='country' required />
                <label for="country" class="form-label">Country</label>
            </div>
            <div class="form-group field">
                <input type="email" class="form-field" placeholder="Email" name="email" id='email' required />
                <label for="email" class="form-label">Email</label>
            </div>
        </div>
        <div class="form-group-wrapper">
            <div class="form-group field">
                <input type="password" class="form-field" placeholder="Password" name="password" id='password' required />
                <label for="password" class="form-label">Password</label>
            </div>
            <div class="form-group field">
                <input type="password" class="form-field" placeholder="Password" name="passwordRepeat" id='passwordRepeat' required />
                <label for="passwordRepeat" class="form-label">Repeat password</label>
            </div>
        </div>
        <label class="form-button-label">
            <input id="sing-up-button" class="button" type="submit" name="signin" value="Register"/>
        </label>
    </form>
    <section class="login-additional-links">
        <h5><a href="login.jsp">Login instead</a></h5>
        <h5><a href="#">Forgot password</a></h5>
    </section>
</main>

</body>
</html>
