package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.Filter;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    public UserMeal save(UserMeal userMeal);

    public void delete(int id, int userId) throws NotFoundException;

    public UserMeal get(int id, int userId) throws NotFoundException;

    public List<UserMeal> getAll(int userId);

    public List<UserMeal> getFiltered(int userId, Filter filter);

    public void update(UserMeal userMeal);
}
