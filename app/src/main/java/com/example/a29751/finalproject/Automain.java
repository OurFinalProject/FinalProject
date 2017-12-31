package com.example.a29751.finalproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Automain extends AppCompatActivity {

    private String SnackText = "Add new information";

    protected static final String ACTIVITY_NAME = "Automomain";

    /*private EditText edF = (EditText)findViewById(R.id.editText);
    private EditText edP = (EditText)findViewById(R.id.editText2);
    private EditText edK = (EditText)findViewById(R.id.editText3);*/
    private TextView edF,edP,edK;
    public AutoDatabaseHelper dbH;
    public SQLiteDatabase db;
    public ArrayList<String> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btSave = (Button)findViewById(R.id.buttonSave);

        edF = (TextView)findViewById(R.id.editText);
        edP = (TextView)findViewById(R.id.editText2);
        edK = (TextView)findViewById(R.id.editText3);


        dbH = new AutoDatabaseHelper(this);
        db = dbH.getWritableDatabase();



        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentV = new ContentValues();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat saveDate = new SimpleDateFormat("yyyy-MM-dd");
                String sDate = saveDate.format(c.getTime());

                String fDate = edF.getText().toString();
                String pDate = edP.getText().toString();
                String kDate = edK.getText().toString();

                info.add(sDate);
                info.add(fDate);
                info.add(pDate);
                info.add(kDate);

                contentV.put(dbH.KEY_FUEL, fDate);
                contentV.put(dbH.KEY_PRICE, pDate);
                contentV.put(dbH.KEY_KILO, kDate);
                contentV.put(dbH.KEY_DATE, sDate);
                db.insert(dbH.TABLE_NAME, null, contentV);


                Intent intent = new Intent(Automain.this, AutomobileListview.class);
                startActivity(intent);
            }
        });
        Log.i(ACTIVITY_NAME, "Returned to Automoblie");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.automenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case R.id.auto_action1:
                Snackbar.make(findViewById(R.id.auto_action1), SnackText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("Toolbar", SnackText);
                break;


            case R.id.auto_action2:

                Intent intentProgress = new Intent(Automain.this,AutoSummaryInfo.class);
                startActivity(intentProgress);
                break;



            case R.id.auto_action3:

                AlertDialog.Builder obuilder = new AlertDialog.Builder(this);

                LayoutInflater li= getLayoutInflater();
                LinearLayout root = (LinearLayout)li.inflate(R.layout.auto_dialog_signin, null);
                final EditText et = (EditText)root.findViewById(R.id.dialogmessage);
                obuilder.setView(root);


                obuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SnackText = et.getText().toString();
                    }
                })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                obuilder.create().show();

                break;

            case R.id.auto_list:

                Intent intentList = new Intent(Automain.this,AutomobileListview.class);
                startActivity(intentList);
                break;

            case R.id.auto_acAbout:
                AlertDialog.Builder about = new AlertDialog.Builder(this);
                about.setTitle("Support").setMessage("Version 1 by Fanyu Hao").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                about.create().show();
                break;


        }


        return super.onOptionsItemSelected(item);
    }

}
