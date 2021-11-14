package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class UserProfile extends AppCompatActivity {

    Button button_cancel_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        button_cancel_activity = findViewById(R.id.button_cancel_activity);

        button_cancel_activity.setOnClickListener(v -> finish());
    }
}


