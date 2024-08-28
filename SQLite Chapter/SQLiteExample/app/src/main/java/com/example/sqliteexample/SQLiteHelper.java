package com.example.sqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Products
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE Products (" +
                "product_id TEXT PRIMARY KEY," +
                "product_name TEXT," +
                "product_quantity INTEGER" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi cần nâng cấp cơ sở dữ liệu
        db.execSQL("DROP TABLE IF EXISTS Products");
        onCreate(db);
    }
}
