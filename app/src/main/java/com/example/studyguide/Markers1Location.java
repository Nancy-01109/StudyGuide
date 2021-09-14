package com.example.studyguide;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Markers1Location extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MyHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers_1);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.frame);

        RecyclerView contactView = (RecyclerView)findViewById(R.id.myLocationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        mDatabase = new MyHelper(this);
        ArrayList<Markers> allMarkers = mDatabase.listMarkers();

        if(allMarkers.size() > 0){
            contactView.setVisibility(View.VISIBLE);
            MarkerAdapter mAdapter = new MarkerAdapter(this, allMarkers);
            contactView.setAdapter(mAdapter);

        }else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        Button fab = (Button) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_locations, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enterName);
        final EditText gpsField = (EditText)subView.findViewById(R.id.enterGps);
        final  EditText descrField=(EditText) subView.findViewById(R.id.enterDesc);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new LOCATION");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD LOCATION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String gps = gpsField.getText().toString();
                final String desr = descrField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Markers1Location.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Markers newMarker = new Markers(name, gps,desr);
                    mDatabase.addMarkers(newMarker);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Markers1Location.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

}


