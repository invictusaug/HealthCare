package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.security.identity.DocTypeNotSupportedException;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "create table users(username text, email text, password text)";
        db.execSQL(qry1);
        String qry2 = "create table cart(username text, product text, price float, otype text)";
        db.execSQL(qry2);
        String qry3 = "create table orderplace(username text, fullname text, address text, contactno text, pincode int, date text, time text, amount float, otype text)";
        db.execSQL(qry3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }
    public boolean login(String username, String password) {
        boolean result = false;
        String[] str = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.rawQuery("select * from users where username=? and password=?", str)) {
            if (c.moveToFirst()) {
                result = true;
            }
        }
        return result;
    }
    public void addCart(String username, String product, float price, String otype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("product", product);
        contentValues.put("price", price);
        contentValues.put("otype", otype);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("cart", null, contentValues);
        sqLiteDatabase.close();
    }
    public boolean checkCart(String username, String product) {
        boolean result = false;
        String[] str = new String[2];
        str[0] = username;
        str[1] = product;
        SQLiteDatabase database = getReadableDatabase();
        try (Cursor c = database.rawQuery("select * from cart where username=? and product=?", str)) {
            if (c.moveToFirst()) {
                result = true;
            }
        }
        return result;
    }
    public void removeCart(String username, String otype) {
        String[] str = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("cart","username=? and otype=?",str);
        sqLiteDatabase.close();
    }
    public ArrayList getCartData(String username, String otype) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] strings = new String[2];
        strings[0] = username;
        strings[1] = otype;
        try (Cursor cursor = sqLiteDatabase.rawQuery("select * from cart where username = ? and otype = ?", strings)) {
            if (cursor.moveToFirst()) {
                do {
                    String product = cursor.getString(1);
                    String price = cursor.getString(2);
                    arrayList.add(product + "$" + price);
                } while (cursor.moveToNext());
            }
        }
        sqLiteDatabase.close();
        return arrayList;
    }
    public  void addOrder(String username, String fullname,String address, String contact, int pincode, String date, String time, float price, String otype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("fullname", fullname);
        contentValues.put("address", address);
        contentValues.put("contact", contact);
        contentValues.put("pincode", pincode);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("amount", price);
        contentValues.put("otype", otype);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("orderplace", null, contentValues);
        sqLiteDatabase.close();
    }
    public ArrayList getOrderData(String username) {
        ArrayList<String > stringArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] strings = new String[1];
        try (Cursor cursor = sqLiteDatabase.rawQuery("select * from orderplace where username = ?", strings)) {
            if (cursor.moveToFirst()) {
                do {
                    stringArrayList.add(cursor.getString(1) + "$" + cursor.getString(2) + "$" + cursor.getString(3) + "$" + cursor.getString(4) + "$" + cursor.getString(5) + "$" + cursor.getString(6) + "$" + cursor.getString(7) + "$" + cursor.getString(8));
                } while (cursor.moveToNext());
            }
        }
        sqLiteDatabase.close();
        return stringArrayList;
    }
    public boolean checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time) {
        boolean result = false;
        String[] strings = new String[6];
        strings[0] = username;
        strings[1] = fullname;
        strings[2] = address;
        strings[3] = contact;
        strings[4] = date;
        strings[5] = time;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try (Cursor cursor = sqLiteDatabase.rawQuery("select * from orderplace where username = ? and fullname = ? and address = ? and contactno = ? and date = ? and time = ?", strings)) {
            if (cursor.moveToFirst()) {
                result = true;
            }
        }
        sqLiteDatabase.close();
        return result;
    }
}
