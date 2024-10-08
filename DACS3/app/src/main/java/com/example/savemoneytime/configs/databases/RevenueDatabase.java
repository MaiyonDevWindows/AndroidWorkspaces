package com.example.savemoneytime.configs.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.MainApplication.Models.CategoryRevenueModel;
import com.example.savemoneytime.database.IActionUserRevenueDAO;
import com.example.savemoneytime.database.ICategoryRevenueDAO;

@Database(entities = {CategoryRevenueModel.class, ActionUserRevenueModel.class}, version = 2)
public abstract class RevenueDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="revenue1.db";
    private static RevenueDatabase instance;
    public static synchronized RevenueDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),RevenueDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ICategoryRevenueDAO categoryRevenueDAO();
    public abstract IActionUserRevenueDAO actionUserRevenueDao();
}
