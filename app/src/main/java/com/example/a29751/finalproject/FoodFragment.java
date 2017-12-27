package com.example.a29751.finalproject;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FoodFragment extends Fragment {

    EditText nameDetails, calDetails, fatDetails,carboDetails,dateDetails,timeDetails;
    public FoodFragment() {
        // Required empty public constructor
    }

    private foodDatabaseHelper foodData;
    private SQLiteDatabase fdbSQLite;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      //Bundle message
        Bundle info = getArguments();
      //  final long id = info.getLong("ID");
        final String fdID=info.getString("FID");
        final String fName = info.getString("FNAME");
        String fdcal = info.getString("FDCAL");
        String fdfat = info.getString("FDFAT");
        String fdcarbo = info.getString("FDCARBO");
        String fddate = info.getString("FDDATE");
        String fdtime = info.getString("FDTIME");

       //layout screen

        nameDetails = (EditText)view.findViewById(R.id.fd_detailname);
        calDetails = (EditText)view.findViewById(R.id.fd_detailcals);
        fatDetails = (EditText)view.findViewById(R.id.fd_detailfats);
        carboDetails = (EditText)view.findViewById(R.id.fd_detailcabos);
        dateDetails = (EditText)view.findViewById(R.id.fd_detailDate);
        timeDetails = (EditText)view.findViewById(R.id.fd_detailTime);

        nameDetails.setText(fName);
        calDetails.setText(fdcal);
        fatDetails.setText(fdfat);
        carboDetails.setText(fdcarbo);
        dateDetails.setText(fddate);
        timeDetails.setText(fdtime);


        foodData = new foodDatabaseHelper(getActivity());
        fdbSQLite = foodData.getWritableDatabase();

        Button btnUpdate = (Button)view.findViewById(R.id.fd_updatebtn);
        Button btnDelete = (Button)view.findViewById(R.id.fd_deletebtn);

        //Update the new item and update item information in the database
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = nameDetails.getText().toString();
                String fdcal = calDetails.getText().toString();
                String fdfat = fatDetails.getText().toString();
                String fdcarbo = carboDetails.getText().toString();
                String fddate = dateDetails.getText().toString();
                String fdtime = timeDetails.getText().toString();

                foodData.updateItem(fdbSQLite,Integer.parseInt(fdID),fName,fdcal,fdfat,fdcarbo,fddate,fdtime);
                getActivity().finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);

            }
        });

        //Delete this item in the database
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                foodData.deleteItem(fdbSQLite,  Integer.parseInt(fdID));
                getActivity().finish();
                Intent intent = getActivity().getIntent();
                startActivity(intent);

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_fragment_layout, container, false);

    }

}
