package com.example.mad_assignment2.data;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_assignment2.R;
import com.example.mad_assignment2.screens.EventPreviewScreen;
import com.example.mad_assignment2.screens.HomeScreen;
import com.example.mad_assignment2.screens.VendorDetailsScreen;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList name, imageUrl,category;

    private ArrayList<Integer> buttonIds;

    int position;

    public CustomAdapter(Context context,
                         ArrayList name,
                         ArrayList imageUrl,
                         ArrayList category,
                         ArrayList<Integer> buttonIds){

        this.context = context;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.buttonIds = buttonIds;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.vendor_name_txt.setText(String.valueOf(name.get(position)));
        holder.imageUrl_txt.setText(String.valueOf(category.get(position)));
        final int buttonId = buttonIds.get(position);

        holder.eventDetailsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VendorDetailsScreen.class);
                intent.putExtra("BUTTON_ID", buttonId);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vendor_name_txt, imageUrl_txt;
        LinearLayout eventDetailsLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            vendor_name_txt = itemView.findViewById(R.id.vendor_name_txt);
            imageUrl_txt = itemView.findViewById(R.id.imageUrl_txt);
            eventDetailsLayout = itemView.findViewById(R.id.eventDetailsLayout);
        }
    }

}
