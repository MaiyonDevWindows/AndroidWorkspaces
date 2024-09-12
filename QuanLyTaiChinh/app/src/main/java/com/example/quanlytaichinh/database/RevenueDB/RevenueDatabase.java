package com.example.quanlytaichinh.database.RevenueDB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.quanlytaichinh.MainApplication.Models.ActionUserRevenueModel;
import com.example.quanlytaichinh.MainApplication.Models.CatelogyRevenueModel;
import com.example.quanlytaichinh.database.CategoryRevenueDAO;
@Database(entities = {CatelogyRevenueModel.class, ActionUserRevenueModel.class}, version = 2, exportSchema = true)
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
    public abstract CategoryRevenueDAO categoryRevenueDAO();
    public abstract ActionUserRevenueDao actionUserRevenueDao();
}
