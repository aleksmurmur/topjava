package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.CreateMealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DbUtil;
import ru.javawebinar.topjava.util.HashmapStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MealDao {

    private static final Logger log = LoggerFactory.getLogger(MealDao.class);

    private static final int CALORIES_PER_DAY = 2000;

    private Storage storage;

    public MealDao() {
        this.storage = new HashmapStorage();
    }

    public void add(CreateMealTo mealTo) {
        storage.save(mealTo);
    }

    public void update(Integer id, Meal meal) {
        storage.update(id, meal);
    }

    public void delete(Integer id) {
        storage.delete(id);
    }

    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(storage.getAll(), CALORIES_PER_DAY);

    }

    public MealTo get(Integer id) {
        return MealsUtil.convertToDto(storage.get(id), storage.getAll(), CALORIES_PER_DAY);
    }


}
