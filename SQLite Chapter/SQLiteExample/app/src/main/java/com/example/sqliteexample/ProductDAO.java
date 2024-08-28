package com.example.sqliteexample;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    // Thêm sản phẩm
    public void addProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", product.getProductId());
        values.put("product_name", product.getProductName());
        values.put("product_quantity", product.getProductQuantity());
        db.insert("Products", null, values);
        db.close();
    }

    // Sửa sản phẩm
    public void updateProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_name", product.getProductName());
        values.put("product_quantity", product.getProductQuantity());
        db.update("Products", values, "product_id = ?", new String[]{product.getProductId()});
        db.close();
    }

    // Xóa sản phẩm
    public void deleteProduct(String productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Products", "product_id = ?", new String[]{productId});
        db.close();
    }

    // Lấy danh sách sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productId = cursor.getString(cursor.getColumnIndex("product_id"));
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                @SuppressLint("Range") int productQuantity = cursor.getInt(cursor.getColumnIndex("product_quantity"));
                productList.add(new Product(productId, productName, productQuantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
