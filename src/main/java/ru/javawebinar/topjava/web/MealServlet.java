package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;
    private MealRestController mealRestController;
private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
//        repository = new InMemoryMealRepository();
        try {
            appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
             mealRestController = appCtx.getBean(MealRestController.class);
        } catch (Exception e) { e.printStackTrace();}

    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");


        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) mealRestController.create(meal);
        else mealRestController.update(meal);
//        repository.save(SecurityUtil.authUserId(), meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
//                repository.delete(SecurityUtil.authUserId(), id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
//                        repository.get(SecurityUtil.authUserId(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("getFiltered");
                List<MealTo> meals = mealRestController.getWithFilter(
                        getDate(request, "startDate"),
                        getTime(request, "startTime"),
                        getDate(request, "endDate"),
                        getTime(request, "endTime")
                );
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
//                        MealsUtil.getTos(repository.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

private LocalDate getDate(HttpServletRequest request, String parameter) {
        return !request.getParameter(parameter).isEmpty() ? LocalDate.parse(request.getParameter(parameter)) : null;
}
    private LocalTime getTime(HttpServletRequest request, String parameter) {
        return !request.getParameter(parameter).isEmpty() ? LocalTime.parse(request.getParameter(parameter)) : null;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}