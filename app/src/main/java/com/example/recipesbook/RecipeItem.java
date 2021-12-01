package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipesbook.db.CookLaterManager;
import com.example.recipesbook.db.RecipeManager;

public class RecipeItem extends AppCompatActivity {

    ImageView imageFullPreview;
    Button cancelButton, cookLaterButton;
    TextView recipeTitle, recipeDuration, recipeDescription, recipeIngredients, recipeAuthor, recipeDate;
    RecipeManager recipeManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        cookLaterButton = findViewById(R.id.cook_later_button);
        imageFullPreview = findViewById(R.id.imageFullPreview);

        recipeDate = findViewById(R.id.recipeDate);
        recipeAuthor = findViewById(R.id.recipeAuthor);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        cancelButton = findViewById(R.id.recipe_item_button);

        String intentImage = getIntent().getStringExtra("recipeImage");
        String intentTitle = getIntent().getStringExtra("recipeTitle");
        String intentDate = getIntent().getStringExtra("recipeDate");
        String intentDateLong = getIntent().getStringExtra("recipeDateLong");
        String intentAuthor = "By " + getIntent().getStringExtra("recipeAuthor");
        String intentDuration = getIntent().getStringExtra("recipeDuration");
        String intentDescription = getIntent().getStringExtra("recipeDescription");
        String intentIngredients = getIntent().getStringExtra("recipeIngredients");
        String intentTag = getIntent().getStringExtra("recipeTag");
        String intentId = getIntent().getStringExtra("recipeId");

        Glide.with(this)
                .load(intentImage)
                .into(imageFullPreview);
        recipeDate.setText("Published on " + intentDate);
        recipeTitle.setText(intentTitle);
        recipeAuthor.setText(intentAuthor);
        recipeDuration.setText(String.valueOf(intentDuration));
        recipeDuration.setText("Hello");
        recipeDescription.setText(intentDescription);
        recipeIngredients.setText(intentIngredients);

        cancelButton.setOnClickListener(v -> finish());

        cookLaterButton.setOnClickListener(v -> {
            recipeManager = new RecipeManager(this);
//            long durationLong = Long.parseLong(intentDuration);
            long dateLong = Long.parseLong(intentDateLong);
            long durationLong = 452362920;

            recipeManager.addToLater(
                    durationLong,
                    dateLong,
                    intentImage,
                    intentTitle,
                    intentIngredients,
                    intentDescription,
                    intentTag,
                    intentId
            );
            Toast.makeText(this, "Recipe added to Cook Later list", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

}













