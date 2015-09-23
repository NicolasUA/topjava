package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.Filter;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private UserMealRestController mealController;
    private AdminRestController adminController;
    private Filter filter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            adminController = appCtx.getBean(AdminRestController.class);
            mealController = appCtx.getBean(UserMealRestController.class);
        }
        filter = new Filter();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                LoggedUser.getId());
        if (id.isEmpty()) {
            LOG.info("Create {}" , userMeal);
            mealController.create(userMeal);
        } else {
            LOG.info("Update {}" , userMeal);
            mealController.update(userMeal, Integer.valueOf(id));
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    UserMealsUtil.getWithExceeded(mealController.getFiltered(filter),
                            adminController.get(LoggedUser.getId()).getCaloriesPerDay()));
            request.setAttribute("userList", adminController.getAll());
            request.setAttribute("userId", LoggedUser.getId());
            request.setAttribute("filter", filter);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealController.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("filter")) {
            LoggedUser.setId(Integer.parseInt(request.getParameter("userId")));
            filter.setFromDate(request.getParameter("fromDate").isEmpty() ? null :
                    LocalDate.parse(request.getParameter("fromDate")));
            filter.setToDate(request.getParameter("toDate").isEmpty() ? null :
                    LocalDate.parse(request.getParameter("toDate")));
            filter.setFromTime(request.getParameter("fromTime").isEmpty() ? null :
                    LocalTime.parse(request.getParameter("fromTime")));
            filter.setToTime(request.getParameter("toTime").isEmpty() ? null :
                    LocalTime.parse(request.getParameter("toTime")));
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000, LoggedUser.getId()) :
                    mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
