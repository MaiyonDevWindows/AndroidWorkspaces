package com.example.sqliteexample;

import android.content.Context;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(Context context) {
        productDAO = new ProductDAO(context);
    }

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(String productId) {
        productDAO.deleteProduct(productId);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}
