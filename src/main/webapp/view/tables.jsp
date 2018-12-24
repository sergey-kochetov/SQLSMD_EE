<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <c:forEach items="${tables}" var="table">
                            <td>
                                <a href="find?table=${table}">${table}</a><br>
                            </td>
                </c:forEach>
            </tr>
        </table>
        <a href="menu">menu</a><br>
    </body>
</html>