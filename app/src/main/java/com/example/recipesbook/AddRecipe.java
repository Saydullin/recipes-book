package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipesbook.db.DbRecipe;
import com.example.recipesbook.db.RecipeManager;
import com.example.recipesbook.utils.Validator;

public class AddRecipe extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    EditText recipeTitle;
    EditText recipeDuration;
    EditText recipeDescription;
    EditText recipeIngredientsAmount;
    Button button_cancel_activity;
    Button submit_add_recipe;
    ImageView recipeImagePreview;

    DbRecipe dbRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        dbRecipe = new DbRecipe(this);

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
                String validateImage = recipeImagePreview.getResources().toString().trim();
                String validateTitle = recipeTitle.getText().toString().trim();
                String validateDescription= recipeDescription.getText().toString().trim();
                int validateDuration = Integer.parseInt(recipeDuration.getText().toString());
                int validateIngredientsAmount = Integer.parseInt(recipeIngredientsAmount.getText().toString());

                // Validate received data from user
                validate.checkString("image", validateImage, new int[] {5, -1});
                validate.checkString("title", validateTitle, new int[] {3, 15});
                validate.checkInt("ingredients amount", validateIngredientsAmount, new int[] {2, 50});
                validate.checkInt("duration", validateDuration, new int[] {5, 1440});
                validate.checkString("description", validateDescription, new int[] {50, 400});

                // Add data to database
                recipeManager.add(validateImage, validateTitle, validateIngredientsAmount, validateDuration, validateDescription);
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            } catch(NumberFormatException e) {
                Toast.makeText(this, "Fill all the fields!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

//            SQLiteDatabase database1 = dbRecipe.getWritableDatabase();
//            // Чтение из базы в классе сделать
//            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
//
//            Cursor cursor = database1.query(DbRecipe.TABLE_RECIPES, null, null, null, null, null, null);
//
//            if (cursor.moveToFirst()) {
//                int idIndex = cursor.getColumnIndex(DbRecipe.KEY_ID);
//                int nameIndex = cursor.getColumnIndex(DbRecipe.KEY_TITLE);
//                int emailIndex = cursor.getColumnIndex(DbRecipe.KEY_MAIL);
//
//                do {
//                    Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                            ", name = " + cursor.getString(nameIndex) +
//                            ", email = " + cursor.getString(emailIndex));
//                } while (cursor.moveToNext());
//            } else {
//                Log.d("mLog", "0 rows");
//            }
//
//            cursor.close();
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
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
            recipeImagePreview.setImageURI(data.getData());
        }
    }

}


