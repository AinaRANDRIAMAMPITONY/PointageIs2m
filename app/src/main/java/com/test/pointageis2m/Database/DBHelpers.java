package com.test.pointageis2m.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelpers extends SQLiteOpenHelper{
        public static final String DBNAME="Login.db";
        public DBHelpers( Context context) {
            super(context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table users(password TEXT primary key)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public Boolean insertData( String password){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();


            values.put("password",password);

            long result = db.insert("users",null,values);

            if(result==-1)return false;
            else
                return true;
        }

        public Boolean checkUsernamePassword( String password){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from users where  password=?", new String[] {password});
            if (cursor.getCount()>0)
                return true;
            else
                return false;
        }

    }

