package com.example.a29751.finalproject;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodHistoryInfo extends AppCompatActivity {
    private ArrayList<String> flistAllItem = new ArrayList<>();
    private ArrayList<String> fIdList = new ArrayList<>();
    private ArrayList<String> fnameList = new ArrayList<>();
    private ArrayList<String> fcalsList = new ArrayList<>();
    private ArrayList<String> ffatsList = new ArrayList<>();
    private ArrayList<String> fCarboList = new ArrayList<>();
    private ArrayList<String> fDateList = new ArrayList<>();
    private ArrayList<String> fTimeList = new ArrayList<>();

    private String[] foodSplitString;
    private foodDatabaseHelper foodData;
    private SQLiteDatabase fdbSQLite;
    private final int RQCODE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_history_info);


        ListView listView = (ListView)findViewById(R.id.foodList);
        final FoodListAdapter foodlistAdapter = new FoodListAdapter(this);
        listView.setAdapter(foodlistAdapter);

        foodData = new foodDatabaseHelper(this);
        fdbSQLite = foodData.getWritableDatabase();

        flistAllItem = foodData.readItem(fdbSQLite);

        for(int i=0;i<flistAllItem.size();i++){
            foodSplitString= flistAllItem.get(i).split("_");
            fIdList.add(foodSplitString[0]);
            fnameList.add(foodSplitString[1]);
            fcalsList.add(foodSplitString[2]);
            ffatsList.add(foodSplitString[3]);
            fCarboList.add(foodSplitString[4]);
            fDateList.add(foodSplitString[5]);
            fTimeList.add(foodSplitString[6]);
        }
        foodlistAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intent to a fragment detail activity

                Toast t = Toast.makeText(FoodHistoryInfo.this,"Click on "+foodlistAdapter.getItem(position) , Toast.LENGTH_SHORT);
                t.show();

                Bundle args = new Bundle();
           //     args.putLong("ID",id);
                args.putString("FID",fIdList.get(position));
                args.putString("FNAME",fnameList.get(position));
                String fdcal = fcalsList.get(position);
                args.putString("FDCAL",fdcal);
                String fdfat = ffatsList.get(position);
                args.putString("FDFAT",fdfat);
                String fdcarbo = fCarboList.get(position);
                args.putString("FDCARBO",fdcarbo);
                String fddate = fDateList.get(position);
                args.putString("FDDATE",fddate);
                String fdtime = fTimeList.get(position);
                args.putString("FDTIME",fdtime);

                FoodFragment newFragment= new FoodFragment();

                newFragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.foodFrame, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();


            }
        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class FoodListAdapter extends ArrayAdapter<String> {
        public FoodListAdapter(Context ctx){
            super(ctx,0);
        }

        public int getCount(){
            return fnameList.size();
        }

        public String getItem(int position){
            return fnameList.get(position);
        }

        public View getView(int position, View converView, ViewGroup parent){
            LayoutInflater inflater = FoodHistoryInfo.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.activity_foodname_listview, null);
            TextView foodName = (TextView)result.findViewById(R.id.food_nametext);
            //    result.setBackgroundResource(R.color.colorPrimary);

            foodName.setText(getItem(position));
            return result;
        }

      /*  public long getItemId(int position){
            cur.moveToPosition(position);
            return cur.getLong(cur.getColumnIndex(ChatDatabaseHelper.ID_HEADER));
        }*/
    }
}



