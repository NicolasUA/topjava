package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        if ("delete".equals(request.getParameter("action"))) {
            list.remove(Integer.parseInt(request.getParameter("id")));
        }
        UserMeal newMeal = null;
        Integer id = null;
        if ("edit".equals(request.getParameter("action"))) {
            id = Integer.parseInt(request.getParameter("id"));
            newMeal = list.get(id);
        }
        if ("add".equals(request.getParameter("action"))) {
            newMeal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            try {
                list.remove(Integer.parseInt(request.getParameter("id")));
            } catch (Exception e) {}
            list.add(newMeal);
            list.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
            newMeal = null;
            id = null;
        }

        HttpSession session = request.getSession();
        session.setAttribute("mealList", UserMealsUtil.getFilteredMealsWithExceeded(list, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        session.setAttribute("editMeal", newMeal);
        session.setAttribute("id", id);
        response.sendRedirect("mealList.jsp");
    }
}
