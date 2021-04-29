package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    @Autowired
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        int userId = SecurityUtil.authUserId();
        return mealService.create(meal, userId);
    }

    public Meal update(Meal meal, Integer id) {
        log.info("update {}", meal);
        int userId = SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(meal, id);
        return mealService.update(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
        mealService.delete(id, userId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
        return mealService.get(id, userId);
    }

    public Collection<MealTo> getAll() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        Collection<Meal> allMeal = mealService.getAll(userId);
        return MealsUtil.getTos(allMeal, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> filter(@Nullable LocalDate startDate, @Nullable LocalTime startTime, @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        Collection<Meal> filteredByDate = mealService.getFiltered(userId, startDate, endDate);

        return MealsUtil.filterByPredicate(filteredByDate, MealsUtil.DEFAULT_CALORIES_PER_DAY,
                meal -> DateTimeUtil.isBetweenTime(meal.getDateTime().toLocalTime(), startTime, endTime));
    }
}