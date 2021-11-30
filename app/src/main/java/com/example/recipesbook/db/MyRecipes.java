package com.example.recipesbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyRecipes extends SQLiteOpenHelper {

    Context context;

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "MyRecipesDB";
    public static final String TABLE_ADDED_RECIPES = "recipes";
    public static final String TABLE_COOK_LATER_RECIPES = "cook_later";

    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DESCRIPTION = "name";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_DATE = "date";
    public static final String KEY_TAG = "tag";
    public static final String KEY_DOC_KEY = "doc";

    //  tags
    public static final String TAG_GENERAL = "all";
    public static final String TAG_SALADS = "salads";
    public static final String TAG_SOUPS = "soups";
    public static final String TAG_DESERTS = "deserts";
    public static final String TAG_DIET = "diet";

    public MyRecipes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ADDED_RECIPES + "(" +
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
        db.execSQL("CREATE TABLE " + TABLE_COOK_LATER_RECIPES + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_IMAGE + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_DURATION + " LONG," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_INGREDIENTS + " TEXT," +
                KEY_DATE + " LONG," +
                KEY_TAG + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDED_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COOK_LATER_RECIPES);

        onCreate(db);
    }

}
