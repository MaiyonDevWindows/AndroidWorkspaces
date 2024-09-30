package com.example.savemoneytime.configs.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.CategoryModel;
import com.example.savemoneytime.MainApplication.Models.Setting;
import com.example.savemoneytime.database.IActionUserDAO;
import com.example.savemoneytime.database.ICategoryDAO;
import com.example.savemoneytime.database.ISettingUserDAO;

@Database(
        entities = {CategoryModel.class, ActionUserModel.class, Setting.class}, version = 2)
public abstract class SaveDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="savemoney1.db";
    private static SaveDatabase instance;
    public static synchronized SaveDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),SaveDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ICategoryDAO categoryDAO();
    public abstract IActionUserDAO actionUserDao();
    public abstract ISettingUserDAO aSettingUserDAO();
    //  public abstract CategoryRevenueDAO categoryRevenueModel();
}
