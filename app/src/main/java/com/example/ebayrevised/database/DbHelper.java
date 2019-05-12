package com.example.ebayrevised.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ebayrevised.database.model.CartProduct;
import com.util.Constant;

public class DbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;


    // Database Name


    public DbHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CartProduct.CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long addToCart(CartProduct cartProduct){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.COLUMN_NAME,cartProduct.getProductName());
        values.put(Constant.COLUMN_PRIZE,cartProduct.getProductPrize());
        values.put(Constant.COLUMN_QUANTITY,cartProduct.getProductQuantity());
        values.put(Constant.COLUMN_DISCRIPTION,cartProduct.getProductDiscription());

        long id = db.insert(Constant.TABLE_NAME,null,values);
        db.close();
        return id;

    }
    public Cursor displayCart (){
        return db.query(Constant.TABLE_NAME,null,null,
                null,null,null,null);
    }

}