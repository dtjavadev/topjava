package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = 100002;
    public static final LocalDate START_DATE = LocalDate.parse("2020-01-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static final LocalDate END_DATE = LocalDate.parse("2020-02-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(100008, LocalDateTime.parse("2020-01-31 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Ужин", 410),
            new Meal(100007, LocalDateTime.parse("2020-01-31 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Обед", 500),
            new Meal(100006, LocalDateTime.parse("2020-01-31 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Завтрак", 1000),
            new Meal(100005, LocalDateTime.parse("2020-01-31 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Еда на пограничное значение", 100),
            new Meal(100004, LocalDateTime.parse("2020-01-30 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Ужин", 500),
            new Meal(100003, LocalDateTime.parse("2020-01-30 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Обед", 1000),
            new Meal(100002, LocalDateTime.parse("2020-01-30 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "Завтрак", 500)
    );

    public static List<Meal> getFiltered() {
        return userMeals.stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime().toLocalDate(), START_DATE, END_DATE))
                .collect(Collectors.toList());
    }


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("2021-01-01 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "New", 550);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeals.get(6));
        updated.setDateTime(LocalDateTime.parse("2021-01-01 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        updated.setDescription("Update");
        updated.setCalories(660);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
