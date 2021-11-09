package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecipeItem extends AppCompatActivity {

    ImageView imageFullPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        TextView recipeTitle = findViewById(R.id.recipeTitle);
        imageFullPreview = findViewById(R.id.imageFullPreview);
        TextView recipeDuration = findViewById(R.id.recipeDuration);
        TextView recipeDescription = findViewById(R.id.recipeDescription);
        Button recipe_item_button = findViewById(R.id.recipe_item_button);

        Glide.with(this)
                .load(getIntent().getStringExtra("recipeImage"))
                .into(imageFullPreview);
        recipeTitle.setText(getIntent().getStringExtra("recipeTitle"));
        recipeDuration.setText(getIntent().getStringExtra("recipeDuration"));
        recipeDescription.setText(getIntent().getStringExtra("recipeDescription"));

        recipe_item_button.setOnClickListener(v -> finish());

    }
}













