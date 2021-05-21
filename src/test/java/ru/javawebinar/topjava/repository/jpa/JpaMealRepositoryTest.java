package ru.javawebinar.topjava.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaMealRepositoryTest {

    @Autowired
    private MealRepository repository;

    @Test
    public void save() {
        Meal created = repository.save(getNew(), USER_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.get(newId, USER_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        repository.save(updated, USER_ID);
        MEAL_MATCHER.assertMatch(repository.get(MEAL1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertNull(repository.save(meal1, ADMIN_ID));
    }

    @Test
    public void delete() {
        assertTrue(repository.delete(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertFalse(repository.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertFalse(repository.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        Meal actual = repository.get(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
    }

    @Test
    public void getNotFound() {
        assertNull(repository.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertNull(repository.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        MEAL_MATCHER.assertMatch(repository.getAll(USER_ID), meals);
    }

    @Test
    public void getBetweenHalfOpen() {
        MEAL_MATCHER.assertMatch(repository.getBetweenHalfOpen(START, END, USER_ID), filteredMeals);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new Meal(null, meal1.getDateTime(), "duplicate", 100), USER_ID));
    }
}