package com.example.studyguide;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Markers> listMarkers;
    private ArrayList<Markers> mArrayList;

    private MyHelper mDatabase;

    public MarkerAdapter(Context context, ArrayList<Markers> listMarkers) {
        this.context = context;
        this.listMarkers = listMarkers;
        this.mArrayList=listMarkers;
        mDatabase=new MyHelper(context);
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_layout, parent, false);
        return new MarkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, int position) {
        final Markers markers = listMarkers.get(position);

        holder.name.setText(markers.getName());
        holder.gps.setText(markers.getGps());
        holder.desription.setText(markers.getDesription());

        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(markers);
            }
        });

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteContact(markers.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listMarkers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listMarkers = mArrayList;
                } else {

                    ArrayList<Markers> filteredList = new ArrayList<>();

                    for (Markers markers : mArrayList) {

                        if (markers.getName().toLowerCase().contains(charString)) {

                            filteredList.add(markers);
                        }
                    }

                    listMarkers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listMarkers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listMarkers = (ArrayList<Markers>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void editTaskDialog(final Markers markers){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_locations, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enterName);
        final EditText gpsField = (EditText)subView.findViewById(R.id.enterGps);
        final  EditText descrField=(EditText)subView.findViewById(R.id.enterDesc);

        if(markers != null){
            nameField.setText(markers.getName());
            gpsField.setText(String.valueOf(markers.getGps()));
            descrField.setText(markers.getDesription());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit LOCATION");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT LOCATION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String gps = gpsField.getText().toString();
                final String description=descrField.getText().toString();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateMarkers(new Markers(markers.getId(), name, gps,description));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
