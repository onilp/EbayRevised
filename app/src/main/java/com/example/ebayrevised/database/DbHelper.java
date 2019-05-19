package com.example.ebayrevised.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ebayrevised.model.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME ="shoppingCart";
    //table name
    private static final String TABLE_CART_PRODUCTS = "cartProducts";
    //column name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRIZE = "prize";
    private static final String KEY_DISCRIPTION = "discription";
    private static final String KEY_QUANTITY = "quantity";

    //table create statement
    private static final String CREATE_TABLE_CART_PRODUCTS = "CREATE TABLE "
            + TABLE_CART_PRODUCTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AutoIncrement," +
                KEY_NAME + " TEXT," +
                KEY_PRIZE + " TEXT," +
                KEY_DISCRIPTION + " TEXT," +
                KEY_QUANTITY + " TEXT" + ")";


    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CART_PRODUCTS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_PRODUCTS);
        //create new table
        onCreate(db);
    }

    public long addToCart(ProductDetail productDetail){
        Log.d("about to add to cart", productDetail.toString());

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,productDetail.getName());
        values.put(KEY_PRIZE,productDetail.getPrize());
        values.put(KEY_DISCRIPTION,productDetail.getDiscription());
        values.put(KEY_QUANTITY,productDetail.getQuantity());

        long itemId = database.insert(TABLE_CART_PRODUCTS,null,values);
        database.close();
        Log.d("added to cart", productDetail.toString());

        return itemId;
    }

    public List<ProductDetail> displayCart (){

        List<ProductDetail> productDetailList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CART_PRODUCTS;

        SQLiteDatabase database = this.getWritableDatabase();
        try (Cursor cursor = database.rawQuery(query, null)) {

            ProductDetail productDetail;

            if (cursor.moveToFirst()) {
                do {
                    productDetail = new ProductDetail();
                    productDetail.setName(cursor.getString(1));
                    productDetail.setPrize(cursor.getString(2));
                    productDetail.setDiscription(cursor.getString(3));
                    productDetail.setQuantity(cursor.getString(4));

                    productDetailList.add(productDetail);
                } while (cursor.moveToNext());
            }
        }
        Log.d("displayCart",productDetailList.toString());
        return productDetailList;
    }
    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CART_PRODUCTS,null,null);
        db.execSQL("delete   from "+ TABLE_CART_PRODUCTS);
    }

    public void deleteItemFromCart(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_PRODUCTS, KEY_NAME + " = ?",
                new String[] { name });



    }

}