<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 10.02.2023
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="colors.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<table >
    <thead>
    <tr>
        <th>Id</th>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
<c:forEach items="${meals}" var="meal">
    <tr >
            <c:if test="${meal.excess}"> <tr style="color: red;"> </c:if>
<%--    <td><fmt:formatDate value="${meal.getDateTime()}" type="BOTH"></fmt:formatDate> </td>--%>
    <td><c:out value="${meal.id}" /></td>
        <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm"/></td>

        <td><c:out value="${meal.description}" /></td>
        <td><c:out value="${meal.calories}" /></td>
    <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Редактировать</a></td>
    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>

    <c:if test="${meal.excess}"> </tr> </c:if>
    </tr>
</c:forEach>
</table>

<p><a href="meals?action=insert">Добавить еду</a></p>
</body>
</html>
