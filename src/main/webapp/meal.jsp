<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit/Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit/Add meal</h2>

<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post">
    Date : <input
        type="datetime-local" name="date" value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description" value="${meal.description}"/> <br/>
    Calories : <input
        type="text" name="calories" value="${meal.calories}"/> <br/>
    <input type="submit" value="Save"/>
</form>
</body>
</html>