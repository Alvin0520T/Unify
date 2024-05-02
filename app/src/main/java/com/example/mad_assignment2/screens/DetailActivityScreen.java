package com.example.mad_assignment2.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.VendorDBHelper;
import com.example.mad_assignment2.models.Vendor;

public class DetailActivityScreen extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView averageRatingView;
    private int buttonId;
    private VendorDBHelper vendorDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        vendorDBHelper = new VendorDBHelper(this);

        // Get the button ID from the intent
        buttonId = getIntent().getIntExtra("BUTTON_ID", 0);

        // Initialize views
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ratingBar = findViewById(R.id.ratingBar);
        averageRatingView = findViewById(R.id.averageRatingTextView);

        setDetailContent(buttonId);

        // Set the RatingBar change listener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    // Update rating in database
                    Vendor vendor = vendorDBHelper.getVendorByButtonId(buttonId);
                    if (vendor != null) {
                        vendor.setRating(rating);
                        vendorDBHelper.updateVendorRating(vendor);

                        // Update the average rating view
                        averageRatingView.setText(String.format("Average Rating: %.2f", rating));
                    }
                }
            }
        });

        ImageButton backButton = findViewById(R.id.backButton01);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivityScreen.this, EventDetailScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDetailContent(int buttonId) {
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        Vendor vendor = vendorDBHelper.getVendorByButtonId(buttonId);

        if (vendor != null) {
            // Set vendor details
            imageView1.setImageResource(getResources().getIdentifier(vendor.getBoothDirectory(), "@drawable/dic01.png", getPackageName()));
            imageView2.setImageResource(getResources().getIdentifier(vendor.getBoothDirectory(), "@drawable/dic01.png", getPackageName()));
            titleTextView.setText(vendor.getName());
            descriptionTextView.setText(vendor.getDescription());

            // Set rating and average rating view
            ratingBar.setRating((float) vendor.getRating());
            averageRatingView.setText(String.format("Average Rating: %.2f", vendor.getRating()));
        }
    }

    @Override
    protected void onDestroy() {
        vendorDBHelper.close();
        super.onDestroy();
    }
}
