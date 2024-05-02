package com.example.mad_assignment2.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.EventDBHelper;
import com.example.mad_assignment2.data.VendorDBHelper;
import com.example.mad_assignment2.models.Event;
import com.example.mad_assignment2.models.Vendor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BoothBookingPreviewScreen extends AppCompatActivity {
    private TextView title, description, date, time, participatingVendorList, availableBooth;
    private androidx.constraintlayout.widget.ConstraintLayout backgroundImage;
    private Button bookingBtn, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth_booking_preview_screen);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        participatingVendorList = findViewById(R.id.participatingVendorList);
        availableBooth = findViewById(R.id.available_booth_num);
        backgroundImage = findViewById(R.id.backgroundImage);
        bookingBtn = findViewById(R.id.bookEventBtn);
        backButton = findViewById(R.id.backButton);
        VendorDBHelper vendorDBHelper = new VendorDBHelper(this);
        EventDBHelper eventDbHelper = new EventDBHelper(this);

        Intent intent = getIntent();
        int eventId = intent.getIntExtra("eventid", -1);
        String vendorName = intent.getStringExtra("vendorname");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (eventId > 0) {
            Event event = null;
            try {
                event = eventDbHelper.getEventFromId(eventId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Update the views with event information
            if (event != null) {
                title.setText(event.getTitle());
                description.setText(event.getDescription());
                date.setText(formatDateRange(event.getStartDate(), event.getEndDate()));
                time.setText(event.getTime());
                participatingVendorList.setText( formatVendorList(event.getVendorIdList()));
                availableBooth.setText("Available booth(s) left: " + remainingBooth(event.getVendorIdList()));
                int id = getResources().getIdentifier(event.getBackgroundLogoUrl(), "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                backgroundImage.setBackground(drawable);
            }
        }

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = null;
                try {
                    event = eventDbHelper.getEventFromId(eventId);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(event.getVendorIdList().contains(vendorName)) {
                    showUnsuccessfulDialog("Sorry! You have already booked a booth in this event.");
                }
                else if(remainingBooth(event.getVendorIdList()) == 0) {
                    showUnsuccessfulDialog("Sorry! No more booth available.");
                }
                else  {
                    eventDbHelper.addVendorToEvent(eventId, vendorName);
                    Vendor vendor =  vendorDBHelper.getVendorByName(vendorName);

                    //Assign booth number
                    int boothNumber = event.getVendorIdList().size() + 1;
                    vendor.setBoothLocation(boothNumber);
                    showConfirmationDialog(event, vendor);
                }
            }
        });
    }

    private String formatDateRange(Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);
        String formattedMonthYear = monthYearFormat.format(startDate);
        return formattedStartDate + " - " + formattedEndDate + " " + formattedMonthYear;
    }

    private String formatVendorList(ArrayList<String> vendorList) {
        StringBuilder formattedList = new StringBuilder();
        for (int i = 0; i < vendorList.size(); i++) {
            formattedList.append(vendorList.get(i));
            if (i < vendorList.size() - 1) {
                // Add a comma and space if not the last vendor
                formattedList.append(", ");
            }
        }
        return formattedList.toString();
    }

    private int remainingBooth(ArrayList<String> vendorList) {
        int totalSlot = 15;
        int remainingSlot = totalSlot - vendorList.size();
        return remainingSlot;
    }

    private void showConfirmationDialog(Event event, Vendor vendor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Booking Successful!");
        builder.setMessage("Congratulations! You have booked a booth for:\n\n" + "Event Name: " + event.getTitle() + "\nDate: " + formatDateRange(event.getStartDate(), event.getEndDate()) + "\nBooth Number: " + vendor.getBoothLocation())
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent toHomeScreen = new Intent(BoothBookingPreviewScreen.this, HomeScreen.class);
                        startActivity(toHomeScreen);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showUnsuccessfulDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Booking Failed!");
        builder.setMessage(message)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
