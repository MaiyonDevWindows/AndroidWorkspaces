package com.example.savemoneytime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savemoneytime.MainApplication.Models.CategoryModel;

import java.util.List;

@Dao
public interface ICategoryDAO {
    @Insert
    void insertCategory(CategoryModel categoryModel);
    @Query("SELECT * FROM category ORDER BY idCategory DESC")
    List<CategoryModel> getListCategory();
    @Update
    void updateCategory(CategoryModel categoryModel);
    @Query("DELETE FROM category")
    void delete();
    @Query("SELECT * FROM category WHERE idCategory = (:idCate)")
    List<CategoryModel> getListByIdCategory(int idCate);
}
