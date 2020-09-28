package dao;

import models.Department;
import models.Employee;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oEmployeeDao implements EmployeeDao {

    private final Sql2o sql2o;

    public Sql2oEmployeeDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add() {

    }

    @Override
    public void addEmployeeToDepartment(Employee employee, Department department) {

    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public List<Department> getAllEmployeesForADepartment(int id) {
        return null;
    }

    @Override
    public Employee findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
