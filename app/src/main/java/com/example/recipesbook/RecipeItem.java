package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipesbook.db.CookLaterManager;

public class RecipeItem extends AppCompatActivity {

    ImageView imageFullPreview;
    Button cancelButton, cookLaterButton;
    TextView recipeTitle, recipeDuration, recipeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        cookLaterButton = findViewById(R.id.cook_later_button);
        imageFullPreview = findViewById(R.id.imageFullPreview);

        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeDescription = findViewById(R.id.recipeDescription);
        cancelButton = findViewById(R.id.recipe_item_button);

        String ingredientsAmount = getIntent().getStringExtra("recipeIngredients");
        String intentImage = getIntent().getStringExtra("recipeImage");
        String intentTitle = getIntent().getStringExtra("recipeTitle");
        String intentDuration = getIntent().getStringExtra("recipeDuration");
        String intentDescription = getIntent().getStringExtra("recipeDescription");

        Glide.with(this)
                .load(intentImage)
                .into(imageFullPreview);
        recipeTitle.setText(intentTitle);
        recipeDuration.setText(intentDuration);
        recipeDescription.setText(intentDescription);

        cancelButton.setOnClickListener(v -> finish());

        cookLaterButton.setOnClickListener(v -> {
            CookLaterManager cookLaterManager = new CookLaterManager(this);
//            cookLaterManager.add(intentImage, intentTitle, ingredientsAmount, intentDuration, intentDescription)
        });

    }

}













