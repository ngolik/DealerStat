<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@ page session="false" %>
<html>
<head>
    <title>Receptions Page</title>

</head>
<body>
<br/>
<h1>Reception List</h1>
<c:if test="${!empty listComments}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Comment message<br>message<br>message</th>
            <th width="120">Comment message<br>rate<br>rate</th>
            <th width="120">IsApproved</th>
            <th width="120">Comment date</th>
            <th width="60">Edit</th>
                <%--<th width="60">Delete</th>--%>
        </tr>
        <c:forEach items="${listComments}" var="comment">
            <tr>
                <td>${comment.id}</td>
<%--                <td><a href="/doctor/receptiondata/${comment.id}" target="_blank">${reception.patient.lastName}--%>
                <td>${comment.message}<br>${comment.message}<br>${comment.message}</td>
                <td>${comment.rate}<br>${comment.rate}<br>${comment.rate}</td>
                <td>${comment.approved}</td>
                <td>${comment.createdAt}</td>

                    <%--<td><a href="<c:url value='/doctor/all-patient-receptions/remove/${reception.id}'/>">Delete</a></td>--%>
            </tr>
        </c:forEach>
    </table>
</c:if>
<h1>Add a Reception</h1>
<c:url var="addAction" value="/doctor/all-patient-receptions/add"/>
<form:form action="${addAction}" modelAttribute="reception">
    <table>
        <c:if test="${!empty reception.preliminaryDiagnosis}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="patient.id">
                    <spring:message text="Patient"/>
                </form:label>
            </td>
            <td>
                <form:select path="patient.id">
                    <c:forEach items="${listPatients}" var="patient">
                        <form:option value="${patient.id}">
                            ${patient.lastName}&nbsp;${patient.firstName}&nbsp;${patient.surName}
                        </form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="user.id">
                    <spring:message text="Doctor"/>
                </form:label>
            </td>
            <td>
                <form:select path="user.id">
                    <c:forEach items="${listUsersByRoleDoctor}" var="user">
                        <form:option value="${user.id}">
                            ${user.lastName}&nbsp;${user.firstName}&nbsp;${user.surName}
                        </form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="preliminaryDiagnosis">
                    <spring:message text="PreliminaryDiagnosis"/>
                </form:label>
            </td>
            <td>
                <form:input path="preliminaryDiagnosis" required="required"/>
            </td>
        </tr>
        <tr hidden="hidden">
            <td>
                <form:label path="discharge">
                    <spring:message text="Patient discharge"/>
                </form:label>
            </td>
            <td>
                <form:select path="discharge">
                    <form:option value="true">Discharge</form:option>
                    <form:option value="false">Not discharge</form:option>
                </form:select>
            </td>
        </tr>
        <tr hidden="hidden">
            <td>
                <form:label path="finalDiagnosis">
                    <spring:message text="FinalDiagnosis"/>
                </form:label>
            </td>
            <td>
                <form:input path="finalDiagnosis"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty reception.preliminaryDiagnosis}">
                    <input type="submit"
                           value="<spring:message text="Edit Reception"/>"/>
                </c:if>
                <c:if test="${empty reception.preliminaryDiagnosis}">
                    <input type="submit"
                           value="<spring:message text="Add Reception"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>