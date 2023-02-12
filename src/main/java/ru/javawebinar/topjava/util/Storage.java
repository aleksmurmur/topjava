package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.CreateMealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface Storage {

    void save(CreateMealTo mealTo);

    void update(Integer id, Meal meal);

    void delete(Integer id);

    List<Meal> getAll();

    Meal get(Integer id);
}
