<%-- 
    Document   : NewBook
    Created on : Mar 3, 2019, 2:59:16 AM
    Author     : schueler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
    
  </head>
  <body>
    <div class="login-page">
      <div class="form">
          <h1>New User</h1>
        <form class="login-form" method="post" action="<%= request.getContextPath() %>/NewUser">
            <input type="text" name="username" placeholder="username"/>
            <input type="text" name="password" placeholder="password"/>
            <input id="formButton" type="submit" name="insert" value="Insert"/>
            <input id="formButton" formaction="Login" formmethod="get" type="submit" name="back" value="back"/>

            <p>${sessionMessage}</p>
        </form>
       </div>       
    </div>
  </body>
</html>


