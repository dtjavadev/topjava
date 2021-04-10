<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html lang="ru">
<head>
    <title>Show All Meals</title>
    <style>
        .normal {
            color: green
        }

        .excess {
            color: red
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Edit</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=add">Add Meal</a></p>
</body>
</html>