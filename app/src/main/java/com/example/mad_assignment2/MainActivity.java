package com.example.mad_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mad_assignment2.data.EventDBHelper;
import com.example.mad_assignment2.data.VendorDBHelper;
import com.example.mad_assignment2.models.Event;
import com.example.mad_assignment2.screens.DetailActivityScreen;
import com.example.mad_assignment2.screens.EventDetailScreen;
import com.example.mad_assignment2.screens.VendorBookingCalendarScreen;
import com.example.mad_assignment2.screens.VendorLoginScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventDBHelper eventDb = new EventDBHelper(this);
        VendorDBHelper vendorDb = new VendorDBHelper(this);
//        eventDb.clearDatabase();
//        vendorDb.clearDatabase();
        try {
            eventDb.initializeEventData();
            vendorDb.initializeVendorData();
            Log.d("Debug", "Before logEventData");
            eventDb.logEventData(); // Log the database content
            vendorDb.logVendorData();
            Log.d("Debug", "After logEventData");
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(MainActivity.this, DetailActivityScreen.class);
        MainActivity.this.startActivity(myIntent);
    }
}