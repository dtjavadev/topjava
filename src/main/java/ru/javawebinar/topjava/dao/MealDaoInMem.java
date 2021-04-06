package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDaoInMem {
    void add(Meal meal);

    List<Meal> getList();

    Meal getById(long id);

    void edit(long id, Meal meal);

    void delete(long id);
}
