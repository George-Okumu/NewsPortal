
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oEmployeeDao;
import dao.Sql2oNewsDao;
import exceptions.ApiException;
import models.Department;
import models.Employee;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Sql2oDepartmentDao departmentDao;
        Sql2oEmployeeDao employeeDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();
//        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Georgedatabase1");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        employeeDao = new Sql2oEmployeeDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        get("/", "application/json", (req, res) ->
                "{\"message\":\"Hello, Looking for a news API ! WELCOME to NEWS-PORTAL-API mainpage.\"}");


        //Getting all departments
        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("departments/:id", "application/json", (request, response) -> {
            int target = Integer.parseInt(request.params("id"));
            Department department = departmentDao.findById(target);
            if(department != null){
                return gson.toJson(department);
            }else{
                throw new Error("Department with that Id does not exist");
            }
        });


        //Getting news
        get("/news", "application/json", (request, response) -> {
            return gson.toJson(newsDao.getAll());
        });


        // get all employee details
        get("/employees", "application/json", (request, response) -> {
            return gson.toJson(employeeDao.getAll());
        });

        // Getting each employer
        get("employees/:id", "application/json", (request, response) -> {
            int target = Integer.parseInt(request.params("id"));
            Employee employee = employeeDao.findById(target);
            if(employee != null){
                return gson.toJson(employee);
            }else{
                throw new Error("Employee with that Id does not exist");
            }
        });


        get("/departments/:id/employees", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<Department> departmentEmployees;

            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            departmentEmployees = employeeDao.getAllDepartmentsForAnEmployee(departmentId);
            res.type("application/json");
            return gson.toJson(departmentEmployees);
        });


        //post:Add department
        post("/departments/new", "application/json", (request, response)->{
            Department department = gson.fromJson(request.body(), Department.class);
            departmentDao.add(department);
            response.status(201);
            return gson.toJson(department);
        });



       // Posts all Employee details

        post("/employees/new", "application/json", (request, response) ->{
            Employee employee = gson.fromJson(request.body(), Employee.class);
            employeeDao.add(employee);
            response.status(201);
            return gson.toJson(employee);
        });


        //Posting news
        post("/news/new", "application/json", (request, response) -> {
            News news = gson.fromJson(request.body(), News.class);
            newsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });




     // Filters
        exception(ApiException.class, (exc, request, response) -> {
            ApiException err = exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json"); //after does not run in case of an exception.
            response.status(err.getStatusCode()); //set the status
            response.body(gson.toJson(jsonMap));  //set the output.
        });

        after((request, response) ->{
            response.type("application/json");
        });
    }
}
