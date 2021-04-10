package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealDao {
    void save(Meal meal);

    Collection<Meal> getList();

    Meal getById(Integer id);

    void delete(Integer id);
}
