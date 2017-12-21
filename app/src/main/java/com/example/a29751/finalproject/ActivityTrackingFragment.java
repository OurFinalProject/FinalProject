package com.example.a29751.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sowha on 2017-12-13.
 */

public class ActivityTrackingFragment extends Fragment {
    Button deleteButton;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tracking_fragment, container, false);
        activityTrackingDatabaseHelper dbHelper = new activityTrackingDatabaseHelper(getActivity()) ;
        db = dbHelper.getWritableDatabase();

        Bundle bundle = this.getArguments();
        final String type = bundle.getString("type");
        final int minutes = bundle.getInt("minutes");
        final String commnets = bundle.getString("comments");
        final String time = bundle.getString("time");
        final String id = bundle.getString("id");
        TextView typeView = view.findViewById(R.id.fragmentType);
        typeView.setText(""+type);
        TextView minView = view.findViewById(R.id.fragmentMin);
        minView.setText("" + minutes + " minutes");
        TextView commView = view.findViewById(R.id.fragmentComm);
        commView.setText(""+commnets);
        TextView timeView = view.findViewById(R.id.fragmentTime);
        timeView.setText(""+time);


        deleteButton=view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                    db.delete(activityTrackingDatabaseHelper.TABLE_NAME, activityTrackingDatabaseHelper.KEY_ID + "=" + id, null);
                    getActivity().finish();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
            }
        });

        return view;
    }
}
