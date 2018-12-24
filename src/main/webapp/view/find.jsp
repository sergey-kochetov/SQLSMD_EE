<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <table border="1">
            <c:forEach items="${tableData}" var="row">
                <tr>
                    <c:forEach items="${row}" var="element">
                        <td>
                            ${element}<br>
                        </td>
                    </c:forEach>
                    <td><a href="edit?row=${row}">edit</a><br></td>
                </tr>
            </c:forEach>
        </table>
        <a href="menu">menu</a><br>
    </body>
</html>