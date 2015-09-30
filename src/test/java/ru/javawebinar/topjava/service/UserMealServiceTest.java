package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(MEAL_ID, USER_ID);
        MealTestData.MATCHER.assertEquals(USER_MEALS.get(MEAL_ID), userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        List<UserMeal> userMeals = USER_MEALS.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
        userMeals.remove(USER_MEALS.get(MEAL_ID));
        MealTestData.MATCHER.assertCollectionEquals(userMeals, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        LocalDate start = LocalDate.of(2015, Month.MAY, 30);
        LocalDate end = LocalDate.of(2015, Month.MAY, 31);
        List<UserMeal> userMeals = USER_MEALS.values().stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime(), LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX)))
                        .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
        MealTestData.MATCHER.assertCollectionEquals(userMeals, service.getBetweenDates(start, end, USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
        LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 31, 10, 0);
        List<UserMeal> userMeals = USER_MEALS.values().stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime(), start, end))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
        MealTestData.MATCHER.assertCollectionEquals(userMeals, service.getBetweenDateTimes(start, end, USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserMeal> userMeals = USER_MEALS.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
        MealTestData.MATCHER.assertCollectionEquals(userMeals, service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal testMeal = USER_MEALS.get(MEAL_ID);
        UserMeal userMeal = new UserMeal(testMeal.getId(), testMeal.getDateTime(), testMeal.getDescription(), testMeal.getCalories());
        userMeal.setDescription("New food");
        userMeal.setCalories(250);
        service.update(userMeal, USER_ID);
        MealTestData.MATCHER.assertEquals(userMeal, service.get(MEAL_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        UserMeal testMeal = USER_MEALS.get(MEAL_ID);
        UserMeal userMeal = new UserMeal(testMeal.getId(), testMeal.getDateTime(), testMeal.getDescription(), testMeal.getCalories());
        userMeal.setDescription("New food");
        userMeal.setCalories(250);
        service.update(userMeal, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal newMeal = new UserMeal(LocalDateTime.now(), "Test food", 500);
        UserMeal createdMeal = service.save(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        List<UserMeal> userMeals = USER_MEALS.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
        userMeals.add(0,newMeal);
        MealTestData.MATCHER.assertCollectionEquals(userMeals, service.getAll(USER_ID));
    }
}