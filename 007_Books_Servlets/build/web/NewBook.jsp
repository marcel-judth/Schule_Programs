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
    <style media="screen">
      
    </style>
  </head>
  <body>
    <div class="login-page">
      <div class="form">
          <h1>New Book</h1>
        <form class="login-form" method="post" action="<%= request.getContextPath() %>/NewBook">
            <input type="number" name="id" placeholder="id"/>
            <input type="text" name="title" placeholder="title"/>
            <input type="text" name="author" placeholder="author"/>
            <input id="formButton" type="submit" name="" value="Insert"/>
            <p>${sessionMessage}</p>
        </form>
       </div>       
    </div>
  </body>
</html>


