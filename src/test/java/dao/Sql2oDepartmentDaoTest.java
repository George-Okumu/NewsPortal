package dao;

import models.Department;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {

    private static Connection conn;
    private static Sql2oEmployeeDao employeeDao;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oNewsDao newsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Georgedatabase1");
        employeeDao = new Sql2oEmployeeDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        employeeDao.clearAll();
        departmentDao.clearAll();
        newsDao.clearAll();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

//    @Test
//    public void findByIdReturnsCorrectDepartment() throws Exception {
//        Department testDepartment = setUpNewDepartment();
//        Department otherDepartment = setUpNewDepartment();
//        assertEquals(otherDepartment, departmentDao.findById(testDepartment.getId()));
//    }

    public Department setUpNewDepartment(){
        return new Department("Bamburi Cement", "Production of cement", 2250);
    }


}