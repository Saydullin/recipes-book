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
import java.util.UUID;

public class CookLaterManager {

    CookLater cookLater;
    Context context;

    public CookLaterManager(Context context) {
        this.context = context;
    }

    public void add(String imagePreview, String title, int ingredientsAmount, int duration, String description, String tag) {
        cookLater = new CookLater(context);

        SQLiteDatabase database = cookLater.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DbRecipe.KEY_IMAGE, imagePreview);
        contentValues.put(DbRecipe.KEY_TITLE, title);
        contentValues.put(DbRecipe.KEY_DURATION, duration);
        contentValues.put(DbRecipe.KEY_DESCRIPTION, description);
        contentValues.put(DbRecipe.KEY_INGREDIENTS_AMOUNT, ingredientsAmount);
        contentValues.put(DbRecipe.KEY_TAG, tag);

        database.insert(DbRecipe.TABLE_RECIPES, null, contentValues);
    }

    public List<Recipe> get(String tag) {

        cookLater = new CookLater(context);

        Cursor cursor;

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase database = cookLater.getWritableDatabase();

        if (tag.equals("all")) {
            cursor = database.query(DbRecipe.TABLE_RECIPES, null, null, null, null, null, null);
        } else {
            cursor = database.rawQuery("SELECT * FROM " + DbRecipe.TABLE_RECIPES + " WHERE " + cookLater.KEY_TAG + "='" + tag + "'", null);
        }

//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DbRecipe.KEY_ID);
//            int imageIndex = cursor.getColumnIndex(DbRecipe.KEY_IMAGE);
//            int titleIndex = cursor.getColumnIndex(DbRecipe.KEY_TITLE);
//            int descriptionIndex = cursor.getColumnIndex(DbRecipe.KEY_DESCRIPTION);
//            int durationIndex = cursor.getColumnIndex(DbRecipe.KEY_DURATION);
//            int ingredientsAmountIndex = cursor.getColumnIndex(DbRecipe.KEY_INGREDIENTS_AMOUNT);
//            int duration;
//            StringBuilder durationFormat = new StringBuilder();
//
//            do {
//                duration = cursor.getInt(durationIndex);
////                if (duration / 60 >= 1) {
////                    durationFormat.append(duration / 60).append("h ");
////                }
////                durationFormat.append(duration % 60).append("minR");
//                String randomKey = UUID.randomUUID().toString();
//
//                recipeList.add(new Recipe(
//                        randomKey,
//                        cursor.getInt(ingredientsAmountIndex),
//                        duration,
//                        cursor.getString(titleIndex),
//                        cursor.getString(imageIndex),
//                        cursor.getString(descriptionIndex)));
//
//                durationFormat.delete(0, durationFormat.length());
//            } while (cursor.moveToNext());
//        }

        cursor.close();

        return recipeList;
    }
}


