package com.example.recipesbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AllRecipes extends SQLiteOpenHelper {

    Context context;

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "AllRecipesDB";
    public static final String TABLE_ALL_RECIPES = "all_recipes";

    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DESCRIPTION = "name";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_DATE = "date";
    public static final String KEY_TAG = "tag";
    public static final String KEY_DOC_KEY = "doc";

    public AllRecipes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ALL_RECIPES + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_IMAGE + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_DURATION + " LONG," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_INGREDIENTS + " TEXT," +
                KEY_DATE + " LONG," +
                KEY_TAG + " TEXT," +
                KEY_DOC_KEY + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_RECIPES);

        onCreate(db);
    }

}


