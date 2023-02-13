package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private MealService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.debug("Meal {} created by user with id: {}", meal, userId);
        return service.create(userId, meal);
    }

    public List<MealTo> update(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.debug("Meal {} updated by user with id: {}", meal, userId);
        return service.update(userId, meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(userId, id);
        log.debug("Meal {} deleted", id);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.debug("Meals got by user {}", userId);
        return service.getAll(userId);
    }

    public Meal get(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.debug("Meal {} got by user {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public List<MealTo> getWithFilter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.debug("get meals with filter for user {}", userId);
        return service.getWithFilter(userId, startDate, startTime, endDate, endTime);
    }


}