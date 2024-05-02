package com.example.mad_assignment2.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mad_assignment2.models.Vendor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class VendorDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "VendorDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Vendor";
    private static final String NAME_FIELD = "name"; // Set name as the primary key
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CATEGORY_FIELD = "category";
    private static final String IMAGE_URL_FIELD = "imageUrl";
    private static final String RATING_FIELD = "rating";
    private static final String BOOTH_LOCATION_FIELD = "boothLocation";
    private static final String BOOTH_DIRECTORY_FIELD = "boothDirectory";

    public VendorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                NAME_FIELD + " TEXT PRIMARY KEY, " + // Set name as the primary key
                EMAIL_FIELD + " TEXT, " +
                PASSWORD_FIELD + " TEXT, " +
                DESCRIPTION_FIELD + " TEXT, " +
                CATEGORY_FIELD + " TEXT, " +
                IMAGE_URL_FIELD + " TEXT, " +
                RATING_FIELD + " REAL, " +
                BOOTH_LOCATION_FIELD + " INTEGER, " +
                BOOTH_DIRECTORY_FIELD + " TEXT)";
        sqLiteDatabase.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Boolean initializeVendorData() throws Exception {
        SQLiteDatabase DB = this.getWritableDatabase();

        // Check if the database is empty
        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        Log.d("Database Initialization", "Current row count: " + count);

        // If the database is empty, add initial vendors
        if (count == 0) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            ArrayList<Vendor> initialVendors = VendorInitData.initializeVendorToArrayList();

            // Insert initial vendors into the database
            for (Vendor vendor : initialVendors) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(NAME_FIELD, vendor.getName());
                contentValues.put(EMAIL_FIELD, vendor.getEmail());
                contentValues.put(PASSWORD_FIELD, vendor.getPassword());
                contentValues.put(DESCRIPTION_FIELD, vendor.getDescription());
                contentValues.put(CATEGORY_FIELD, vendor.getCategory());
                contentValues.put(IMAGE_URL_FIELD, vendor.getImageUrl());
                contentValues.put(RATING_FIELD, vendor.getRating());
                contentValues.put(BOOTH_LOCATION_FIELD, vendor.getBoothLocation());
                contentValues.put(BOOTH_DIRECTORY_FIELD, vendor.getBoothDirectory());

                long result = DB.insert(TABLE_NAME, null, contentValues);

                if (result == -1) {
                    Log.d("Database Initialization", "Insert failed for vendor with name: " + vendor.getName());
                    return false; // If any insertion fails, return false
                } else {
                    Log.d("Database Initialization", "Insert successful for vendor with name: " + vendor.getName());
                }
            }

            return true; // All initial vendors inserted successfully
        } else {
            Log.d("Database Initialization", "Database is not empty, no need to initialize");
            return false; // Database is not empty, no need to initialize
        }
    }

    public Cursor getVendorData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public void clearDatabase() {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete(TABLE_NAME, null, null);
        DB.close();
    }

    public void logVendorData() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME_FIELD));
                    @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(EMAIL_FIELD));

                    Log.d("Database Content", "Name: " + name + " | Email: " + email);

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

    //
// SIGNUP, LOGIN, BOOKING METHODS
//
    public boolean addNewVendor(Vendor vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_FIELD, vendor.getName()); // Use name as primary key
        values.put(EMAIL_FIELD, vendor.getEmail());
        values.put(PASSWORD_FIELD, vendor.getPassword());
        values.put(DESCRIPTION_FIELD, vendor.getDescription());
        values.put(CATEGORY_FIELD, vendor.getCategory());
        values.put(IMAGE_URL_FIELD, vendor.getImageUrl());
        values.put(RATING_FIELD, vendor.getRating());
        values.put(BOOTH_LOCATION_FIELD, vendor.getBoothLocation());
        values.put(BOOTH_DIRECTORY_FIELD, vendor.getBoothDirectory());


        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }

    public Vendor getVendorByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Vendor vendor = null;

        // Define the columns you want to retrieve
        String[] columns = {NAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, DESCRIPTION_FIELD, CATEGORY_FIELD, IMAGE_URL_FIELD, RATING_FIELD, BOOTH_LOCATION_FIELD,BOOTH_DIRECTORY_FIELD};

        // Define the selection criteria (WHERE clause)
        String selection = EMAIL_FIELD + " = ?";
        String[] selectionArgs = {email};

        // Perform the query to retrieve the vendor with the specified email
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // Extract vendor information from the cursor
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME_FIELD));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(CATEGORY_FIELD));
            @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(IMAGE_URL_FIELD));
            @SuppressLint("Range") double rating = cursor.getDouble(cursor.getColumnIndex(RATING_FIELD));
            @SuppressLint("Range") int boothLocation = cursor.getInt(cursor.getColumnIndex(BOOTH_LOCATION_FIELD));
            @SuppressLint("Range") String boothDirectory = cursor.getString(cursor.getColumnIndex(BOOTH_DIRECTORY_FIELD));
            // Create a Vendor object
            vendor = new Vendor(name, email, password, description, category, imageUrl, rating, boothLocation,boothDirectory);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return vendor;
    }

    public Vendor getVendorByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Vendor vendor = null;

        // Define the columns you want to retrieve
        String[] columns = {NAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, DESCRIPTION_FIELD, CATEGORY_FIELD, IMAGE_URL_FIELD, RATING_FIELD, BOOTH_LOCATION_FIELD};

        // Define the selection criteria (WHERE clause)
        String selection = NAME_FIELD + " = ?";
        String[] selectionArgs = {name};

        // Perform the query to retrieve the vendor with the specified email
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // Extract vendor information from the cursor
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(EMAIL_FIELD));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(CATEGORY_FIELD));
            @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(IMAGE_URL_FIELD));
            @SuppressLint("Range") double rating = cursor.getDouble(cursor.getColumnIndex(RATING_FIELD));
            @SuppressLint("Range") int boothLocation = cursor.getInt(cursor.getColumnIndex(BOOTH_LOCATION_FIELD));
            @SuppressLint("Range") String boothDirectory = cursor.getString(cursor.getColumnIndex(BOOTH_DIRECTORY_FIELD));
            // Create a Vendor object
            vendor = new Vendor(name, email, password, description, category, imageUrl, rating, boothLocation,boothDirectory);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return vendor;
    }

    public Vendor authenticateUser(String email, String password) {
        // Retrieve user data from the database using email
        Vendor vendor = getVendorByEmail(email);

        // Check if a user with the given email exists and the password matches
        if (vendor != null && password.equals(vendor.getPassword())) {
            return vendor; // Return the authenticated vendor
        } else {
            return null; // Return null if authentication fails
        }
    }

    public boolean isEmailUsed(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + EMAIL_FIELD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

     public Cursor readVendorDetails(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


        public Vendor getVendorByButtonId(int buttonId) {
            SQLiteDatabase db = this.getReadableDatabase();
            Vendor vendor = null;

            String[] columns = {
                    NAME_FIELD,
                    EMAIL_FIELD,
                    PASSWORD_FIELD,
                    DESCRIPTION_FIELD,
                    CATEGORY_FIELD,
                    IMAGE_URL_FIELD,
                    RATING_FIELD,
                    BOOTH_LOCATION_FIELD,
                    BOOTH_DIRECTORY_FIELD
            };

            String selection = BOOTH_LOCATION_FIELD + " = ?";
            String[] selectionArgs = {String.valueOf(buttonId)};

            Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME_FIELD));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(EMAIL_FIELD));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(CATEGORY_FIELD));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(IMAGE_URL_FIELD));
                @SuppressLint("Range") double rating = cursor.getDouble(cursor.getColumnIndex(RATING_FIELD));
                @SuppressLint("Range") int boothLocation = cursor.getInt(cursor.getColumnIndex(BOOTH_LOCATION_FIELD));
                @SuppressLint("Range") String boothDirectory = cursor.getString(cursor.getColumnIndex(BOOTH_DIRECTORY_FIELD));

                vendor = new Vendor(name, email, password, description, category, imageUrl, rating, boothLocation, boothDirectory);
            }

            cursor.close();
            db.close();

            return vendor;
        }
    public void updateVendorRating(Vendor vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RATING_FIELD, vendor.getRating());

        String selection = NAME_FIELD + " = ?";
        String[] selectionArgs = { vendor.getName() };

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}

