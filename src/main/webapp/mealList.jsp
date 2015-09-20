<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                    <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <label>Filter</label>
    <form method="get" action="meals">
        <c:set var="filter" value="${filter}"/>
        <jsp:useBean id="filter" scope="page" type="ru.javawebinar.topjava.Filter"/>
        <table>
            <tr>
                <td>
                    <Label>User:</Label>
                    <select size="1" name="userId">
                        <c:forEach items="${userList}" var="user">
                            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                            <option value="${user.id}" ${userId == user.id ? 'selected' : ''}>${user.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    <Label>From Date</Label>
                    <input type="date" value="${filter.fromDate}" placeholder="Start Date" name="fromDate"></dd>
                </td>
                <td>
                    <Label>To Date</Label>
                    <input type="date" value="${filter.toDate}" placeholder="End Date" name="toDate"></dd>
                </td>
            </tr>
            <tr>
                <td>
                    <Label>From Time</Label>
                    <input type="time" value="${filter.fromTime}" placeholder="Start Time" name="fromTime"></dd>
                </td>
                <td>
                    <Label>To Time</Label>
                    <input type="time" value="${filter.toTime}" placeholder="End Time" name="toTime"></dd>
                </td>
            </tr>
        </table>
        <button type="submit" name="action" value="filter">Apply</button>
    </form>
</section>
</body>
</html>