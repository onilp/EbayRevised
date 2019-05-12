package com.example.ebayrevised.database.model;

import com.util.Constant;

public class CartProduct {
     private String productName;
     private String productPrize;
     private String productDiscription;
     private String productQuantity;
     int id;

     public static final String CREATE_TABLE =
             "CREATE TABLE " + Constant.TABLE_NAME + "("
                     + Constant.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + Constant.COLUMN_NAME + " TEXT,"
                     + Constant.COLUMN_PRIZE + " TEXT,"
                     + Constant.COLUMN_DISCRIPTION + "TEXT,"
                     + Constant.COLUMN_QUANTITY + "TEXT" + ")";

     public CartProduct(){}

     public CartProduct(int id, String productName, String productPrize, String productDiscription, String productQuantity){
         this.id =id;
         this.productName =productName;
         this.productPrize = productPrize;
         this.productDiscription = productDiscription;
         this.productQuantity = productQuantity;
     }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrize() {
        return productPrize;
    }

    public void setProductPrize(String productPrize) {
        this.productPrize = productPrize;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}