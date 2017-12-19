package com.example.a29751.finalproject;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FoodSummary extends AppCompatActivity {

    ProgressBar pb;
    private foodDatabaseHelper foodData;
    private SQLiteDatabase fdbSQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);
        foodData = new foodDatabaseHelper(this);
        fdbSQLite = foodData.getWritableDatabase();
        pb = (ProgressBar)findViewById(R.id.progressBar);

        foodCalculate fcc= new foodCalculate();
        fcc.execute();
    }




    private class foodCalculate extends AsyncTask<String,Integer, String> {


        @Override
        protected String doInBackground(String... strings) {//...means an array
            HttpURLConnection conn = null;
            URL url = null;
            InputStream input = null;
            try {

                //foodData.readItem(fdbSQLite);
                publishProgress(25);
                SystemClock.sleep(300);

                publishProgress(50);
                SystemClock.sleep(300);

                publishProgress(75);
                SystemClock.sleep(300);


            } catch (Exception ex){
                Log.i("IOException: " ,ex.getMessage());
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {


            pb.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);


            pb.setProgress(values[0]);
        }


    }





}
