package com.example.savemoneytime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.savemoneytime.MainApplication.Models.Setting;
import java.util.List;

@Dao
public interface ISettingUserDAO {
    @Insert
    void insertSetting(Setting setting);
    @Query("SELECT * FROM settinguser")
    List<Setting> getsetting();
    @Query("UPDATE settinguser SET weekless=(:weekless), normal =(:normal), strong=(:strong)  WHERE idSetting = idSetting ")
    void update(int weekless, int normal, int strong);
}
