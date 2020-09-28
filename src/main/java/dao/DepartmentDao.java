package dao;

import models.Department;
import models.Employee;
import models.News;

import java.util.List;

public interface DepartmentDao {
    void add(Department department);
    void addDepartmentToEmployee(Department department, Employee employee);

    List<Department> getAll();
    List<Employee> getAllEmployeesByDepartment(int departmentId);


    Department findById(int id);

    void update(int id, String departmentName, String description, int numberOfEmployees);

    void deleteById(int id);

    void clearAll();



}
