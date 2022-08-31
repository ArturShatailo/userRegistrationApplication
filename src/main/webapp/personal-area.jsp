
<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 8/2/2022
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="<c:url value="/resources/style/style-c-m.css" />" rel="stylesheet">
  <link href="<c:url value="/resources/style/style-pa.css" />" rel="stylesheet">
  <link href="<c:url value="/resources/style/style-menu.css" />" rel="stylesheet">
  <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
  <script src="<c:url value="/resources/script/script-cookies.js" />"></script>
  <script src="<c:url value="/resources/script/script-pa.js" />"></script>
  <title>Personal area</title>
</head>
<body>

<header>
  <div class="cookies"></div>

  <nav class="menu">
    <div class="menu-list">
      <a class="menu-button page-menu" href="funds.jsp">Funds</a>
      <a class="menu-button page-menu active-menu-link" href="personal-area.jsp">Personal area</a>
    </div>
  </nav>
  
  <h1>PERSONAL AREA</h1>
</header>

<section class="edit-user-pop-up">
  <form class="form register-form" action="editUserServlet" method="post">
    <div class="close-pop-up">&#10006</div>
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="Name" name="name" id='name' required />
        <label for="name" class="form-label">Name</label>
      </div>
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="Surname" name="surname" id='surname' required />
        <label for="surname" class="form-label">Surname</label>
      </div>
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="Country" name="country" id='country' required />
        <label for="country" class="form-label">Country</label>
      </div>
    <label class="form-button-label">
      <input id="editUser" class="button" type="submit" name="editUser" value="Save"/>
    </label>
  </form>
</section>

<main>
  <div class="personal-area-info">
    <!--<p>Name: <span><%//= request.getAttribute("name")%></span></p>
    <p>Surname: <span><%//= request.getAttribute("surname")%></span></p>
    <p>Email: <span><%//= request.getAttribute("email")%></span></p>
    <p>Country: <span><%//= request.getAttribute("country")%></span></p>-->
    <div><p class="name-icon pa-info-icon"></p><p>Full name </p><p><span id="user-name"></span><span> </span><span id="user-surname"></span></p></div>
    <div><p class="country-icon pa-info-icon"></p><p>Country </p><p><span id="user-country"></span></p></div>
    <div><p class="email-icon pa-info-icon"></p><p>Email </p><p><span id="user-email"></span></p></div>
  </div>
</main>

<section class="interface-wrapper">

  <form class="interface-item" action="logoutServlet" method="post">
    <input id="log-out-button" class="button" type="submit" name="log-out" value="Log out"/>
  </form>
  <button class="button interface-item" id="change-info">
    Edit info
  </button>
</section>

</body>
</html>
