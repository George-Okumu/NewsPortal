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
    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oEmployeeDao employeeDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();
        String connectionString = "jdbc:h2:~/news-portal.DB;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        employeeDao = new Sql2oEmployeeDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        //Getting news
        get("/news", "application/json", (request, response) -> {
            return gson.toJson(newsDao.getAll());
        });

        //gets all department information
        get("/departments", "application/json", (request, response) -> {
            System.out.println(departmentDao.getAll());
            return gson.toJson(departmentDao.getAll());
        });

        // get all employee details
        get("/employees", "application/json", (request, response) -> {
            return gson.toJson(employeeDao.getAll());
        });


        get("employees/:id", "application/json", (request, response) -> {
            int target = Integer.parseInt(request.params("id"));
            Employee employee = employeeDao.findById(target);
            if(employee != null){
                return gson.toJson(employee);
            }else{
                throw new Error("Employee with that Id does not exist");
            }
        });

        //getting each news that belongs to a department
        get("/departments/:id/news", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("That department does not exists", request.params("id")));
            }

            allNews = newsDao.getAllNewsByDepartment(departmentId);

            return gson.toJson(allNews);
        });

        //getting an employee from a department
        get("/employees/:id/departments", "application/json", (request, response) -> {
            int employeeId = Integer.parseInt(request.params("id"));
            Employee employeeToFind = employeeDao.findById(employeeId);
            if (employeeToFind == null){
                throw new Error(String.format("No employee exist in this department");
            }
            else if (employeeDao.getAllDepartmentsForAnEmployee(employeeId).size()==0){
                return "{\"message\":\"I'm sorry, but no department exists for this employee.\"}";
            }
            else {
                return gson.toJson(employeeDao.getAllDepartmentsForAnEmployee(employeeId));
            }
        });


        //Posts all department information
        post("/departments/new", "application/json", (request, response) -> {
            Department department = gson.fromJson(request.body(), Department.class);
            departmentDao.add(department);
            response.status(201);
            return gson.toJson(department);
        });


        //Posts all Employee details

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


        //Posting all news to a specific department
        post("/departments/:departmentsId/news/new", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentsId"));
            News news = gson.fromJson(request.body(), News.class);
            news.setDepartmentId(departmentId);
            newsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        //Adding employee to a specific department
        post("/departments/:departmentsId/employee/:employeeId", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            int employeeId = Integer.parseInt(request.params("employeeId"));
            Department department = departmentDao.findById(departmentId);
            Employee employee = employeeDao.findById(employeeId);

            if (department != null && employee != null){
                //both exist and can be associated - we should probably not connect things that are not here.
                employeeDao.addEmployeeToDepartment(employee, department);
                response.status(201);
                return gson.toJson(String.format("Employee '%s' works at '%s' ",department.getDepartmentName(), employee.getEmployeeName()));
            }
            else {
                throw new Error("Department does not exist");
            }
        });



     // Filters
        exception(ApiException.class, (exc, req, res) -> {
            ApiException err = exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json"); //after does not run in case of an exception.
            res.status(err.getStatusCode()); //set the status
            res.body(gson.toJson(jsonMap));  //set the output.
        });

        after((request, response) ->{
            response.type("application/json");
        });
    }
}
