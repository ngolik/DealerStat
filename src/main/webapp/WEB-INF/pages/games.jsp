<%--
  Created by IntelliJ IDEA.
  User: Nikitos
  Date: 09.04.2021
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Games</h2>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
    </tr>
    <c:forEach var="game" items="${gamesList}">
        <tr>
            <td>${game.id}</td>
            <td>${game.name}</td>
            <td>
                <a href="/edit/${game.id}">edit</a>
                <a href="/delete/${game.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Add</h2>
<c:url value="/games/add" var="add"/>
<a href="${add}">Add new Game</a>
</body>
</html>
