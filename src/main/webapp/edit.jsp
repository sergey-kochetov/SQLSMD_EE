<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
         <form action="edit" method="post">
              <table>
                  <c:forEach items="${row}" var="element">
                      <tr>
                         <td>change ${element}</td>
                         <td><input type="text" name="element"/></td>
                      </tr>
                  </c:forEach>
                  <tr>
                  <td><input type="submit" value="edit"/></td>
                </tr>
              </table>
            </form>
        <a href="menu">menu</a><br>
    </body>
</html>