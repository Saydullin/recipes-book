package com.example.recipesbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.recipesbook.MainActivity;

public class DbRecipe extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "RecipesDB";
    public static final String TABLE_RECIPES = "recipes";

    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DESCRIPTION = "name";
    public static final String KEY_INGREDIENTS_AMOUNT = "ingredients_amount";
    public static final String KEY_MAIL = "email";

    public DbRecipe(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_IMAGE + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_DURATION + " INTEGER," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_INGREDIENTS_AMOUNT + " TEXT," +
                KEY_MAIL + " TEXT" +
            ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        onCreate(db);
    }

}
