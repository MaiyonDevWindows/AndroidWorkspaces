package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;
    private Context context;
    public ProductDAO(Context context){
        this.context = context;
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public int insertProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("productId", product.getProductId());
        values.put("productName", product.getProductName());
        values.put("productQuantity", String.valueOf(product.getProductQuantity()));
        long result = db.insert("product", null, values);
        return result > 0 ? 1 : -1;
    }
    public List<String> getAllProductsToString(){
        List<String> strings = new ArrayList<>();
        Cursor cursor = db.query("PRODUCT", null, null, null, null, null, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Product product = new Product();
            product.setProductId(cursor.getString(0));
            product.setProductName(cursor.getString(1));
            String str = product.getProductId() + " - " + product.getProductName() + " - " + product.getProductQuantity();
            strings.add(str);
        }
        cursor.close();
        return strings;
    }
}
