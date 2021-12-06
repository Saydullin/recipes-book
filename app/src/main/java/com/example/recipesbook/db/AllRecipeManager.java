//package com.example.recipesbook.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.example.recipesbook.models.Recipe;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AllRecipeManager {
//
//    AllRecipes allRecipes;
//    Context context;
//
//    public AllRecipeManager(Context context) {
//        this.context = context;
//        this.allRecipes = new AllRecipes(context);
//    }
//
////    public void add(long duration, long date, String imagePreview, String title, String ingredients, String description, String tag, String docKey) {
//    public void add(List<Recipe> data) {
//        SQLiteDatabase database = allRecipes.getWritableDatabase();
//
//        Log.d("DATASIZE", "Data size - " + data.size());
//
//        for (int i = 0; i < data.size(); i++) {
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put(MyRecipes.KEY_IMAGE, data.get(i).getImage());
//            contentValues.put(MyRecipes.KEY_TITLE, data.get(i).getTitle());
//            contentValues.put(MyRecipes.KEY_DURATION, data.get(i).getDuration());
//            contentValues.put(MyRecipes.KEY_DATE, data.get(i).getDate());
//            contentValues.put(MyRecipes.KEY_DESCRIPTION, data.get(i).getDescription());
//            contentValues.put(MyRecipes.KEY_INGREDIENTS, data.get(i).getIngredients());
//            contentValues.put(MyRecipes.KEY_TAG, data.get(i).getTag());
//            contentValues.put(MyRecipes.KEY_DOC_KEY, data.get(i).getUserEmail());
//
//            try {
//                database.insert(AllRecipes.TABLE_ALL_RECIPES, null, contentValues);
//                Toast.makeText(context, "Recipe added successfully", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(context, "Data NOT Saved: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }
//
//    public List<Recipe> get() {
//        Cursor cursor;
//
//        List<Recipe> recipeList = new ArrayList<>();
//
//        SQLiteDatabase database = allRecipes.getWritableDatabase();
//
//        cursor = database.query(AllRecipes.TABLE_ALL_RECIPES, null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(AllRecipes.KEY_ID);
//            int imageIndex = cursor.getColumnIndex(AllRecipes.KEY_IMAGE);
//            int titleIndex = cursor.getColumnIndex(AllRecipes.KEY_TITLE);
//            int dateIndex = cursor.getColumnIndex(AllRecipes.KEY_DATE);
//            int tagIndex = cursor.getColumnIndex(AllRecipes.KEY_TAG);
//            int descriptionIndex = cursor.getColumnIndex(AllRecipes.KEY_DESCRIPTION);
//            int durationIndex = cursor.getColumnIndex(AllRecipes.KEY_DURATION);
//            int ingredients = cursor.getColumnIndex(AllRecipes.KEY_INGREDIENTS);
//            int docKey = cursor.getColumnIndex(AllRecipes.KEY_DOC_KEY);
//
//            do {
//                recipeList.add(new Recipe(
//                        cursor.getString(descriptionIndex),
//                        cursor.getLong(durationIndex),
//                        cursor.getLong(dateIndex),
//                        cursor.getString(idIndex),
//                        cursor.getString(imageIndex),
//                        cursor.getString(ingredients),
//                        cursor.getString(tagIndex),
//                        cursor.getString(titleIndex),
//                        cursor.getString(docKey),
//                        "No Name"));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//
//        return recipeList;
//    }
//
//    public void deleteAdded(String docKey) {
//        SQLiteDatabase database = allRecipes.getWritableDatabase();
//
//        database.delete(AllRecipes.TABLE_ALL_RECIPES, AllRecipes.KEY_DOC_KEY + "='" + docKey + "'", null);
//    }
//
//}
//
//
