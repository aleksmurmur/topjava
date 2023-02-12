<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12.02.2023
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Создание и редактирование еды</title>
<%--    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>--%>
<%--    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>--%>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%--<script>--%>
<%--    $(function() {--%>
<%--        $('input[name=time]').datepicker();--%>
<%--    });--%>
<%--</script>--%>
<form method="post" action="meals" name="addMeal">
    Id: <input type="text" readonly="readonly" name="mealId"
value="<c:out value="${meal.id}"/> "> <br />
    Время приема пищи: <input type="datetime-local" name="time"
    value="<javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm"/>" /> <br />
    Описание: <input type="text" name="description" value="<c:out value="${meal.description}"/>"> <br />
    Калории: <input type="text" name="calories" value="<c:out value="${meal.calories}"/> "> <br />
    <input type="submit" value="Добавить" />
</form>

</body>
</html>
