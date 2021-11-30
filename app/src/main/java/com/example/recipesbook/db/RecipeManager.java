package com.example.recipesbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesbook.models.Recipe;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    MyRecipes myRecipes;
    Context context;

    public RecipeManager(Context context) {
        this.context = context;
    }

    public void addToAdded(long duration, long date, String imagePreview, String title, String ingredients, String description, String tag, String docKey) {
        myRecipes = new MyRecipes(context);
        Toast.makeText(context, "Image: " + imagePreview, Toast.LENGTH_LONG).show();

        SQLiteDatabase database = myRecipes.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MyRecipes.KEY_IMAGE, imagePreview);
        contentValues.put(MyRecipes.KEY_TITLE, title);
        contentValues.put(MyRecipes.KEY_DURATION, duration);
        contentValues.put(MyRecipes.KEY_DATE, date);
        contentValues.put(MyRecipes.KEY_DESCRIPTION, description);
        contentValues.put(MyRecipes.KEY_INGREDIENTS, ingredients);
        contentValues.put(MyRecipes.KEY_TAG, tag);
        contentValues.put(MyRecipes.KEY_DOC_KEY, docKey);

        try {
            database.insert(MyRecipes.TABLE_ADDED_RECIPES, null, contentValues);
            Toast.makeText(context, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Data NOT Saved: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public List<Recipe> getFromAdded() {

        myRecipes = new MyRecipes(context);
        Cursor cursor;

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase database = myRecipes.getWritableDatabase();

        cursor = database.query(MyRecipes.TABLE_ADDED_RECIPES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(MyRecipes.KEY_ID);
            int imageIndex = cursor.getColumnIndex(MyRecipes.KEY_IMAGE);
            int titleIndex = cursor.getColumnIndex(MyRecipes.KEY_TITLE);
            int dateIndex = cursor.getColumnIndex(MyRecipes.KEY_DATE);
            int tagIndex = cursor.getColumnIndex(MyRecipes.KEY_TAG);
            int descriptionIndex = cursor.getColumnIndex(MyRecipes.KEY_DESCRIPTION);
            int durationIndex = cursor.getColumnIndex(MyRecipes.KEY_DURATION);
            int ingredients = cursor.getColumnIndex(MyRecipes.KEY_INGREDIENTS);
            int docKey = cursor.getColumnIndex(MyRecipes.KEY_DOC_KEY);

            do {
                recipeList.add(new Recipe(
                        cursor.getString(descriptionIndex),
                        cursor.getLong(durationIndex),
                        cursor.getLong(dateIndex),
                        cursor.getString(idIndex),
                        cursor.getString(imageIndex),
                        cursor.getString(ingredients),
                        cursor.getString(tagIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(docKey),
                        "No Name"));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipeList;
    }

    public void deleteAdded(String id) {
        SQLiteDatabase database = myRecipes.getWritableDatabase();
    }

}


