<%@ page import="ru.javawebinar.topjava.model.UserMealWithExceed" %>
<%@ page import="ru.javawebinar.topjava.model.UserMeal" %>
<%@ page import="ru.javawebinar.topjava.web.MealServlet" %>
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
            <c:forEach items="${mealList}" var="meal" varStatus="loopCounter">
                <tr style="color: ${meal.isExceed() ? "red" : "green"}">
                    <td align="center">
                        <%=((UserMealWithExceed)pageContext.findAttribute("meal"))
                            .getDateTime().format(MealServlet.DATEFORMAT)
                        %>
                    </td>
                    <td>${meal.getDescription()}</td>
                    <td align="center">${meal.getCalories()}</td>
                    <td align="center">
                        <form style="margin: 0" class="pure-form" method="post" action="meal">
                            <input type="hidden" name="id" value="${loopCounter.count - 1}">
                            <button style="width: 45%;" class="pure-button" type="submit" name="action"
                                    value="edit">Edit</button>
                            <button style="width: 45%;" class="pure-button" type="submit" name="action" value="delete"
                                    onclick="return confirm('Are you sure you want to delete this meal?')">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form style="width: 1000px" method="post" action="meal" class="pure-form">
        <fieldset>
            <input style="width: 25%; text-align: center" type="text" placeholder="DateTime" name="dateTime"
                   value="<%=pageContext.findAttribute("editMeal") == null ? "" :
                        ((UserMeal)pageContext.findAttribute("editMeal")).getDateTime().format(MealServlet.DATEFORMAT)%>">
            <input style="width: 40%" type="text" placeholder="Description" name="description"
                   value="${editMeal.getDescription()}">
            <input style="width: 10%; text-align: center" type="number" placeholder="Calories" name="calories"
                   value="${editMeal.getCalories()}">
            <input type="hidden" name="id" value="${id}">
            <button style="width: 11%" type="submit" class="pure-button" name="action" value="add">Save</button>
            <button style="width: 11%" type="submit" class="pure-button">Clear</button>
        </fieldset>
    </form>
    <h2>"<a href="/topjava">Домой"</a></h2>
</body>
</html>
