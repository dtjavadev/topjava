package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, ConcurrentHashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        ConcurrentHashMap<Integer, Meal> map = repository.computeIfAbsent(userId, id -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            map.put(meal.getId(), meal);
            repository.put(userId, map);
            return meal;
        }

        // handle case: update, but not present in storage
        return map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        ConcurrentHashMap<Integer, Meal> meals = repository.get(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        ConcurrentHashMap<Integer, Meal> meals = repository.get(userId);
        return meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        ConcurrentHashMap<Integer, Meal> meals = repository.computeIfAbsent(userId, id -> new ConcurrentHashMap<>());
        return MealsUtil.getFilteredByPredicate(meals.values(), meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), LocalDate.MIN, LocalDate.MAX));
    }

    @Override
    public Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalDate endDate) {
        ConcurrentHashMap<Integer, Meal> meals = repository.get(userId);
        return MealsUtil.getFilteredByPredicate(meals.values(), meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), startDate, endDate));
    }
}

