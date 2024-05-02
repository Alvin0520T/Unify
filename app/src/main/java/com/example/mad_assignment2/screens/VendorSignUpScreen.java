package com.example.mad_assignment2.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.VendorDBHelper;
import com.example.mad_assignment2.models.Vendor;

public class VendorSignUpScreen extends AppCompatActivity {

    EditText emailET, boothNameET, boothDescriptionET, categoryDescriptionET, imageUrlET, passwordET;
    Button signUpButton;
    TextView changeToLogInBtn;
    VendorDBHelper vendorDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up_screen);

        emailET = findViewById(R.id.emailEditText);
        boothNameET = findViewById(R.id.boothNameEditText);
        boothDescriptionET = findViewById(R.id.boothDescriptionEditText);
        categoryDescriptionET = findViewById(R.id.categoryDescriptionEditText);
        imageUrlET = findViewById(R.id.imageUrlEditText);
        passwordET = findViewById(R.id.passwordEditText);

        signUpButton = findViewById(R.id.signUpButton);
        changeToLogInBtn = findViewById(R.id.changeToLogInButton);

        vendorDBHelper = new VendorDBHelper(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String boothName = boothNameET.getText().toString();
                String boothDescription = boothDescriptionET.getText().toString();
                String category = categoryDescriptionET.getText().toString();
                String imageUrl = imageUrlET.getText().toString();
                String password = passwordET.getText().toString();

                if (email.isEmpty() || boothName.isEmpty() || boothDescription.isEmpty() || category.isEmpty() || imageUrl.isEmpty() || password.isEmpty()) {
                    showToast("Please fill in all fields.");
                } else if (vendorDBHelper.isEmailUsed(email)) {
                    showToast("Email is already in use. Please use a different email.");
                } else {
                    // Create a new Vendor object and add it to the database
                    Vendor newVendor = new Vendor(boothName, email, password, boothDescription, category, imageUrl, 0.0, 0,"null");
                    if (vendorDBHelper.addNewVendor(newVendor)) {

                        showToast("Sign-up successful!");
                        Intent toBoothBookingCalendar = new Intent(VendorSignUpScreen.this, VendorBookingCalendarScreen.class);
                        toBoothBookingCalendar.putExtra("vendorname", boothName);
                        startActivity(toBoothBookingCalendar);
                        // You can navigate to another activity here if needed.
                    } else {
                        showToast("Sign-up failed. Please try again.");
                    }
                }
            }
        });

        changeToLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorSignUpScreen.this, VendorLoginScreen.class);
                VendorSignUpScreen.this.startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
