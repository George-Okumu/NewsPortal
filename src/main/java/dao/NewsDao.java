package dao;

import java.util.List;
import models.Department;
import models.News;

public interface NewsDao {
    //Create methods
    void add(News news);
    void addNewsToDepartment(News news, Department department);

    //Read methods
    List<News> getAll();                    // gets all news
    List<News> getAllNewsByDepartment(int departmentId); //gets each

    News findById(int id);

    void update(int id, String title, String content, int departmentId);

    void deleteById(int id);
    void clearAll();



}
