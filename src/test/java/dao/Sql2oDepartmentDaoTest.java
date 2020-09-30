package dao;

import models.Department;
import models.Employee;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

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





    @Test
    public void savesOneInstanceCorrectlyAndGetsRightId_true(){
        Department testDepartment = new Department("Servicing", "Repairs", "2250");
        assertEquals(0, testDepartment.getId());
    }

    @Test
    public void getsTotalSizeCorrectly_true(){
        Department testDepartment = new Department("Servicing", "Repairs", "2250");
        departmentDao.add(testDepartment);
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void returnsZeroIfNoInstanceOfDepartmentExists_0(){
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment(){
        Department testDepartment = setUpDepartment();
        Department testSecondDepartment = new Department("visits", "ushering", "2250");
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }

    @Test
    public void update_UpdatesCorrectlyUpdatesAllFieldsCorrectly_true(){
        Department testDepartment = setUpDepartment();
        departmentDao.update(testDepartment.getId(), "People", "Crowd", "2250");
        Department foundDepartment = departmentDao.findById(testDepartment.getId());
        assertEquals("People", foundDepartment.getDepartmentName());
        assertEquals("Crowd", foundDepartment.getDescription());
        assertEquals("2250", foundDepartment.getNumberOfEmployees());
    }

    @Test
    public void deleteDepartmentByIdDeletesCorrectDepartment(){
        Department testDepartment = setUpDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll_deletesAllTheDataPresentInTheDepartmentsTable_true(){
        Department firstDepartment = setUpDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());

    }

    public Department setUpDepartment(){
        Department department = new Department("Bamburi Cement", "Production of cement", "2250");
        departmentDao.add(department);
        return department;
    }

    public Employee setUpNewEmployee(){
        Employee employee = new Employee("George","Staff", "Managerial Staff", "gokumu12@gmail.com","0798765432",10);
        employeeDao.add(employee);
        return employee;
    }


}