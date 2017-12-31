package com.example.a29751.finalproject;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AutomobileListview extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AutomobileListview";
    public ArrayList<String> info = new ArrayList<>();
    public AutoDatabaseHelper dbH;
    public SQLiteDatabase db;
    public ArrayList<String> ID_ArrayList = new ArrayList<>();
    public ArrayList<String> F_ArrayList = new ArrayList<>();
    public ArrayList<String> P_ArrayList = new ArrayList<>();
    public ArrayList<String> K_ArrayList = new ArrayList<>();
    public ArrayList<String> D_ArrayList = new ArrayList<>();
    private String[] auto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_listview);

        final ListView lv = (ListView)findViewById(R.id.lv);

        final AutoAdapter messageAdapter = new AutoAdapter( this );
        lv.setAdapter (messageAdapter);

        dbH = new AutoDatabaseHelper(this);
        db = dbH.getWritableDatabase();

        final Cursor cursor = db.rawQuery(" select * from " + dbH.TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String message = cursor.getString(cursor.getColumnIndex(dbH.KEY_DATE));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message );
            info.add(message);
            messageAdapter.notifyDataSetChanged();
            cursor.moveToNext();

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursorView = db.rawQuery(" select * from " + dbH.TABLE_NAME,null);
                cursorView.moveToFirst();
                String autoid,autof, autop, autok, autod;
                ArrayList<String> oneData = new ArrayList<>();
                while(!cursorView.isAfterLast()) {

                    autoid = cursorView.getString(cursorView.getColumnIndex(dbH.KEY_ID));
                    autof = cursorView.getString(cursorView.getColumnIndex(dbH.KEY_FUEL));
                    autop = cursorView.getString(cursorView.getColumnIndex(dbH.KEY_PRICE));
                    autok = cursorView.getString(cursorView.getColumnIndex(dbH.KEY_KILO));
                    autod = cursorView.getString(cursorView.getColumnIndex(dbH.KEY_DATE));

                    String allData = autoid+"_"+autof+"_"+autop+"_"+autok+"_"+autod;
                    oneData.add(allData);
                    cursorView.moveToNext();
                }

                for(int i = 0; i < oneData.size(); i++){
                    auto = oneData.get(i).split("_");
                    ID_ArrayList.add(auto[0]);
                    F_ArrayList.add(auto[1]);
                    P_ArrayList.add(auto[2]);
                    K_ArrayList.add(auto[3]);
                    D_ArrayList.add(auto[4]);
                }
                messageAdapter.notifyDataSetChanged();


                Bundle args = new Bundle();
                /*String message = messageAdapter.getItem(position);*/
                args.putString("AutoID",ID_ArrayList.get(position));
                args.putString("Fuel", F_ArrayList.get(position));
                args.putString("Price", P_ArrayList.get(position));
                args.putString("Kilo", K_ArrayList.get(position));
                args.putString("Date", D_ArrayList.get(position));

                Autoshowinfo newFragment = new Autoshowinfo();
                newFragment.setArguments(args);

                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.autoframe, newFragment);
                tra.addToBackStack(null);

                tra.commit();
                /*Intent intent = new Intent(AutomobileListview.this, Autoshowinfo.class);
                intent.putExtras(args);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked view.");*/
            }
        });

    }

    private class AutoAdapter extends ArrayAdapter<String> {

        public AutoAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return info.size();
        }

        public String getItem(int position) {
            return info.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = AutomobileListview.this.getLayoutInflater();

            View result = null;
            TextView message;
            result = inflater.inflate(R.layout.timelist, null);
            message = (TextView) result.findViewById(R.id.timeview);


            message.setText(getItem(position)); // get the string at position
            return result;

        }
    }

}
