package com.example.recipesbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CookLater extends SQLiteOpenHelper {

    Context context;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "CookLaterDB";
    public static final String TABLE_RECIPES = "recipes";

    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_INGREDIENTS_AMOUNT = "ingredients_amount";
    public static final String KEY_TAG = "tag";

    //  tags
    public static final String TAG_GENERAL = "all";
    public static final String TAG_SALADS = "salads";
    public static final String TAG_SOUPS = "soups";
    public static final String TAG_DESERTS = "deserts";
    public static final String TAG_DIET = "diet";

    public CookLater(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
                KEY_TAG + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        onCreate(db);
    }

}
