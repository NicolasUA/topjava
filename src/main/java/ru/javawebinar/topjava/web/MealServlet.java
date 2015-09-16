package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    public static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm");
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);
    private static List<UserMeal> mealList = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
    private LinkedList<UserMeal> list = new LinkedList<>(mealList);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        request.setAttribute("mealList", UserMealsUtil.getFilteredMealsWithExceeded(list, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if ("delete".equals(request.getParameter("action"))) {
            UserMeal newMeal = list.remove(Integer.parseInt(request.getParameter("id")));
            LOG.debug("delete meat" + newMeal);
        }

        if ("edit".equals(request.getParameter("action"))) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("id", id);
            UserMeal newMeal = list.get(id);
            request.setAttribute("editMeal", newMeal);
            LOG.debug("edit meal" + newMeal);
        }

        if ("add".equals(request.getParameter("action"))) {
            UserMeal newMeal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime"), DATEFORMAT),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            try {
                list.remove(Integer.parseInt(request.getParameter("id")));
            } catch (Exception e) { }
            list.add(newMeal);
            LOG.debug("save meal" + newMeal);
            list.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        }

        doGet(request, response);
    }


}
