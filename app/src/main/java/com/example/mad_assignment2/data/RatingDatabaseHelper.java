package com.example.mad_assignment2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RatingDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ratings.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_RATINGS = "ratings";
    public static final String COLUMN_ID = "_id"; // Primary key
    public static final String COLUMN_BUTTON_ID = "button_id"; // ID of the button
    public static final String COLUMN_RATING = "rating"; // Rating given by the user

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_RATINGS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_BUTTON_ID
            + " integer not null, " + COLUMN_RATING
            + " real not null);";

    public RatingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    // Add methods to interact with the database

    // Insert a new rating into the database
    public void insertRating(int buttonId, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUTTON_ID, buttonId);
        values.put(COLUMN_RATING, rating);

        // Insert the rating into the database
        db.insert(TABLE_RATINGS, null, values);
        db.close();
    }

    // Get the average rating for a specific button
    public float getAverageRating(int buttonId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_RATING + ") FROM " + TABLE_RATINGS +
                " WHERE " + COLUMN_BUTTON_ID + " = ?", new String[]{String.valueOf(buttonId)});

        float averageRating = -1; // Default value if no ratings are found

        if (cursor != null && cursor.moveToFirst()) {
            averageRating = cursor.getFloat(0);
            cursor.close();
        }

        db.close();
        return averageRating;
    }
}
