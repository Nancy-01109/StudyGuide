package com.example.studyguide;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarkerViewHolder extends RecyclerView.ViewHolder {
    public TextView name,gps,desription;
    public ImageView deleteContact;
    public  ImageView editContact;

    public MarkerViewHolder(@NonNull View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.CityName);
        gps=(TextView)itemView.findViewById(R.id.CityGps);
        desription=(TextView)itemView.findViewById(R.id.Citydesc);
        deleteContact = (ImageView)itemView.findViewById(R.id.deleteBut);
        editContact = (ImageView)itemView.findViewById(R.id.editBut);
    }
}
