package com.example.mad_assignment2.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_assignment2.MainActivity;
import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.CustomAdapter;
import com.example.mad_assignment2.data.VendorDBHelper;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventDetailScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    Button backButton;

    VendorDBHelper vendorDBHelper;
    ArrayList<String> name, imageUrl,category;
    ArrayList<Integer> buttonId;

    CustomAdapter customAdapter;
    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_screen);

        recyclerView = findViewById(R.id.recyclerView);
        vendorDBHelper = new VendorDBHelper(EventDetailScreen.this);
        name = new ArrayList<>();
        imageUrl = new ArrayList<>();
        category = new ArrayList<>();
        buttonId = new ArrayList<>();

        storeVendorDataInArrays();

        customAdapter = new CustomAdapter(EventDetailScreen.this,name,imageUrl,category,buttonId);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EventDetailScreen.this));

        ImageButton backImageButton = findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the target activity you want to navigate to
                Intent intent = new Intent(EventDetailScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        ImageButton previewImageButton = findViewById(R.id.previewImageButton);
        previewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the target activity you want to navigate to
                Intent intent = new Intent(EventDetailScreen.this, EventPreviewScreen.class);
                intent.putExtra("BUTTON_ID", buttonId);
                startActivity(intent);
            }
        });

    }

    void storeVendorDataInArrays(){
        Cursor cursor = vendorDBHelper.getVendorData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Vendor Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                name.add(cursor.getString(0));
                category.add(cursor.getString(4));
                buttonId.add(cursor.getInt(5));
            }
        }
        Log.d("EventDetailScreen", "name: " + name.toString());
        Log.d("EventDetailScreen", "category: " + category.toString());
        Log.d("EventDetailScreen", "buttonId: " + buttonId.toString());
    }

}


