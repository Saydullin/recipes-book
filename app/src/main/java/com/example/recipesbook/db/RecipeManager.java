package com.example.recipesbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.util.ArrayList;

public class RecipeManager {

    DbRecipe dbRecipe;
    Context context;

    public RecipeManager(Context context) {
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

    public ArrayList<String> get() {

        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> resultItem = new ArrayList<String>();

        SQLiteDatabase database1 = dbRecipe.getWritableDatabase();
        // Чтение из базы в классе сделать

        Cursor cursor = database1.query(DbRecipe.TABLE_RECIPES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbRecipe.KEY_ID);
            int imageIndex = cursor.getColumnIndex(DbRecipe.KEY_IMAGE);
            int titleIndex = cursor.getColumnIndex(DbRecipe.KEY_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DbRecipe.KEY_DESCRIPTION);
            int durationIndex = cursor.getColumnIndex(DbRecipe.KEY_DURATION);
            int ingredientsAmountIndex = cursor.getColumnIndex(DbRecipe.KEY_INGREDIENTS_AMOUNT);

            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", title = " + cursor.getString(titleIndex) +
                        ", description = " + cursor.getString(descriptionIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }

        cursor.close();

        return result;
    }

}
