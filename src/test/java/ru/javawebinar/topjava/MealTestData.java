package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.*;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);
    public static final int MEAL_ID = START_SEQ + 2;
    public static final Map<Integer, UserMeal> USER_MEALS = new HashMap<>(); 
    public static final Map<Integer, UserMeal> ADMIN_MEALS = new HashMap<>(); 
    
    static {
        USER_MEALS.put(MEAL_ID, new UserMeal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        USER_MEALS.put(MEAL_ID + 1, new UserMeal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        USER_MEALS.put(MEAL_ID + 2, new UserMeal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        USER_MEALS.put(MEAL_ID + 3, new UserMeal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        USER_MEALS.put(MEAL_ID + 4, new UserMeal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        USER_MEALS.put(MEAL_ID + 5, new UserMeal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

        ADMIN_MEALS.put(MEAL_ID + 6, new UserMeal(MEAL_ID + 6, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510));
        ADMIN_MEALS.put(MEAL_ID + 7, new UserMeal(MEAL_ID + 7, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин ", 510));
    }
}
