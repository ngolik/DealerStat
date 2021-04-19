<%--
  Created by IntelliJ IDEA.
  User: Nikitos
  Date: 09.04.2021
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>.
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${empty game.name}">
        <title>Add</title>
    </c:if>
    <c:if test="${!empty game.name}">
        <title>Edit</title>
    </c:if>
</head>
<body>
<c:if test="${empty game.name}">
    <c:url value="/games/add" var="var"/>
</c:if>
<c:if test="${!empty game.name}">
    <c:url value="/games/edit" var="var"/>
</c:if>
<form action="${var}" method="POST">
    <c:if test="${!empty game.name}">
        <input type="hidden" name="id" value="${game.id}">
        <%--        <input type="text" name="title" id="title" value="${film.title}">--%>
        <%--        <input type="text" name="year" id="year" value="${film.year}">--%>
        <%--        <input type="text" name="genre" id="genre" value="${film.genre}">--%>
    </c:if>
    <label for="name">Name</label>
    <input type="text" name="title" id="name">
    <c:if test="${empty game.name}">
        <input type="submit" value="Add new Game">
    </c:if>
    <c:if test="${!empty game.name}">
        <input type="submit" value="Edit game">
    </c:if>
</form>
</body>
</html>

