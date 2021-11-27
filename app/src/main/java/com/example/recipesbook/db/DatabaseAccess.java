package com.example.recipesbook.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recipesbook.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static  DatabaseAccess instance;
    Cursor c = null;
    Context context;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public List<Recipe> getRecipesByTag(String tag) {
        List<Recipe> recipeList = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM recipes WHERE tag='" + tag + "'", null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                int ingredientsAmount = c.getInt(4);
                int duration = c.getInt(3);
                String title = c.getString(1);
                String tagName = c.getString(6);
                String image = c.getString(2);
                String description = c.getString(5);
                Log.w("Error", "ID: " + id);
                Log.w("Error", "TAG: " + tagName);
                Log.w("Error", "TITLE: " + title);
                Log.w("Error", "IMAGE: " + image);
                Log.w("Error", "DURATION: " + duration);
                Log.w("Error", "DESCRIPTION: " + description);
                Log.w("Error", "INGREDIENTSAMOUNT: " + ingredientsAmount);
                recipeList.add(new Recipe(
                        id,
                        ingredientsAmount,
                        duration,
                        title,
                        image,
                        description));
            } while(c.moveToNext());
        }

        return recipeList;
    }

}
