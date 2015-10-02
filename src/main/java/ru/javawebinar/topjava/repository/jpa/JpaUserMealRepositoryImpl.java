package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            User ref = em.getReference(User.class, userId);
            userMeal.setUser(ref);
            em.persist(userMeal);
            return userMeal;
        }
        if (em.createNamedQuery(UserMeal.MERGE)
                .setParameter("id", userMeal.getId())
                .setParameter("description", userMeal.getDescription())
                .setParameter("calories", userMeal.getCalories())
                .setParameter("date_time", userMeal.getDateTime())
                .setParameter("user", em.getReference(User.class, userId))
                .executeUpdate() == 0) {
            return null;
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("user", em.getReference(User.class, userId))
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> meals =  em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user", em.getReference(User.class, userId))
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter("user", em.getReference(User.class, userId))
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class)
                .setParameter("user", em.getReference(User.class, userId))
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
    }
}