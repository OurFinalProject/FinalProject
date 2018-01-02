package com.example.a29751.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 29751 on 2017-10-11.
 */

public class foodDatabaseHelper extends SQLiteOpenHelper{
    public final String ACTIVITY_NAME ="foodDatabaseHelper";
    public final static String DATABASE_NAME = "fdDatabase.db";
    public final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "myTable";
    public final static String ID_HEADER = "ID" ;
    public final static String NAME_HEADER = "foodName";
    public final static String CAL_HEADER = "calName";
    public final static String FAT_HEADER = "fatName";
    public final static String CARBO_HEADER = "carboName";
    public final static String DATE_HEADER = "date";
    public final static String TIME_HEADER = "time";


    public Cursor cur;
    public foodDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME,null,VERSION_NUM);

        Log.i(ACTIVITY_NAME, "new foodDatabaseHelper ");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_MSG = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_HEADER  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + NAME_HEADER + " TEXT ," +
                CAL_HEADER + " TEXT ," +
                FAT_HEADER + " TEXT," +
                CARBO_HEADER + " TEXT," +
                DATE_HEADER + " TEXT," +
                TIME_HEADER + " TEXT )";
        db.execSQL(CREATE_TABLE_MSG);


        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    public void saveItem(SQLiteDatabase dbSQLite,String sName,String cals,String fats, String carbos){
        ContentValues newData = new ContentValues();
        newData.put(NAME_HEADER, sName);
        newData.put(CAL_HEADER, cals);
        newData.put(FAT_HEADER, fats);
        newData.put(CARBO_HEADER, carbos);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        String date = strDate.substring(0,10);
        String time = strDate.substring(11,19);

        newData.put(DATE_HEADER,date);
        newData.put(TIME_HEADER,time);
        //Then insert
        dbSQLite.insert(TABLE_NAME,null , newData);

    }

    public void updateItem(SQLiteDatabase dbSQLite,long id,String sName,String cals,String fats, String carbos,String date, String time){
        ContentValues newData = new ContentValues();
        newData.put(NAME_HEADER, sName);
        newData.put(CAL_HEADER, cals);
        newData.put(FAT_HEADER, fats);
        newData.put(CARBO_HEADER, carbos);
        newData.put(DATE_HEADER,date);
        newData.put(TIME_HEADER,time);
        //Then insert
        dbSQLite.update(TABLE_NAME,newData,ID_HEADER + "=" + id,null);

    }

    public ArrayList<String> readItem(SQLiteDatabase dbSQLite){

        cur = dbSQLite.rawQuery("select * from " + TABLE_NAME+" order by "+ DATE_HEADER +" ASC, "+ TIME_HEADER+" ASC;" ,null);
        int column = cur.getCount();
        cur.moveToFirst();
        String foodId, foodName, cals,fats,carbos,foodDate,foodTime;
        ArrayList<String> oneRowData = new ArrayList<>();
        while(!cur.isAfterLast()){
            foodId = cur.getString(cur.getColumnIndex(ID_HEADER));
            foodName = cur.getString(cur.getColumnIndex(NAME_HEADER));
            cals = cur.getString(cur.getColumnIndex(CAL_HEADER));
            fats = cur.getString(cur.getColumnIndex(FAT_HEADER));
            carbos = cur.getString(cur.getColumnIndex(CARBO_HEADER));
            foodDate = cur.getString(cur.getColumnIndex(DATE_HEADER));
            foodTime = cur.getString(cur.getColumnIndex(TIME_HEADER));

            String allData = foodId+"_" +foodName+"_" +cals+"_" +fats+"_" +carbos+"_" +foodDate+"_" +foodTime;

            oneRowData.add(allData);
            cur.moveToNext();
        }

        for(int i = 0; i <cur.getColumnCount();i++){
            System.out.println(cur.getColumnName(i)+ "-----");
        }
        return oneRowData;
    }

    public void deleteItem(SQLiteDatabase db, long id){
        db.delete(foodDatabaseHelper.TABLE_NAME, foodDatabaseHelper.ID_HEADER + "=" + id, null);

        Log.i(ACTIVITY_NAME, "Delete item");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + ", newVersion="  + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onDowngrade, newVersion=" + newVersion + ", oldVersion=" + oldVersion);

    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        Log.i(ACTIVITY_NAME, "onOpen was called");
    }


}
