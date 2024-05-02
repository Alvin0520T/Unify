package com.example.mad_assignment2.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_assignment2.R;


public class HomeScreenDrawer extends AppCompatActivity{

    private Button homeButton;
    private Button calendarButton;
    private Button aboutButton;
    private Button bookingButton;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_drawer);

        homeButton = findViewById(R.id.homeButton);
        calendarButton = findViewById(R.id.calendarButton);
        aboutButton = findViewById(R.id.aboutButton);
        bookingButton = findViewById(R.id.bookingButton);
        drawerView = findViewById(R.id.drawerView);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenDrawer.this, CalendarVisitorScreen.class);
                startActivity(intent);
            }
        });

        //currently no about, nvm dont use it : )
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreenDrawer.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenDrawer.this, VendorLoginScreen.class);
                startActivity(intent);
            }
        });


    }
}
