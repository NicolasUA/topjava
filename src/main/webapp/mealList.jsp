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
                        <a style="width: 48%" href="meal?action=edit&id=${loopCounter.count - 1}" class="pure-button">
                            Edit
                        </a>
                        <a style="width: 48%" href="meal?action=delete&id=${loopCounter.count - 1}" class="pure-button"
                                onclick="return confirm('Are you sure you want to delete this meal?')">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form style="width: 1000px" method="get" action="meal" class="pure-form">
        <fieldset>
            <%
                String editdata = pageContext.findAttribute("editMeal") == null ? "" :
                        ((UserMeal)pageContext.findAttribute("editMeal"))
                                .getDateTime().format(MealServlet.DATEFORMAT);
            %>
            <input style="width: 25%" type="text" placeholder="DateTime" name="dateTime" value="<%=editdata%>">
            <input style="width: 40%" type="text" placeholder="Description" name="description" value="${editMeal.getDescription()}">
            <input style="width: 10%" type="number" placeholder="Calories" name="calories" value="${editMeal.getCalories()}">
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="action" value="add">
            <button style="width: 11%" type="submit" class="pure-button">Save</button>
            <a style="width: 11%" href="meal" class="pure-button">Clear</a>
        </fieldset>
    </form>
    <h2>"<a href="/topjava">Домой"</a></h2>
</body>
</html>
