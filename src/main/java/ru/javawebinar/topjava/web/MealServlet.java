package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMem;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final int CALORIES_PER_DAY = 2000;
    private final MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDaoInMem();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "showall" : action) {
            case "delete":
                Integer mealId = getId(request);
                log.info("Delete {}", mealId);
                dao.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "add":
            case "edit":
                Meal meal = action.equals("add") ? new Meal() : dao.getById(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "showall":
            default:
                log.info("showall");
                request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        meal.setDateTime(localDateTime);
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("mealId");
        if (id == null) {
            meal.setId(null);
        } else {
            meal.setId(getId(request));
        }
        log.info(meal.getId() == null ? "Add {}" : "Edit {}", meal);
        dao.save(meal);

        response.sendRedirect("meals");
    }

    public static Integer getId(HttpServletRequest req) {
        return Integer.valueOf(req.getParameter("mealId"));
    }
}
