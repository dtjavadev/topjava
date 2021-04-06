package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoInMem;
import ru.javawebinar.topjava.dao.MealDaoInMemImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String ADD_OR_EDIT = "/meal.jsp";
    private static final String LIST_OF_MEALS = "/meals.jsp";
    private final MealDaoInMem dao;
    private final int caloriesPerDay = 2000;

    public MealServlet() {
        super();
        dao = new MealDaoInMemImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            String forward;
            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("delete")) {
                long mealId = Long.parseLong(request.getParameter("mealId"));
                dao.delete(mealId);
                forward = LIST_OF_MEALS;
                request.setAttribute("meals", MealsUtil.createToList(dao.getList(), caloriesPerDay));
            } else if (action.equalsIgnoreCase("edit")) {
                forward = ADD_OR_EDIT;
                long mealId = Long.parseLong(request.getParameter("mealId"));
                Meal meal = dao.getById(mealId);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("showall")) {
                forward = LIST_OF_MEALS;
                request.setAttribute("meals", MealsUtil.createToList(dao.getList(), caloriesPerDay));
            } else {
                forward = ADD_OR_EDIT;
            }

            request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            request.setCharacterEncoding("UTF-8");
            Meal meal = new Meal();
            LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            meal.setDateTime(localDateTime);
            meal.setDescription(request.getParameter("description"));
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));

            String id = request.getParameter("mealId");
            if (id == null || id.isEmpty()) {
                dao.add(meal);
            } else {
                meal.setId(Long.parseLong(id));
                dao.edit(Long.parseLong(id), meal);
            }
            RequestDispatcher view = request.getRequestDispatcher(LIST_OF_MEALS);
            request.setAttribute("meals", MealsUtil.createToList(dao.getList(), caloriesPerDay));
            view.forward(request, response);
        }
    }
}
