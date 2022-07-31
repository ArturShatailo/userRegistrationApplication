<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 7/31/2022
  Time: 8:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>

<form style="display: flex; align-items: center; justify-content: center; flex-direction: column;" action="loginUser" method="post">
    <div style="display: flex; align-content: center; flex-direction: column;">
        <label style="width: 400px; display: flex; justify-content: space-between; margin: 10px"> Email
            <input type="email" name="email" required/>
        </label>
        <label style="width: 400px; display: flex; justify-content: space-between; margin: 10px"> Password
            <input type="password" name="password" required/>
        </label>
        <label style="width: 400px; display: flex; justify-content: center;">
            <input style="margin: 10px; width: 100px; height: 30px" type="submit" name="signup" value="Sign up"/>
        </label>
    </div>
</form>

</body>
</html>

