package models;

import java.util.Objects;

public class Department {
    private String departmentName;
    private String description;
    private int numberOfEmployees;
    private int id;

    public Department(String departmentName, String description, int numberOfEmployees) {
        this.departmentName = departmentName;
        this.description = description;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return numberOfEmployees == that.numberOfEmployees &&
                id == that.id &&
                departmentName.equals(that.departmentName) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName, description, numberOfEmployees, id);
    }
}
