package com.example.savemoneytime.MainApplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "category_revenue")
public class CategoryRevenueModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idCategory;
    private int sourceImgCategory;
    private String titleCategory;
    public CategoryRevenueModel(int sourceImgCategory, String titleCategory) {
        this.sourceImgCategory = sourceImgCategory;
        this.titleCategory = titleCategory;
    }
    public int getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
    public int getSourceImgCategory() {
        return sourceImgCategory;
    }
    public void setSourceImgCategory(int sourceImgCategory) {
        this.sourceImgCategory = sourceImgCategory;
    }
    public String getTitleCategory() {
        return titleCategory;
    }
    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }
}
