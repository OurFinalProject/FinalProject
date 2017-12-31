package com.example.a29751.finalproject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Autoshowinfo extends Fragment {

    private AutoDatabaseHelper dbH;
    private SQLiteDatabase db;
    EditText fEd,pEd,kEd,dEd;

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {

        final Bundle info = getArguments();

        final String autoID = info.getString("AutoID");
        String autoF = info.getString("Fuel");
        String autoP = info.getString("Price");
        String autoK = info.getString("Kilo");
        String autoD = info.getString("Date");

        fEd = (EditText)view.findViewById(R.id.editF);
        pEd = (EditText)view.findViewById(R.id.editP);
        kEd = (EditText)view.findViewById(R.id.editK);
        dEd = (EditText)view.findViewById(R.id.editD);

        fEd.setText(autoF);
        pEd.setText(autoP);
        kEd.setText(autoK);
        dEd.setText(autoD);

        dbH= new AutoDatabaseHelper(getActivity());
        db = dbH.getWritableDatabase();

        Button editBt = (Button)view.findViewById(R.id.editBt);
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dbF = fEd.getText().toString();
                String dbP = pEd.getText().toString();
                String dbK = kEd.getText().toString();
                String dbD = dEd.getText().toString();

                /*ContentValues contentVn = new ContentValues();
                contentVn.put(dbH.KEY_FUEL, autoF);
                contentVn.put(dbH.KEY_PRICE, autoP);
                contentVn.put(dbH.KEY_KILO, autoK);
                contentVn.put(dbH.KEY_DATE,  autoD);*/

                /*long id = info.getLong("AutoID");*/


                dbH.eidtInfo(db,Integer.parseInt(autoID),dbF,dbP,dbK,dbD);
                getActivity().finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_autoshowinfo, parentViewGroup, false);
        return view;
    }


}