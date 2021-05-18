package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MealTestData.MEAL_ID, MealTestData.USER_ID);
        MealTestData.assertMatch(meal, MealTestData.meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL_ID, MealTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.MEAL_ID, MealTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL_ID, MealTestData.USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.MEAL_ID, MealTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMeals = service.getBetweenInclusive(MealTestData.START_DATE, MealTestData.END_DATE, MealTestData.USER_ID);
        MealTestData.assertMatch(filteredMeals, MealTestData.getFiltered());
    }

    @Test
    public void getAll() {
        List<Meal> list = service.getAll(MealTestData.USER_ID);
        MealTestData.assertMatch(list, MealTestData.meals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, MealTestData.USER_ID);
        MealTestData.assertMatch(service.get(MealTestData.MEAL_ID, MealTestData.USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.update(service.get(MealTestData.MEAL_ID, MealTestData.USER_ID), MealTestData.ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), MealTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, MealTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () ->
                service.create(new Meal(null, LocalDateTime.parse("2020-01-31 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Ужин", 410), MealTestData.USER_ID));
    }
}