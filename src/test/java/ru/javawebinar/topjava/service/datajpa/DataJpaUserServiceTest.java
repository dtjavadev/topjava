package ru.javawebinar.topjava.service.datajpa;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.Profiles;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getUserWithMeals() {
        User actualUser = service.getUserWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(actualUser, admin);
        MEAL_MATCHER.assertMatch(actualUser.getMeals(), adminMeal2, adminMeal1);
    }

    @Test
    public void gettUserWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getUserWithMeals(NOT_FOUND));
    }
}
