package dao;
import models.Department;
import models.Employee;

import java.util.List;

public interface EmployeeDao {
    void add(Employee employee);
    void addEmployeeToDepartment(Employee employee, Department department);

    List<Employee> getAll();
    List<Department> getAllDepartmentsForAnEmployee(int id);

    Employee findById(int id);

    void deleteById(int id);
    void clearAll();


}
