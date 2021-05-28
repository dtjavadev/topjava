package ru.javawebinar.topjava.service.datajpa;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.Profiles;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserWithMeals() {
        User actualUser = userService.getUserWithMeals(ADMIN_ID);
        List<Meal> actualMeals = actualUser.getMeals();
        USER_MATCHER.assertMatch(actualUser, admin);
        MEAL_MATCHER.assertMatch(actualMeals, adminMeal2, adminMeal1);
    }
}
