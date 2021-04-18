<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@ page session="false" %>
<html>
<head>
    <title>Games Page</title>
</head>
<body>
<br/>
<h1>Game List</h1>
<c:if test="${!empty listGames}">

</c:if>

<h1>Add a Game</h1>

<c:url var="addAction" value="/games/add"/>
<form:form action="${addAction}" modelAttribute="game">
    Name: <label>
    <input type = "text" name = "first_name">
    </label>
    <input type = "submit" value = "Submit" />
</form:form>

</body>
</html>
