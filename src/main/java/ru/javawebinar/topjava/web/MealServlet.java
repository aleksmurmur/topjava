package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.CreateMealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static final int caloriesPerDay = 2000;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEALS_LIST = "/meals.jsp";

    private MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (dao.getAll().isEmpty()) addMeals();

//        List<MealTo> meals = MealsUtil.filteredByStreams(MealsUtil.generateMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
//        req.setAttribute("meals", meals);
//        req.setAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
//        req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        String forward = "";
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("meals", dao.getAll());
            forward = MEALS_LIST;
        }
        else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            dao.delete(mealId);
            req.setAttribute("meals", dao.getAll());
            forward = MEALS_LIST;
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            req.setAttribute("meal", dao.get(mealId));
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("listMeals")) {
            req.setAttribute("meals", dao.getAll());
            forward = MEALS_LIST;
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("time"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories").trim());

        String mealId = req.getParameter("mealId").trim();

        if (mealId == null || mealId.isEmpty()) {
            dao.add(new CreateMealTo(dateTime, description, calories));
        } else {
            Integer id = Integer.parseInt(mealId.trim());
            dao.update(id, new Meal(id, dateTime, description, calories));
        }
        RequestDispatcher view = req.getRequestDispatcher(MEALS_LIST);
        req.setAttribute("meals", dao.getAll());
        view.forward(req, resp);
    }
    private  void addMeals() {
        List<CreateMealTo> generatedMeals = MealsUtil.generateMeals();
        for (CreateMealTo m : generatedMeals) {
            dao.add(m);
        }
        log.debug("{} meals generated", generatedMeals.size());
    }

}
