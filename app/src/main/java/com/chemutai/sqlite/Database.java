package com.chemutai.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    Context context;

    public Database(@Nullable Context context) {
        super(context, "customers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS customers (id INTEGER PRIMARY KEY AUTOINCREMENT, NAMES TEXT, EMAIL TEXT, BALANCE REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE customers");

        db.execSQL("CREATE TABLE customers (id INTEGER PRIMARY KEY AUTOINCREMENT, NAMES TEXT, EMAIL TEXT, BALANCE REAL)");
    }

    public void insert(Customer customer){
        //create content values
        ContentValues values = new ContentValues();
        values.put("NAMES", customer.getNames());
        values.put("EMAIL", customer.getEmail());
        values.put("BALANCE", customer.getBalance());

        this.getWritableDatabase().insert("customers", "", values);
    }

    public int count(){
        String sql = "SELECT COUNT(id) FROM customers ";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public ArrayList<Customer> show(){
        String sql = "SELECT * FROM customers";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);

        ArrayList<Customer> customers = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                Log.d("CUSTOMERS", "ID: "+cursor.getInt(0));
                Log.d("CUSTOMERS", "NAMES: "+cursor.getString(1));
                Log.d("CUSTOMERS", "EMAIL: "+cursor.getString(2));
                Log.d("CUSTOMERS", "BALANCE: "+cursor.getDouble(3));
                Log.d("CUSTOMERS", "............................................. ");

                Customer c = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));

                customers.add(c);
            }
            while (cursor.moveToNext());
        }
        return customers;
    }

    public void delete(long id){
        this.getWritableDatabase().delete("CUSTOMERS", "ID = "+id, null);
    }

    public void update(Customer customer){
        ContentValues values = new ContentValues();
        values.put("NAMES", customer.getNames());
        values.put("EMAIL", customer.getEmail());
        values.put("BALANCE", customer.getBalance());

        this.getWritableDatabase().update("CUSTOMERS", values, "ID="+customer.getId(), null);
    }
}
