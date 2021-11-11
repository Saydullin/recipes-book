package com.example.recipesbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Recipe {

    DbRecipe dbRecipe;
    Context context;

    public Recipe(Context context) {
        this.context = context;
    }

    public void add(String imagePreview, String title, int ingredientsAmount, int duration, String description) {
        dbRecipe = new DbRecipe(context);

        SQLiteDatabase database = dbRecipe.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DbRecipe.KEY_IMAGE, imagePreview);
        contentValues.put(DbRecipe.KEY_TITLE, title);
        contentValues.put(DbRecipe.KEY_DURATION, duration);
        contentValues.put(DbRecipe.KEY_DESCRIPTION, description);
        contentValues.put(DbRecipe.KEY_INGREDIENTS_AMOUNT, ingredientsAmount);

        database.insert(DbRecipe.TABLE_RECIPES, null, contentValues);
    }

}
