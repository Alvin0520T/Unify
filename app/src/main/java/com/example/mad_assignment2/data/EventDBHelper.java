package com.example.mad_assignment2.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mad_assignment2.models.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EventDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Event";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String ORGANIZER_FIELD = "organizer";
    private static final String BACKGROUND_LOGO_URL_FIELD = "backgroundLogoUrl";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";
    private static final String TIME_FIELD = "time";
    private static final String VENDOR_ID_LIST_FIELD = "vendorIdList";


    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(ORGANIZER_FIELD)
                .append(" TEXT, ")
                .append(BACKGROUND_LOGO_URL_FIELD)
                .append(" TEXT, ")
                .append(START_DATE_FIELD)
                .append(" DATE, ")
                .append(END_DATE_FIELD)
                .append(" DATE, ")
                .append(TIME_FIELD)
                .append(" TEXT, ")
                .append(VENDOR_ID_LIST_FIELD)
                .append(" TEXT")
                .append(")");

        sqLiteDatabase.execSQL(sql.toString());
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Boolean initializeEventData() throws Exception {
        SQLiteDatabase DB = this.getWritableDatabase();

        // Check if the database is empty
        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        Log.d("Database Initialization", "Current row count: " + count);

        // If the database is empty, add initial events
        if (count == 0) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            ArrayList<Event> initialEvents = EventInitData.initializeEventToArrayList();

            // Insert initial events into the database
            for (Event event : initialEvents) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID_FIELD, event.getId());
                contentValues.put(TITLE_FIELD, event.getTitle());
                contentValues.put(DESCRIPTION_FIELD, event.getDescription());
                contentValues.put(ORGANIZER_FIELD, event.getOrganizer());
                contentValues.put(BACKGROUND_LOGO_URL_FIELD, event.getBackgroundLogoUrl());
                contentValues.put(START_DATE_FIELD, dateFormat.format(event.getStartDate()));
                contentValues.put(END_DATE_FIELD, dateFormat.format(event.getEndDate()));
                contentValues.put(TIME_FIELD, event.getTime());
                contentValues.put(VENDOR_ID_LIST_FIELD, TextUtils.join(",", event.getVendorIdList()));

                long result = DB.insert(TABLE_NAME, null, contentValues);

                if (result == -1) {
                    Log.d("Database Initialization", "Insert failed for event with ID: " + event.getId());
                    return false; // If any insertion fails, return false
                } else {
                    Log.d("Database Initialization", "Insert successful for event with ID: " + event.getId());
                }
            }

            return true; // All initial events inserted successfully
        } else {
            Log.d("Database Initialization", "Database is not empty, no need to initialize");
            return false; // Database is not empty, no need to initialize
        }
    }

    public Cursor getEventData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        // Check if the event with the given ID exists
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    @SuppressLint("Range")
    public Event getEventFromId(int eventId) throws ParseException {
        SQLiteDatabase DB = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {
                ID_FIELD,
                TITLE_FIELD,
                DESCRIPTION_FIELD,
                ORGANIZER_FIELD,
                BACKGROUND_LOGO_URL_FIELD,
                START_DATE_FIELD,
                END_DATE_FIELD,
                TIME_FIELD,
                VENDOR_ID_LIST_FIELD
        };

        // Define the selection criteria
        String selection = ID_FIELD + "=?";
        String[] selectionArgs = {String.valueOf(eventId)};

        Cursor cursor = DB.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            ArrayList<String> initialEventList = new ArrayList<>();

            Event event = new Event(0, "", "", "", "", new Date(String.valueOf(dateFormat.parse("16-11-2023"))), new Date(String.valueOf(dateFormat.parse("16-11-2023"))), "", initialEventList);
            event.setId(cursor.getInt(cursor.getColumnIndex(ID_FIELD)));
            event.setTitle(cursor.getString(cursor.getColumnIndex(TITLE_FIELD)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD)));
            event.setOrganizer(cursor.getString(cursor.getColumnIndex(ORGANIZER_FIELD)));
            event.setBackgroundLogoUrl(cursor.getString(cursor.getColumnIndex(BACKGROUND_LOGO_URL_FIELD)));
            event.setStartDate(new Date(String.valueOf(dateFormat.parse(cursor.getString(cursor.getColumnIndex(START_DATE_FIELD))))));
            event.setEndDate(new Date(String.valueOf(dateFormat.parse(cursor.getString(cursor.getColumnIndex(END_DATE_FIELD))))));
            event.setTime(cursor.getString(cursor.getColumnIndex(TIME_FIELD)));

            // Parse the vendorIdList into an ArrayList
            String vendorIdListString = cursor.getString(cursor.getColumnIndex(VENDOR_ID_LIST_FIELD));
            ArrayList<String> vendorIdList = new ArrayList<>(Arrays.asList(vendorIdListString.split(",")));
            event.setVendorIdList(vendorIdList);

            cursor.close();
            return event;
        } else {
            return null; // Event with the given ID not found
        }
    }

    public void logEventData() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ID_FIELD));
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(TITLE_FIELD));
                    @SuppressLint("Range") String vendorIdList = cursor.getString(cursor.getColumnIndex(VENDOR_ID_LIST_FIELD));

                    Log.d("Database Content", "ID: " + id + " | Title: " + title + " | VendorIdList: " + vendorIdList);

                    // Add more fields as needed
                } while (cursor.moveToNext());
            } else {
                Log.d("Database Content", "No data found in the database.");
            }
        } catch (Exception e) {
            Log.e("Database Content", "Error logging data: " + e.getMessage());
        } finally {
            cursor.close();
        }
    }

    public void clearDatabase() {
        SQLiteDatabase DB = this.getWritableDatabase();

        // Delete all records from the table
        DB.delete(TABLE_NAME, null, null);

        // Close the database connection
        DB.close();
    }

    public boolean addVendorToEvent(int eventId, String newVendorId) {
        SQLiteDatabase DB = this.getWritableDatabase();

        // First, retrieve the existing vendorIdList for the event with the given ID
        Cursor cursor = DB.rawQuery("SELECT " + VENDOR_ID_LIST_FIELD + " FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?", new String[]{String.valueOf(eventId)});

        if (cursor.moveToFirst()) {
            String existingVendorIdList = cursor.getString(0);

            if (existingVendorIdList == null) {
                existingVendorIdList = String.valueOf(newVendorId);
            } else {
                // Append the new vendor ID to the existing list
                existingVendorIdList += "," + newVendorId;
            }

            // Update the vendorIdList in the database
            ContentValues contentValues = new ContentValues();
            contentValues.put(VENDOR_ID_LIST_FIELD, existingVendorIdList);
            int result = DB.update(TABLE_NAME, contentValues, ID_FIELD + "=?", new String[]{String.valueOf(eventId)});

            if (result > 0) {
                return true; // Successfully added the new vendor ID to the event
            }
        }

        return false; // Failed to add the new vendor ID
    }

    public String getEventTitle(int eventId) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " + TITLE_FIELD + " FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?", new String[]{String.valueOf(eventId)});
        String eventTitle = null;

        if (cursor.moveToFirst()) {
            eventTitle = cursor.getString(0);
        }

        cursor.close();
        return eventTitle;
    }

    public String getEventDescription(int eventId) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " + DESCRIPTION_FIELD + " FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?", new String[]{String.valueOf(eventId)});
        String eventDescription = null;

        if (cursor.moveToFirst()) {
            eventDescription = cursor.getString(0);
        }

        cursor.close();
        return eventDescription;
    }

    public String getEventDate(int eventId) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " + START_DATE_FIELD + ", " + END_DATE_FIELD + " FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?", new String[]{String.valueOf(eventId)});
        String eventDate = null;

        if (cursor.moveToFirst()) {
            String startDate = cursor.getString(0);
            String endDate = cursor.getString(1);
            eventDate = startDate + " - " + endDate;
        }

        cursor.close();
        return eventDate;
    }

    public String getEventTime(int eventId) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " + TIME_FIELD + " FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?", new String[]{String.valueOf(eventId)});
        String eventTime = null;

        if (cursor.moveToFirst()) {
            eventTime = cursor.getString(0);
        }

        cursor.close();
        return eventTime;
    }

}
