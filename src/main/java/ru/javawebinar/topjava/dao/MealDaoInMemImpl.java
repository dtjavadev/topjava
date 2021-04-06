package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsDbInMem;

import java.util.List;

public class MealDaoInMemImpl implements MealDaoInMem {
    private final List<Meal> dbInMem;

    public MealDaoInMemImpl() {
        super();
        dbInMem = MealsDbInMem.createDb();
    }


    @Override
    public void add(Meal meal) {
        long id;
        if (dbInMem.size() == 0) {
            id = 0;
        } else {
            id = dbInMem.get(dbInMem.size() - 1).getId() + 1;
        }
        meal.setId(id);
        dbInMem.add(meal);
    }

    @Override
    public List<Meal> getList() {
        return dbInMem;
    }

    @Override
    public Meal getById(long id) {
        return dbInMem.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void edit(long id, Meal meal) {
        for (Meal m : dbInMem) {
            if (m.getId() == id) {
                dbInMem.set(dbInMem.indexOf(m), meal);
            }
        }
    }

    @Override
    public void delete(long id) {
        dbInMem.removeIf(meal -> meal.getId() == id);
    }
}
