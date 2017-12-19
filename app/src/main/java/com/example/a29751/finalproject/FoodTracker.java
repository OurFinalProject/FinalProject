package com.example.a29751.finalproject;

import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FoodTracker extends AppCompatActivity {

    private TextView fName,cals,fats,carbos;
    public foodDatabaseHelper foodData;
    public SQLiteDatabase fdbSQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fd_toolbar);
        setSupportActionBar(toolbar);

        fName = (TextView)findViewById(R.id.foodName);
        cals = (TextView)findViewById(R.id.fd_cals);
        fats = (TextView)findViewById(R.id.fd_fats);
        carbos = (TextView)findViewById(R.id.fd_cbs);
        Button btnSave = (Button)findViewById(R.id.fd_saveBtn);

        foodData = new foodDatabaseHelper(this);
        fdbSQLite = foodData.getWritableDatabase();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String foodName = fName.getText().toString();

                foodData.saveItem(fdbSQLite,foodName,cals.getText().toString(),
                        fats.getText().toString(),carbos.getText().toString());

                Intent intent = new Intent(FoodTracker.this, FoodHistoryInfo.class);

                startActivity(intent);
            }
        });

 /*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fd_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.food_toolbar_menue,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        String snackText = "Add New Food";

        int id = mi.getItemId();
        switch(id){
            case R.id.fd_action1:

               /* FoodAddfragment addFragment= new FoodAddfragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fd_mainframe, addFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
*/


           Snackbar.make(findViewById(R.id.fd_action1), snackText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("FoodTracker",snackText);
                break;
            case R.id.fd_action2:
                Intent intent = new Intent(FoodTracker.this, FoodHistoryInfo.class);
                 startActivity(intent);

                break;
            case R.id.fd_action3:
                Intent intent2 = new Intent(FoodTracker.this, FoodSummary.class);
                startActivity(intent2);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.fooditem)
                                .setContentTitle("My notification")

                                .setContentText("You clicked FoodSummary");
                Intent resultIntent = new Intent(this, FragmentActivity.class);
                PendingIntent resultPendingIntent = PendingIntent.getActivity( this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);

                int mNotificationId = 6548;
                NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());



                break;
            case R.id.fd_acAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.fd_dialogTitle)
                        .setIcon(R.drawable.helpinfo)
                        .setMessage("Version 1.0, by Liangliang.\n" +
                                "There are two main functions in this activity, you can click icon on the top.")

                        .setNegativeButton(R.string.fd_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.create().show();

                break;
            default:
                break;
        }
        return true;
    }


}
