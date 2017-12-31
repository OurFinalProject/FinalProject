package com.example.a29751.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

public class AutoSummaryInfo extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView priceAVG,lastmonthF,permonth;
    private AutoDatabaseHelper dbH;
    private SQLiteDatabase db;
    private LinearLayout linearLayout;
    private String pAVG,perF;
    private double lastF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_summary_info);

        priceAVG = (TextView)findViewById(R.id.avgPnum);
        lastmonthF = (TextView)findViewById(R.id.lastFnum);
        permonth = (TextView)findViewById(R.id.permonthNum);

        dbH = new AutoDatabaseHelper(this);
        db = dbH.getWritableDatabase();

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        /*progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);*/

        autoSummary autoS = new autoSummary();
        autoS.execute();


    }

    private class autoSummary extends AsyncTask<String,Integer,String>{


        @Override
        protected String doInBackground(String... strings) {

            Cursor cursorSum = db.query(true,dbH.TABLE_NAME,new String[] {dbH.KEY_FUEL,dbH.KEY_PRICE},null,null,dbH.KEY_DATE,null,null,null);
            int num = cursorSum.getCount();

            cursorSum = db.rawQuery("select * from " + dbH.TABLE_NAME,null);
            double lastsumP = 0;
            double sumF = 0;


            /*Calendar date = Calendar.getInstance();
            Calendar date2 = Calendar.getInstance();
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
            date.add(Calendar.MONTH,-1);
            date2.add(Calendar.YEAR,-1);
            String lastMonth = dateF.format(date.getTime());
            String perM = dateF.format(date2.getTime());*/

            cursorSum.moveToFirst();
            while(!cursorSum.isAfterLast()){



                lastF += Double.valueOf(cursorSum.getString(cursorSum.getColumnIndex(dbH.KEY_FUEL)));
                lastsumP += Double.valueOf(cursorSum.getString(cursorSum.getColumnIndex(dbH.KEY_PRICE)));
                sumF += Double.valueOf(cursorSum.getString(cursorSum.getColumnIndex(dbH.KEY_FUEL)));
                cursorSum.moveToNext();
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            pAVG = decimalFormat.format(lastsumP/num);
            perF = decimalFormat.format(sumF/12);

            publishProgress(25);
            SystemClock.sleep(500);

            publishProgress(50);
            SystemClock.sleep(500);

            publishProgress(75);
            SystemClock.sleep(500);

            publishProgress(100);
            SystemClock.sleep(500);



            return null;
        }

        @Override
        protected void onPostExecute(String string) {

            priceAVG.setText(pAVG);
            lastmonthF.setText(String.valueOf(lastF));
            permonth.setText(perF);

            progressBar.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);


        }


    }
}
