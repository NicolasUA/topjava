package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.Filter;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final LoggerWrapper LOG = LoggerWrapper.get(getClass());

    @Autowired
    private UserMealServiceImpl service;

    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll for " + userId);
        return service.getAll(userId);
    }

    public List<UserMeal> getFiltered(int userId, Filter filter) {
        LOG.info("getFiltered for " + userId);
        return service.getFiltered(userId, filter);
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public UserMeal create(UserMeal userMeal) {
        userMeal.setId(null);
        LOG.info("create " + userMeal);
        return service.save(userMeal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(UserMeal userMeal, int id) {
        userMeal.setId(id);
        LOG.info("update " + userMeal);
        service.update(userMeal);
    }
}
