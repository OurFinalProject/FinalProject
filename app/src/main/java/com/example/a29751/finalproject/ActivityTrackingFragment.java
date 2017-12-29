package com.example.a29751.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.a29751.finalproject.activityTrackingDatabaseHelper.TABLE_NAME;

/**
 * Created by sowha on 2017-12-13.
 */

public class ActivityTrackingFragment extends Fragment {
    Button deleteButton;
    SQLiteDatabase db;
    String ACid;
    ArrayList<ActivityTrackingFragment.Activites> messages = new ArrayList<ActivityTrackingFragment.Activites>();
    final String ACTIVITY_NAME = "ActivityFragment";
    ListView listView;
    Activites act;
    EditText typeView;
    EditText minView;
    EditText commView;
    TextView timeView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_tracking_fragment, container, false);

        typeView = view.findViewById(R.id.fragmentType);
        minView = view.findViewById(R.id.fragmentMin);
        commView = view.findViewById(R.id.fragmentComm);
        timeView = view.findViewById(R.id.fragmentTime);

        activityTrackingDatabaseHelper dbHelper = new activityTrackingDatabaseHelper(getActivity()) ;
        db = dbHelper.getWritableDatabase();
        listView = view.findViewById(R.id.listView);

        Cursor c = db.rawQuery("select * from " + TABLE_NAME,null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE " + c.getString(c.getColumnIndex(dbHelper.KEY_TYPE)));
            ActivityTrackingFragment.Activites act = new ActivityTrackingFragment.Activites(c.getString(c.getColumnIndex(dbHelper.KEY_ID)),
                    c.getString(c.getColumnIndex(dbHelper.KEY_TYPE)),
                    c.getInt(c.getColumnIndex(dbHelper.KEY_TIME)),
                    c.getString(c.getColumnIndex(dbHelper.KEY_COMMENTS)));
            messages.add(act);
            c.moveToNext();
        }

        final ActivityTrackingAdapter adapter =  new ActivityTrackingAdapter(getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                act = messages.get(position);

                String type = act.getType();
                int minutes = act.getMinutes();
                String commnets = act.getComments();
                String time = act.getTime().toString();
                ACid = act.getID().toString();

                typeView.setText(" " + type);
                minView.setText(" " + minutes + " minutes");
                commView.setText(" " + commnets);
                timeView.setText(" " + time);

            }
        });



        deleteButton=view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                    db.delete(activityTrackingDatabaseHelper.TABLE_NAME, activityTrackingDatabaseHelper.KEY_ID + "=" + ACid, null);
                    getActivity().finish();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
            }
        });

        return view;
    }

    private class ActivityTrackingAdapter extends ArrayAdapter<String> {
        public ActivityTrackingAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return messages.size();
        }

        public String getItem (int position) {
            return messages.get(position).getType();
        }

        public Activites getActivity (int position) {
            return messages.get(position);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public View getView (int position, View converView, ViewGroup parent){

            LayoutInflater inflater = ActivityTrackingFragment.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.activity_tracking_listview, null);

            TextView message = (TextView)result.findViewById(R.id.textView);
            message.setText( getItem(position)); // get the string at position
            return result;

        }
    }

    private class Activites {
        String type;
        int minutes;
        String comments;
        Date currentTime;
        String id;

        public Activites(String i, String t, int m, String c){
            id=i;
            type=t;
            minutes=m;
            comments=c;
            currentTime = Calendar.getInstance().getTime();
        }

        public String getID(){
            return  id;
        }

        public String getType(){
            return  type;
        }

        public int getMinutes(){
            return minutes;
        }

        public String getComments(){
            return comments;
        }

        public Date getTime(){
            return currentTime;
        }

        public String toString(){
            String details= "Activity: " + type
                    + ", spent " + minutes + " minutes."
                    + " \nComments: " + comments
                    + " \nRecord time: " + currentTime;
            return details;
        }
    }
}
