package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.CreateMealTo;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class HashmapStorage implements Storage{

    private static ConcurrentHashMap<Integer, Meal> map;
    private AtomicInteger atomId = new AtomicInteger(0);

    private static ConcurrentHashMap<Integer, Meal> getMap() {
        if (map != null) return map;
        else return new ConcurrentHashMap<>();
    }

    public HashmapStorage() {
        map = getMap();
    }

    @Override
    public void save(CreateMealTo mealTo) {
        Meal meal = new Meal(atomId.getAndIncrement(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
        map.put(meal.getId(), meal);

    }

    @Override
    public void update(Integer id, Meal meal) {
        map.put(id, meal);
    }

    @Override
    public void delete(Integer id) {
        map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Meal get(Integer id) {
        return map.get(id);
    }


}
