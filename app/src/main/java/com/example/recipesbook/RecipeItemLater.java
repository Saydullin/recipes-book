package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipesbook.db.RecipeManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecipeItemLater extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView imageFullPreviewEdit;
    Button cancelButton, deleteButton;
    TextView recipeTitle, recipeDuration, recipeDescription, recipeIngredients, recipeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item_later);

        deleteButton = findViewById(R.id.recipeDeleteButton);

        recipeDate = findViewById(R.id.recipeDate);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        cancelButton = findViewById(R.id.recipe_item_button);
        imageFullPreviewEdit = findViewById(R.id.imageFullPreviewEdit);

        String intentImage = getIntent().getStringExtra("recipeImage");
        String intentTitle = getIntent().getStringExtra("recipeTitle");
        String intentDate = "Published on " + getIntent().getStringExtra("recipeDate");
        String intentDuration = getIntent().getStringExtra("recipeDuration");
        String intentDescription = getIntent().getStringExtra("recipeDescription");
        String intentIngredients = getIntent().getStringExtra("recipeIngredients");
        String docKey = getIntent().getStringExtra("docKey");

        Glide.with(this)
                .load(intentImage)
                .into(imageFullPreviewEdit);
        recipeDate.setText(intentDate);
        recipeTitle.setText(intentTitle);
        recipeDuration.setText(intentDuration);
        recipeDescription.setText(intentDescription);
        recipeIngredients.setText(intentIngredients);

        cancelButton.setOnClickListener(v -> finish());

        deleteButton.setOnClickListener(v -> {
            db = FirebaseFirestore.getInstance();

            DocumentReference noteRef = db.collection("recipes").document(docKey);

            noteRef.delete();

            RecipeManager recipeManager = new RecipeManager(this);

            recipeManager.deleteLater(docKey);

            finish();
        });

    }
}