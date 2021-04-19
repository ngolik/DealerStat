<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Nikitos
  Date: 19.04.2021
  Time: 00:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>

<div>
    <form method="post">
        <input type="text" name="message" placeholder="Enter message">
        <input type="number" name="rate" placeholder="Enter rate">
        <form:select path="approved">
            <form:option value="true">Execute</form:option>
            <form:option value="false">Not execute</form:option>
        </form:select>
        <input type="datetime-local" name="createdAt" placeholder="Enter date">
        <button type="submit">Add</button>
    </form>
</div>

<div> List of comments</div>
<c:forEach var="comment" items="${comments}">
    <tr>
        <td>${comment.id}</td>
        <td>${comment.message}</td>
        <td>${comment.rate}</td>
        <td>${comment.createdAt}</td>
    </tr>
</c:forEach>
</body>
</html>
