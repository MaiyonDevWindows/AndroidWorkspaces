package com.example.sqliteexample;

public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
    }

    public Product(String productId, String productName, int productQuantity){
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
