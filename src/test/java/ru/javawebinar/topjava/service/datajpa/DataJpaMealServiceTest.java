package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.Profiles;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void getMealWithUser() {
        Meal actualMeal = mealService.getMealWithUser(MEAL1_ID, USER_ID);
        User actualUser = actualMeal.getUser();
        MEAL_MATCHER.assertMatch(actualMeal, meal1);
        USER_MATCHER.assertMatch(actualUser, user);
    }
}
