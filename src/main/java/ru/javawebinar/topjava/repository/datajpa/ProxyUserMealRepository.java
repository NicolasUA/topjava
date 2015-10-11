package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Nicolas on 10.10.2015.
 */
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {
    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    UserMeal findByIdAndUserId(int id, int userId);

    List<UserMeal> findByUserIdOrderByDateTimeDesc(int userId);

    List<UserMeal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
