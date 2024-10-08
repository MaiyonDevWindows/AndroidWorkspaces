package com.example.savemoneytime.MainApplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.savemoneytime.DataConverts.DataTypeConvertor;
import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "actionrevenueuser")
public class ActionUserRevenueModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idAction;
    private String titleAction;
    @TypeConverters({DataTypeConvertor.class})
    private Date dateTimeAction;
    private String paymentAction;
    private int idCategory;
    public ActionUserRevenueModel(String titleAction, Date dateTimeAction, String paymentAction, int idCategory) {
        this.titleAction = titleAction;
        this.dateTimeAction = dateTimeAction;
        this.idCategory = idCategory;
        this.paymentAction=paymentAction;
    }
    public String getPaymentAction() {
        return paymentAction;
    }
    public void setPaymentAction(String paymentAction) {
        this.paymentAction = paymentAction;
    }
    public int getIdAction() {
        return idAction;
    }
    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }
    public String getTitleAction() {
        return titleAction;
    }
    public void setTitleAction(String titleAction) {
        this.titleAction = titleAction;
    }
    public Date getDateTimeAction() {
        return dateTimeAction;
    }
    public void setDateTimeAction(Date dateTimeAction) {
        this.dateTimeAction = dateTimeAction;
    }
    public int getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
