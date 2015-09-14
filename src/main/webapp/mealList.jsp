<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.model.UserMealWithExceed" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">

<html>
<head>
    <title>Meal list</title>
</head>
<body>
    <h2>Meal list</h2>
    <table width="1000" class="pure-table pure-table-bordered">
        <thead>
            <tr align="center">
                <th width="25%">Date</th>
                <th width="40%">Description</th>
                <th width="10%">Calories</th>
                <th width="25%">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${sessionScope.mealList}" var="meal" varStatus="loopCounter">
                <tr style="color: ${meal.isExceed() ? "red" : "green"}">
                    <td align="center">
                        <%=((UserMealWithExceed)pageContext.getAttribute("meal"))
                            .getDateTime().format(DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm"))
                        %>
                    </td>
                    <td>${meal.getDescription()}</td>
                    <td align="center">${meal.getCalories()}</td>
                    <td align="center">
                        <a href="meal?action=edit&id=${loopCounter.count - 1}" class="pure-button">
                            Edit
                        </a>
                        <a href="meal?action=delete&id=${loopCounter.count - 1}" class="pure-button"
                                onclick="return confirm('Are you sure you want to delete this meal?')">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form method="get" action="meal" class="pure-form">
        <fieldset>
            <input type="text" placeholder="Date" name="dateTime" value="${sessionScope.editMeal.getDateTime()}">
            <input type="text" placeholder="Description" name="description" value="${sessionScope.editMeal.getDescription()}">
            <input type="number" placeholder="Calories" name="calories" value="${sessionScope.editMeal.getCalories()}">
            <input type="hidden" name="id" value="${sessionScope.id}">
            <input type="hidden" name="action" value="add">
            <button type="submit" class="pure-button">Add</button>
            <a href="meal" class="pure-button">Clear</a>
        </fieldset>
    </form>
    <h2>"<a href="/topjava">Домой"</a></h2>
</body>
</html>
