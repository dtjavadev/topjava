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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (!meal.getUserId().equals(userId)) {
            return null;
        }

        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId)) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId)) {
            return null;
        }
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public Collection<MealTo> getFiltered(LocalDateTime start, LocalDateTime end) {
        return MealsUtil.filterByPredicate(repository.values(), MealsUtil.DEFAULT_CALORIES_PER_DAY,
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }
}

