package dao;

import models.Department;
import models.Employee;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oEmployeeDao implements EmployeeDao {

    private final Sql2o sql2o;

    public Sql2oEmployeeDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Employee employee) {
        String sql = "INSERT INTO employees (employeeName, position, role, email, phoneNumber, departmentId) VALUES (:employeeName, :position, :role, :email, :phoneNumber, :departmentId);";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(employee)
                    .executeUpdate()
                    .getKey();
            employee.setId(id);
        } catch (Sql2oException ex){
            System.out.println("An error occurs");
        }
    }

    @Override
    public void addEmployeeToDepartment(Employee employee, Department department) {
        String sql = "INSERT INTO departments_employees (departmentid, employeeid) VALUES (:departmentId, :employeeId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("employeeId", employee.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println("Error");
        }
    }

    @Override
    public List<Employee> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM employees")
                    .executeAndFetch(Employee.class);
        }
    }


    @Override
    public List<Department> getAllDepartmentsForAnEmployee(int employeeId) {
        List<Department> departments = new ArrayList();
        String joinQuery = "SELECT departmentid FROM departments_employees WHERE employeeid = :employeeId";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentsIds = con.createQuery(joinQuery)
                    .addParameter("employeeId", employeeId)
                    .executeAndFetch(Integer.class);
            for (Integer departmentId : allDepartmentsIds){
                String restaurantQuery = "SELECT * FROM departments WHERE id = :departmentId";
                departments.add(
                        con.createQuery(restaurantQuery)
                                .addParameter("departmentId", departmentId)
                                .executeAndFetchFirst(Department.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public Employee findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM employees WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Employee.class);
        }
    }

    @Override
    public void deleteById(int id) {

        String sql = "DELETE from employees WHERE id = :id";
        String deleteJoin = "DELETE from departments_employees WHERE employeesid = :employeesId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("employeesId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println("Error Occurs");
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from employees";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println("Error Occurs");
        }
    }

    }
