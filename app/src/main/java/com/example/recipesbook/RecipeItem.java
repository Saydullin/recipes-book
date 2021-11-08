package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecipeItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        TextView recipeTitle = findViewById(R.id.recipeTitle);
        ImageView imageFullPreview = findViewById(R.id.imageFullPreview);
        TextView recipeDuration = findViewById(R.id.recipeDuration);
        TextView recipeDescription = findViewById(R.id.recipeDescription);
        Button recipe_item_button = findViewById(R.id.recipe_item_button);

        Glide.with(this)
                .load(getIntent().getStringExtra("recipeImage"))
                .into(imageFullPreview);
        recipeTitle.setText(getIntent().getStringExtra("recipeTitle"));
        recipeDuration.setText(getIntent().getStringExtra("recipeDuration"));
        recipeDescription.setText(getIntent().getStringExtra("recipeDescription"));

        recipe_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


