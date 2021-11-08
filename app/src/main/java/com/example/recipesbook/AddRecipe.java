package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AddRecipe extends AppCompatActivity {

    Button button_cancel_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        button_cancel_activity = findViewById(R.id.button_cancel_activity);

        button_cancel_activity.setOnClickListener(v -> finish());
    }

}

