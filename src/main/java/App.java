import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oEmployeeDao;
import dao.Sql2oNewsDao;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oEmployeeDao employeeDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();
//String connectionString =
//  "jdbc:h2:~/reviews.db;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        String connectionString = "jdbc:h2:~/news-portal.DB;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        employeeDao = new Sql2oEmployeeDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();


        //Posts all department information
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //gets all department information

        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            System.out.println(departmentDao.getAll());
            return gson.toJson(departmentDao.getAll());//send it back to be displayed
        });

        after((req, res) ->{
            res.type("application/json");
        });
    }
}
