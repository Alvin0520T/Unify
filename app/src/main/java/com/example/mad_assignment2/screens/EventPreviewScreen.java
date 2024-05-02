package com.example.mad_assignment2.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.*;

import android.os.Bundle;

import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.EventDBHelper;
import com.example.mad_assignment2.data.VendorDBHelper;

public class EventPreviewScreen extends AppCompatActivity {

    private Button backButton, eventDetailButton;
    private TextView eventTitleText, eventDescriptionText,eventDateText,eventTimeText;
    private EventDBHelper eventDBHelper;

    private View event_preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_preview_screen);

        backButton = findViewById(R.id.backButton);
        eventTitleText = findViewById(R.id.eventTitleText);
        eventDescriptionText = findViewById(R.id.eventDescriptionText);
        eventDateText = findViewById(R.id.eventDateText);
        eventTimeText = findViewById(R.id.eventTimeText);
        eventDetailButton = findViewById(R.id.eventDetailButton);
        eventDBHelper = new EventDBHelper(this);
        event_preview = findViewById(R.id.event_preview);

        Intent intent = getIntent();
        int event_id = intent.getIntExtra("event_id",-1);
        eventDBHelper.logEventData();
        System.out.println("" + event_id);

        eventTimeText.setText(eventDBHelper.getEventTitle(event_id));
        eventDescriptionText.setText(eventDBHelper.getEventDescription(event_id));
        eventDateText.setText(eventDBHelper.getEventDate(event_id));
        eventTimeText.setText(eventDBHelper.getEventTime(event_id));

        //change background
        int drawable_id = getResources().getIdentifier("home_page_background","drawable",getPackageName());
        Drawable backgroundImg = getResources().getDrawable(drawable_id);
        event_preview.setBackground(backgroundImg);


        System.out.println(eventDescriptionText.getText());
        System.out.println(eventDBHelper.getEventDescription(event_id));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventPreviewScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        eventDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EventPreviewScreen.this, .class);
//                startActivity(intent);
            }
        });




    }
}