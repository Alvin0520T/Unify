package com.example.mad_assignment2.data;

import com.example.mad_assignment2.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class EventInitData {

    public static ArrayList<Event> initializeEventToArrayList() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        ArrayList<Event> initialEventList = new ArrayList<>();

        Event cnsFestival = new Event(1,
                "Club & Society Festival",
                "Browse through and learn more about Taylor’s University’s diverse clubs and societies. Free of charge!",
                "Orientation Leaders",
                "@drawable/cns_preview_background",
                new Date(String.valueOf(dateFormat.parse("16-11-2023"))),
                new Date(String.valueOf(dateFormat.parse("23-11-2023"))),
                "8:00 AM - 5:00 PM",
                new ArrayList<String>());

        ArrayList<String> cnsVendorList = new ArrayList<>();
        cnsVendorList.add("Taylor's Basketball Club");
        cnsVendorList.add("Taylor's Volleyball Club");
        cnsVendorList.add("Taylor's Cooking Club");
        cnsVendorList.add("KFC");
        cnsVendorList.add("McDonald's");
        cnsVendorList.add("Starbucks");
        cnsFestival.setVendorIdList(cnsVendorList);

        Event traditionalFoodFestival = new Event(2,
                "Traditional Food Festival",
                "Taylor's University celebrates its diverse culture with a traditional food festival featuring a tantalizing array of authentic dishes from around the world",
                "TU Student Council",
                "@drawable/tff_preview_background",
                new Date(String.valueOf(dateFormat.parse("4-12-2023"))),
                new Date(String.valueOf(dateFormat.parse("8-12-2023"))),
                "8:00 AM - 5:00 PM",
                new ArrayList<>());

        ArrayList<String> traditionalFoodVendorIdList = new ArrayList<>();
        traditionalFoodVendorIdList.add("Nasi Lemak Delights");
        traditionalFoodVendorIdList.add("Roti Canai Corner");
        traditionalFoodVendorIdList.add("Satay Paradise");
        traditionalFoodVendorIdList.add("Laksa Haven");
        traditionalFoodVendorIdList.add("Hainanese Chicken Rice Delights");
        traditionalFoodVendorIdList.add("Cendol Oasis");
        traditionalFoodFestival.setVendorIdList(traditionalFoodVendorIdList);

        initialEventList.add(cnsFestival);
        initialEventList.add(traditionalFoodFestival);
        return  initialEventList;
    }
}
