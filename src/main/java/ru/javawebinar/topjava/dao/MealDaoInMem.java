package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMem implements MealDao {
    private final Map<Integer, Meal> dbInMem = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(0);

    public MealDaoInMem() {
        super();
        MealsUtil.meals.forEach(this::save);
    }


    @Override
    public void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
            dbInMem.put(meal.getId(), meal);
        } else {
            dbInMem.replace(meal.getId(), meal);
        }
    }

    @Override
    public Collection<Meal> getList() {
        return dbInMem.values();
    }

    @Override
    public Meal getById(Integer id) {
        return dbInMem.get(id);
    }

    @Override
    public void delete(Integer id) {
        dbInMem.remove(id);
    }
}
