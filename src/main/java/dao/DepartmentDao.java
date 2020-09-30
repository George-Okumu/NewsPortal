package dao;

import models.Department;
import models.Employee;
import models.News;

import java.util.List;

public interface DepartmentDao {

    //create
    void add(Department department);
    void addDepartmentToUser(Department department, Employee employee);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<Employee> getAllUsersByDepartment(int departmentId);

    //update
    void update(int id, String departmentName, String description, String numberOfEmployees);

    //delete
    void deleteById(int id);
    void clearAll();


}
