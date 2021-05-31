package com.app.service.categoryservice;

import com.app.models.Category;
import com.app.service.IService;

import java.util.List;

public interface ICategoryService extends IService<Category> {
    List<Category> getCategoriesByBookId(int bookId);
}
