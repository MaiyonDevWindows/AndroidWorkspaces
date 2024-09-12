package com.example.quanlytaichinh.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlytaichinh.MainApplication.Models.CatelogyModel;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(CatelogyModel catelogyModel);

    @Query("SELECT * FROM category ORDER BY idCategory DESC")
    List<CatelogyModel> getListCategory();

    @Update
    void updateCategory(CatelogyModel catelogyModel);

    @Query("DELETE FROM category")
    void delete();

    @Query("SELECT * FROM category WHERE idCategory = (:idCate)")
    List<CatelogyModel> getListByIdCategory(int idCate);
}
