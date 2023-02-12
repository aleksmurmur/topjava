package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class CreateMealTo {

    private final Integer id;


    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;


    public CreateMealTo(LocalDateTime dateTime, String description, int calories) {
        this.id = null;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

    }
    public CreateMealTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }


    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
