package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


@Service
public class MealService {

    private MealRepository repository;
    private UserRepository userRepository;

    public MealService(MealRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<MealTo> create(int userId, Meal meal) {
        meal.setUserId(userId);
        repository.save(userId, meal);
        return getAll(userId);
    }
    public List<MealTo> update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
        return getAll(userId);
    }

    public void delete(int userId, int mealId) {
         checkNotFoundWithId(repository.delete(userId, mealId), mealId);
    }


    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), getCaloriesPerDay(userId));
    }

    public Meal get(int userId, int mealId) {
        return repository.get(userId, mealId);
    }

    public List<MealTo> getWithFilter(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredTos(repository.getWithDateFilter(
                        userId,
                        startDate == null ? LocalDate.MIN : startDate,
                        endDate == null ? LocalDate.MAX : endDate),
                getCaloriesPerDay(userId),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }

    private int getCaloriesPerDay(int userId) {
        return userRepository.get(userId).getCaloriesPerDay();
    }

}