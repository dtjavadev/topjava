package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, Integer userId) {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, Integer userId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalDate endDate) {
        return repository.getFiltered(userId, startDate, endDate);
    }

}