package com.app.service.categoryservice;

import com.app.config.ConnectionJDBC;
import com.app.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    Connection connection = ConnectionJDBC.getConnection();
    private final String SELECT_CATEGORIES_BY_BOOK_ID="select c.id as category_id,\n" +
            "       c.name as category_name,\n" +
            "       c.description as category_description\n" +
            "from book\n" +
            "join book_category bc on book.id = bc.book_id\n" +
            "join category c on c.id = bc.category_id\n" +
            "where book.id  = ?;";
    private final String SELECT_ALL_CATEGORY = "select * from category";
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category(id,name,description);
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        return null;
    }

    @Override
    public void save(Category p) {

    }
    public List<Category> getCategoriesByBookId(int bookId){
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIES_BY_BOOK_ID);
            preparedStatement.setInt(1,bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                Category category = new Category(id,name,description);
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit( Category category) {

    }
}
