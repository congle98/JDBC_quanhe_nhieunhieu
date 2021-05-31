package com.app.service.bookservice;

import com.app.config.ConnectionJDBC;
import com.app.models.Book;
import com.app.models.Category;
import com.app.service.categoryservice.CategoryService;
import com.app.service.categoryservice.ICategoryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{
    Connection connection = ConnectionJDBC.getConnection();
    ICategoryService categoryService = new CategoryService();
    private final String SELECT_ALL_BOOKS = "select * from book";
    private final String DELETE_BOOK_FROM_BOOK = "delete from book where id = ?";
    private final String DELETE_BOOK_FROM_BOOK_CATEGORY = "delete from book_category where book_id = ?";
    private final String INSERT_BOOK_FROM_BOOK = "insert into book(name,author,description) values(?,?,?)";
    private final String INSERT_BOOK_CATEGORY_FROM_BOOK_CATEGORY = "insert into book_category(book_id,category_id) values(?,?)";
    private final String SELECT_BOOK_BY_ID = "select * from book where id = ?";
    private final String UPDATE_BOOK_FROM_BOOK = "update book set name = ?, author = ?, description = ? where id = ?";
    private final String SELECT_BOOK_CATEGORY_FROM_BOOK_CATEGORY_BY_BOOK_ID = "select * from book_category where book_id = ?";
    private final String UPDATE_FROM_BOOK_CATEGORY = "update book_category set book_id = ?, category_id = ? where id = ?";
    private final String COUNT_BOOK_CATEGORY_BY_BOOK_ID = "select count(*) as count from book_category where book_id = ? group by book_id";


    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                List<Category> categories = categoryService.getCategoriesByBookId(id);
                Book book = new Book(id,name,author,description,categories);
                books.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    @Override
    public Book findById(int id) {
        Book book = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int bookId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                book = new Book(bookId,name,author,description);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book;
    }

    @Override
    public void save(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_FROM_BOOK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int bookId= resultSet.getInt(1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_BOOK_CATEGORY_FROM_BOOK_CATEGORY);
//            List<Integer> category_id = new ArrayList<>();
//            for (int i = 0; i <book.getCategories().size() ; i++) {
//                category_id.add(book.getCategories().get(i).getId());
//            }
            for (int i = 0; i <book.getCategories().size(); i++) {
                preparedStatement2.setInt(1,bookId);
                preparedStatement2.setInt(2,book.getCategories().get(i).getId());
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            preparedStatement1 = connection.prepareStatement(DELETE_BOOK_FROM_BOOK_CATEGORY);
            preparedStatement1.setInt(1,id);
            preparedStatement1.executeUpdate();
            preparedStatement2 = connection.prepareStatement(DELETE_BOOK_FROM_BOOK);
            preparedStatement2.setInt(1,id);
            preparedStatement2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

    @Override
    public void edit( Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_FROM_BOOK);
            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.setInt(4,book.getId());
            preparedStatement.executeUpdate();

//            PreparedStatement preparedStatement1 = connection.prepareStatement(COUNT_BOOK_CATEGORY_BY_BOOK_ID);
//            preparedStatement1.setInt(1,book.getId());
//            ResultSet resultSet1 = preparedStatement1.executeQuery();
//            resultSet1.next();
//            int count = resultSet1.getInt(1);


            PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_BOOK_FROM_BOOK_CATEGORY);
            preparedStatement2.setInt(1,book.getId());
            preparedStatement2.executeUpdate();
            PreparedStatement preparedStatement3 = connection.prepareStatement(INSERT_BOOK_CATEGORY_FROM_BOOK_CATEGORY);
            for (int i = 0; i < book.getCategories().size(); i++) {
                preparedStatement3.setInt(1,book.getId());
                preparedStatement3.setInt(2,book.getCategories().get(i).getId());
                preparedStatement3.executeUpdate();
            }

//            for (int i = 0; i < book.getCategories().size(); i++) {
//                resultSet2.next();
//                if(book.getCategories().get(i).getId()!=resultSet2.getInt(3)){
//
//                }
//            }




//            System.out.println(book.getCategories());
//            int i = 0;
//            while (resultSet.next()){
//                PreparedStatement preparedStatement2 = connection.prepareStatement(UPDATE_FROM_BOOK_CATEGORY);
//                preparedStatement2.setInt(1,book.getId());
//                if(book.getCategories().get(i)!=null){
//                    preparedStatement2.setInt(2,book.getCategories().get(i).getId());
//                    preparedStatement2.setInt(3,resultSet.getInt(1));
//                    preparedStatement2.executeUpdate();
//                    i++;
//                }
//            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
