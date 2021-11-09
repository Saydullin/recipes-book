package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
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
import com.example.recipesbook.db.Recipe;

public class AddRecipe extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    EditText recipeTitle;
    EditText recipeDescription;
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
        recipeDescription = findViewById(R.id.recipeDescription);
        button_cancel_activity = findViewById(R.id.button_cancel_activity);
        submit_add_recipe = findViewById(R.id.submit_add_recipe);
        recipeImagePreview = findViewById(R.id.recipeImagePreview);

        button_cancel_activity.setOnClickListener(v -> finish());

        recipeImagePreview.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(AddRecipe.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    Toast.makeText(AddRecipe.this, "Permission given", Toast.LENGTH_SHORT).show();
                    pickImageFromGallery();
                }
            } else {
                Toast.makeText(AddRecipe.this, "Your os is too old", Toast.LENGTH_SHORT).show();
            }
        });

        submit_add_recipe.setOnClickListener(v -> {
            Recipe rec = new Recipe();

            String title = recipeTitle.getText().toString();
            String description = recipeDescription.getText().toString();

            rec.add(45, 43, "ef", "title", "desc");

            SQLiteDatabase database1 = dbRecipe.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(DbRecipe.KEY_NAME, title);
            contentValues.put(DbRecipe.KEY_MAIL, description);

            database1.insert(DbRecipe.TABLE_RECIPES, null, contentValues);

            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();

            Cursor cursor = database1.query(DbRecipe.TABLE_RECIPES, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DbRecipe.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DbRecipe.KEY_NAME);
                int emailIndex = cursor.getColumnIndex(DbRecipe.KEY_MAIL);

                do {
                    Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                            ", name = " + cursor.getString(nameIndex) +
                            ", email = " + cursor.getString(emailIndex));
                } while (cursor.moveToNext());
            } else {
                Log.d("mLog", "0 rows");
            }

            cursor.close();
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


