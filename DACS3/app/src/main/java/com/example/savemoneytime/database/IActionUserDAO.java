package com.example.savemoneytime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import java.util.List;

@Dao
public interface IActionUserDAO {
    @Query("DELETE FROM actionuser WHERE idAction = :idAction")
    void deleteById(int idAction);
    @Insert
    void insertActionUser(ActionUserModel actionUserModel);
    @Query("SELECT * FROM actionuser")
    List<ActionUserModel> getListAction();
}
