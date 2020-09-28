package dao;

import models.Department;
import models.News;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oNewsDao implements NewsDao{

    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(News news) {

    }

    @Override
    public void addNewsToDepartment(News news, Department department) {

    }

    @Override
    public List<News> getAll() {
        return null;
    }

    @Override
    public List<News> getAllNewsByDepartment(int departmentId) {
        return null;
    }

    @Override
    public News findById(int id) {
        return null;
    }

    @Override
    public void update(int id, String title, String content, int departmentId) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
