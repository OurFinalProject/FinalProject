package com.example.a29751.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by haofa on 2017-12-03.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {


    protected static final String DATABASE_NAME = "Autodata1";
    protected static final String TABLE_NAME = "auto";
    protected static final int VERSION_NUM = 1;
    /*protected static final String KEY_MESSAGE = "Message";*/
    protected static final String KEY_ID = "id";
    protected static final String KEY_FUEL = "fuel";
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_KILO = "kilometers";
    protected static final String KEY_DATE = "Date";

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FUEL + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_KILO + " TEXT,"
                + KEY_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
        Log.i("AutoDatabaseHelper", "Calling onCreate");
    }

    public void eidtInfo(SQLiteDatabase db,long id,String autoF, String autoP, String autoK, String autoD){
        ContentValues contentVn = new ContentValues();
        contentVn.put(KEY_FUEL, autoF);
        contentVn.put(KEY_PRICE, autoP);
        contentVn.put(KEY_KILO, autoK);
        contentVn.put(KEY_DATE,  autoD);

        db.update(TABLE_NAME,contentVn,KEY_ID + "=" + id, null);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);

    }
}
