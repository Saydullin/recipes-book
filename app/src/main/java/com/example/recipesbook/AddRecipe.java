package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipesbook.db.DbRecipe;
import com.example.recipesbook.db.FirebaseManager;
import com.example.recipesbook.db.PictureManager;
import com.example.recipesbook.db.RecipeManager;
import com.example.recipesbook.utils.Validator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    EditText recipeIngredientsAmount;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
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
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeImagePreview = findViewById(R.id.recipeImagePreview);
        recipeIngredientsAmount = findViewById(R.id.recipeIngredientsAmount);

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
                    Toast.makeText(AddRecipe.this, "Choose 300x200 image", Toast.LENGTH_SHORT).show();
                    pickImageFromGallery();
                }
            } else {
                Toast.makeText(AddRecipe.this, "Your os is too old", Toast.LENGTH_SHORT).show();
            }
        });

        submit_add_recipe.setOnClickListener(v -> {
            RecipeManager recipeManager = new RecipeManager(this);
            Validator validate = new Validator(this);

            try {
                String validateImage = recipeImagePreview.getResources().toString();
                String validateTitle = recipeTitle.getText().toString().trim();
                String validateDescription= recipeDescription.getText().toString().trim();
                int validateDuration = Integer.parseInt(recipeDuration.getText().toString());
                int validateIngredientsAmount = Integer.parseInt(recipeIngredientsAmount.getText().toString());

                // Validate received data from user
                validate.checkString("image", validateImage, new int[] {5, -1});
                validate.checkString("title", validateTitle, new int[] {3, 35});
                validate.checkInt("ingredients amount", validateIngredientsAmount, new int[] {2, 50});
                validate.checkInt("duration", validateDuration, new int[] {5, 1440});
                validate.checkString("description", validateDescription, new int[] {10, 400});

                PictureManager pictureManager = new PictureManager(this);
                String imageName = pictureManager.addPicture(imageUri, null);

                if (!imageName.equals("")) {
                    // Add data to databases
                    Map<String, Object> recipe = new HashMap<>();
                    recipe.put("userEmail", "saydullinweb@gmail.com");
                    recipe.put("title", validateTitle);
                    recipe.put("image", imageName);
                    recipe.put("duration", validateDuration);
                    recipe.put("ingAmount", validateIngredientsAmount);
                    recipe.put("description", validateDescription);

                    FirebaseManager firebaseManager = new FirebaseManager(this);

                    Map<String, Object> res = firebaseManager.add(recipe, "recipes");

                    if (res.get("ok") == "true") {
                        Toast.makeText(AddRecipe.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddRecipe.this, "Adding Failed", Toast.LENGTH_SHORT).show();
                    }
                }
//                recipeManager.add(imageURI, validateTitle, validateIngredientsAmount, validateDuration, validateDescription, "salads");
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
            Toast.makeText(AddRecipe.this, data.getData().toString(), Toast.LENGTH_LONG).show();
            imageURI = data.getData().toString();
            imageUri = data.getData();
            recipeImagePreview.setImageURI(data.getData());
        }
    }

}


