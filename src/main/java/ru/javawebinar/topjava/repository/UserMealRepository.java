package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.Filter;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    boolean delete(int id, int userId);

    UserMeal get(int id, int userId);

    List<UserMeal> getAll(int userId);

    List<UserMeal> getFiltered(int userId, Filter filter);
}
