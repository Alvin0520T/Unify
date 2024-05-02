package com.example.mad_assignment2.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad_assignment2.R;

public class HomeScreen extends AppCompatActivity {


    private ImageButton drawerButton;
    private TextView dateTextView;
    private  EditText searchEvent;
    private ImageView ongoingEventCard;
    private ImageView upcomingEventCard;
    private ViewStub drawerStub;
    private View drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawerButton = findViewById(R.id.drawerButton);
        dateTextView = findViewById(R.id.dateTextView);
        searchEvent = findViewById(R.id.searchEvent);
        ongoingEventCard = findViewById(R.id.ongoingEventCard);
        upcomingEventCard = findViewById(R.id.upcomingEventCard);


        ViewStub drawerStub = findViewById(R.id.drawerStub);
        View drawerLayout = drawerStub.inflate();
        drawerLayout.setVisibility(View.GONE);


        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, HomeScreenDrawer.class);
                startActivity(intent);
            }
        });


//        drawerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.getVisibility() == View.VISIBLE) {
//                    drawerLayout.setVisibility(View.GONE); // Hide the drawer
//                } else {
//                    drawerLayout.setVisibility(View.VISIBLE);                }
//            }
//        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, CalendarVisitorScreen.class);
                startActivity(intent);
            }
        });

        //searchEvent function Ignored first
        ongoingEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, EventPreviewScreen.class);
                intent.putExtra("event_id",1);
                startActivity(intent);
            }
        });

        upcomingEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, EventPreviewScreen.class);
                intent.putExtra("event_id",2);
                startActivity(intent);
            }
        });


    }
}