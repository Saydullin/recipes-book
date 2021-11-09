package com.example.recipesbook.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class Recipe extends AppCompatActivity {

    DbRecipe dbRecipe;

    public void add(int ingredientsAmount, int duration, String imagePreview, String title, String description) {
        dbRecipe = new DbRecipe(this);

        SQLiteDatabase database = dbRecipe.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DbRecipe.KEY_NAME, title);
        contentValues.put(DbRecipe.KEY_MAIL, description);

        database.insert(DbRecipe.TABLE_RECIPES, null, contentValues);
    }

}
