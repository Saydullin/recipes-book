package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recipesbook.db.DbRecipe;
import com.example.recipesbook.db.FirebaseManager;
import com.example.recipesbook.db.PictureManager;
import com.example.recipesbook.db.RecipeManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddRecipe extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    String imageURI;
    EditText recipeTitle;
    EditText recipeDuration;
    EditText recipeDescription;
    EditText recipeIngredients;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    RecipeManager recipeManager;
    Spinner recipesSpinner;
    Button button_cancel_activity;
    Button submit_add_recipe;
    ImageView recipeImagePreview;

    DbRecipe dbRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        dbRecipe = new DbRecipe(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        recipeTitle = findViewById(R.id.recipeTitle);
        recipesSpinner = findViewById(R.id.recipesSpinner);
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeImagePreview = findViewById(R.id.recipeImagePreview);
        recipeIngredients = findViewById(R.id.recipeIngredients);

        button_cancel_activity = findViewById(R.id.button_cancel_activity);
        submit_add_recipe = findViewById(R.id.submit_add_recipe);

        button_cancel_activity.setOnClickListener(v -> finish());

        recipeImagePreview.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(AddRecipe.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                Toast.makeText(AddRecipe.this, "Your OS is too old", Toast.LENGTH_SHORT).show();
            }
        });

        submit_add_recipe.setOnClickListener(v -> {
            submit_add_recipe.setEnabled(false);
            submit_add_recipe.setText(R.string.loading);
            RecipeManager recipeManager = new RecipeManager(this);

            try {
                String validateTitle = recipeTitle.getText().toString().trim();
                String validateIngredients = recipeIngredients.getText().toString().trim();
                String validateDescription= recipeDescription.getText().toString().trim();
                int validateDuration = Integer.parseInt(recipeDuration.getText().toString());

                PictureManager pictureManager = new PictureManager(this);
                String imageName = pictureManager.addPicture(imageUri, null);

                if (imageName.equals("")) {
                    imageName = "no-recipe-image.png";
                }
                long timestamp = System.currentTimeMillis();
                String chosenTag = recipesSpinner.getSelectedItem().toString();
                SharedPreferences prefs = getSharedPreferences("userData", MODE_PRIVATE);
                String userEmail = prefs.getString("email", "No email");
                String userName = prefs.getString("name", "No name");
                String randomKey = UUID.randomUUID().toString();
                String docKey = UUID.randomUUID().toString();

                // Add data to databases
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("userEmail", userEmail);
                recipe.put("date", timestamp);
                recipe.put("userName", userName);
                recipe.put("title", validateTitle);
                recipe.put("image", imageName);
                recipe.put("duration", validateDuration);
                recipe.put("id", randomKey);
                recipe.put("ingredients", validateIngredients);
                recipe.put("description", validateDescription);
                recipe.put("tag", chosenTag.toLowerCase());

                FirebaseManager firebaseManager = new FirebaseManager(this);

                Map<String, Object> res = firebaseManager.add(recipe, "recipes", docKey);

                if (res.get("ok") == "true") {
                    recipeManager = new RecipeManager(this);
                    long durationRecipe = Long.parseLong(recipeDuration.getText().toString());
                    recipeManager.addToAdded(
                            durationRecipe,
                            timestamp,
                            imageName,
                            validateTitle,
                            validateIngredients,
                            validateDescription,
                            chosenTag.toLowerCase(),
                            docKey
                    );
                    finish();
                } else {
                    Toast.makeText(AddRecipe.this, "Adding Failed", Toast.LENGTH_SHORT).show();
                }
            } catch(NumberFormatException e) {
                Toast.makeText(this, "Fill all the fields!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                Toast.makeText(AddRecipe.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            assert data != null;
            imageURI = data.getData().toString();
            imageUri = data.getData();
            recipeImagePreview.setImageURI(data.getData());
        }
    }

}


