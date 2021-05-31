package com.app.controller;

import com.app.models.Book;
import com.app.models.Category;
import com.app.service.bookservice.BookService;
import com.app.service.bookservice.IBookService;
import com.app.service.categoryservice.CategoryService;
import com.app.service.categoryservice.ICategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/Books")
public class BookController extends HttpServlet {
    IBookService bookService = new BookService();
    ICategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action = "";
        }
        switch (action){
            case "create":
                showFormCreateBook(req,resp);
                break;
            case "edit":
                showFormEditBook(req,resp);
                break;
            case "delete":
                deleteBook(req,resp);
                break;
            default:
                showListBooks(req,resp);
                break;
        }
    }

    private void showFormEditBook(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Book book = bookService.findById(id);
            List<Category> categories = categoryService.findAll();
            List<Category> categoriesOfBook = categoryService.getCategoriesByBookId(id);
            req.setAttribute("book",book);
            req.setAttribute("categories",categories);
            req.setAttribute("categoriesOfBook",categoriesOfBook);
            RequestDispatcher rd = req.getRequestDispatcher("/book/editBook.jsp");
            rd.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showFormCreateBook(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories",categories);
        RequestDispatcher rd = req.getRequestDispatcher("/book/createBook.jsp");
        try {
            rd.forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createBook(HttpServletRequest req, HttpServletResponse resp){
        RequestDispatcher rd = req.getRequestDispatcher("/book/createBook.jsp");
        try {
            String name = req.getParameter("name");
            String author = req.getParameter("author");
            String description = req.getParameter("description");
            String[] category = req.getParameterValues("category_id");

            List<Category> categories = new ArrayList<>();
            for (String categoryid: category
            ) {
                categories.add(new Category(Integer.parseInt(categoryid)));
            }
            Book book = new Book(name,author,description,categories);
            bookService.save(book);
            req.setAttribute("message","khởi tạo thành công");
            rd.forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message","khởi tạo thất bại");
            try {
                rd.forward(req,resp);
            } catch (ServletException servletException) {
                servletException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        bookService.delete(id);
        try {
            resp.sendRedirect("/Books");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showListBooks(HttpServletRequest req, HttpServletResponse resp) {

        try {
            List<Book> bookList = bookService.findAll();
            req.setAttribute("bookList",bookList);
            RequestDispatcher rd = req.getRequestDispatcher("/book/listBook.jsp");
            rd.forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action = "";
        }
        switch (action){
            case "create":
                createBook(req,resp);
                break;
            case "edit":
                editBook(req,resp);
                break;

        }
    }

    private void editBook(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String author = req.getParameter("author");
            String description = req.getParameter("description");
            String [] category_id_string_list= req.getParameterValues("category_id");
            List<Category> categories = new ArrayList<>();
            for (String category_id_string :category_id_string_list
            ) {
                categories.add(new Category(Integer.parseInt(category_id_string)));
            }
            Book book = new Book(id,name,author,description,categories);
            bookService.edit(book);
            resp.sendRedirect("/Books");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
