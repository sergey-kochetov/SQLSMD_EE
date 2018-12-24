<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
    Existing commands:<br>
        connect|database|userName|password<br>
            for connect to database<br>
        help<br>
            this page<br>
        exit<br>
            for exit program<br>
        tables<br>
            for get tables database<br>
        clear|tableName<br>
            database cleaning<br>
        drop|tableName<br>
            drop database<br>
        insert|tableName|row1|param1|...|rowN|paramN<br>
            create data for database<br>
        find|tableName<br>
            for see table tableName<br>
        update|tableName|column1|value1|column2|value2|...|columnN|valueN<br>
            update data into a table<br>
        create|tableName|column1|type1|...|columnN|typeN<br>
            create new table<br>
        delete|tableName|column|value<br>
            deleting data in the table with parameters<br>
        <%@include file="footer.jsp" %>
    </body>
</html>