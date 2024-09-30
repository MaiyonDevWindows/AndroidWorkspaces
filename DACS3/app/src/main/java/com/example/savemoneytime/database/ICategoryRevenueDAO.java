package com.example.savemoneytime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savemoneytime.MainApplication.Models.CategoryRevenueModel;

import java.util.List;

@Dao
public interface ICategoryRevenueDAO {
    @Insert
    void insertCategoryRevenue(CategoryRevenueModel categoryRevenueModel);
    @Query("SELECT * FROM category_revenue ORDER BY idCategory DESC")
    List<CategoryRevenueModel> getListCategoryRevenue();
    @Update
    void updateCategoryRevenue(CategoryRevenueModel categoryRevenueModel);
    @Query("DELETE FROM category_revenue")
    void delete();
    @Query("SELECT * FROM category_revenue WHERE idCategory = (:idCate)")
    List<CategoryRevenueModel> getListByIdCategoryRevenue(int idCate);
}
