package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    @Autowired
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal create(Meal meal, Integer userId) {
        log.info("create {}", meal);
        return mealService.create(meal, userId);
    }

    public Meal update(Meal meal, Integer userId) {
        log.info("update {}", meal);
        return mealService.update(meal, userId);
    }

    public void delete(int id, Integer userId) {
        log.info("delete {}", id);
        mealService.delete(id, userId);
    }

    public Meal get(int id, Integer userId) {
        log.info("get {}", id);
        return mealService.get(id, userId);
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return mealService.getAll();
    }
}