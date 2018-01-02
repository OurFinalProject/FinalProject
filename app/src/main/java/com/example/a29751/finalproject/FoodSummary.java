package com.example.a29751.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.a29751.finalproject.foodDatabaseHelper.TIME_HEADER;

public class FoodSummary extends AppCompatActivity {

    private ProgressBar pb;
    private LinearLayout lo;
    private foodDatabaseHelper foodData;
    private SQLiteDatabase fdbSQLite;
    private String lastCals,lastFats,lastCarbos,avgCals,avgFats,avgCarbos;
    TextView tx_lastCals,tx_lastFats,tx_lastCarbos,txCals,txFats,txCarbos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);
        foodData = new foodDatabaseHelper(this);
        fdbSQLite = foodData.getWritableDatabase();
        pb = (ProgressBar)findViewById(R.id.progressBar);
        lo = (LinearLayout)findViewById(R.id.layout_back);
        lo.setVisibility(View.INVISIBLE);

        tx_lastCals = (TextView) findViewById(R.id.fd_lastCal);
        tx_lastFats = (TextView) findViewById(R.id.fd_lastFat);
        tx_lastCarbos = (TextView) findViewById(R.id.fd_lastCarbo);
        txCals = (TextView) findViewById(R.id.fd_sCal);
        txFats = (TextView) findViewById(R.id.fd_sFat);
        txCarbos = (TextView) findViewById(R.id.fd_sCarbo);

        foodCalculate fcc= new foodCalculate();
        fcc.execute();
    }




    private class foodCalculate extends AsyncTask<String,Integer, String> {

        @Override
        protected String doInBackground(String... strings) {//...means an array

            try {

               // foodData.readItem(fdbSQLite);
           //     Cursor cur = fdbSQLite.rawQuery("select * from " + foodData.TABLE_NAME+" order by "+ DATE_HEADER +" ASC, "+ TIME_HEADER+" ASC;" ,null);
                Cursor cur = fdbSQLite.query(true, foodData.TABLE_NAME, new String[] { foodData.CAL_HEADER ,foodData.FAT_HEADER, foodData.CARBO_HEADER }, null, null, foodData.DATE_HEADER, null, null, null);
                int column = cur.getCount();

                cur = fdbSQLite.rawQuery("select * from " + foodData.TABLE_NAME ,null);
                int total = cur.getCount();

                double cals=0,fats=0,carbos=0,lcals=0,lfats=0,lcarbos=0;
                Calendar cal = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                cal.add(Calendar.DATE, -1);
                String lastdate=dateFormat.format(cal.getTime());

                cur.moveToFirst();
                while(!cur.isAfterLast()){
                    cals += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.CAL_HEADER)));
                    fats += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.FAT_HEADER)));
                    carbos += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.CARBO_HEADER)));
                    String date = cur.getString(cur.getColumnIndex(foodData.DATE_HEADER));
                    if (lastdate.equalsIgnoreCase(date) )
                    {
                        lcals += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.CAL_HEADER)));
                        lfats += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.FAT_HEADER)));
                        lcarbos += Double.valueOf(cur.getString(cur.getColumnIndex(foodData.CARBO_HEADER)));
                    }
                    cur.moveToNext();
                }

                DecimalFormat df = new DecimalFormat("#.##");
                avgCals = df.format(cals/column);
                avgFats = df.format(fats/column);
                avgCarbos = df.format(carbos/column);

                lastCals = df.format(lcals);
                lastFats = df.format(lfats);
                lastCarbos = df.format(lcarbos);

                publishProgress(25);
                SystemClock.sleep(500);

                publishProgress(50);
                SystemClock.sleep(500);

                publishProgress(75);
                SystemClock.sleep(500);

                publishProgress(100);
                SystemClock.sleep(500);


            } catch (Exception ex){
                Log.i("IOException: " ,ex.getMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            tx_lastCals.setText(lastCals);
            tx_lastFats.setText(lastFats);
            tx_lastCarbos.setText(lastCarbos);

            txCals.setText(avgCals);
            txFats.setText(avgFats);
            txCarbos.setText(avgCarbos);

            pb.setVisibility(View.INVISIBLE);
            lo.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);


        }


    }





}
