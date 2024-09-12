package com.example.quanlytaichinh.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.quanlytaichinh.MainApplication.Models.CatelogyRevenueModel;
import java.util.List;

@Dao
public interface CategoryRevenueDAO {
    @Insert
    void insertCategoryRevenue(CatelogyRevenueModel catelogyRevenueModel);

    @Query("SELECT * FROM categoryrevenue ORDER BY idCategory DESC")
    List<CatelogyRevenueModel> getListCategoryRevenue();

    @Update
    void updateCategoryRevenue(CatelogyRevenueModel catelogyRevenueModel);

    @Query("DELETE FROM categoryrevenue")
    void delete();

    @Query("SELECT * FROM categoryrevenue WHERE idCategory = (:idCate)")
    List<CatelogyRevenueModel> getListByIdCategoryRevenue(int idCate);
}
