package com.example.mad_assignment2.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad_assignment2.R;
import com.example.mad_assignment2.data.VendorDBHelper;
import com.example.mad_assignment2.models.Vendor;

public class VendorLoginScreen extends AppCompatActivity {

    EditText emailET, passwordET;
    Button loginBtn;
    TextView changeToSignUpBtn;
    VendorDBHelper vendorDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login_screen);


        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.vendorLoginButton);
        changeToSignUpBtn = findViewById(R.id.changeToSignUpButton);

        vendorDBHelper = new VendorDBHelper(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email and password entered by the user
                String enteredEmail = emailET.getText().toString();
                String enteredPassword = passwordET.getText().toString();

                // Authenticate the user
                Vendor authenticatedVendor = vendorDBHelper.authenticateUser(enteredEmail, enteredPassword);
                if (authenticatedVendor != null) {
                    // Successful login
                    showToast("Login Successful");
                    // Now you can access authenticatedVendor and its properties if needed.
                    Intent toBoothBookingCalendar = new Intent(VendorLoginScreen.this, VendorBookingCalendarScreen.class);
                    toBoothBookingCalendar.putExtra("vendorname", authenticatedVendor.getName());
                    startActivity(toBoothBookingCalendar);
                } else {
                    // Login failed
                    showToast("Login Failed. Please check your email and password.");
                }
            }
        });


        changeToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorLoginScreen.this, VendorSignUpScreen.class);
                VendorLoginScreen.this.startActivity(intent);
            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}