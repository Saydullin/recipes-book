package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipesbook.adapters.RecipesAdapter;
import com.example.recipesbook.db.DbRecipe;
import com.example.recipesbook.models.Recipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recipeRecycle;
    RecyclerView recipeRecycle2;
    RecyclerView recipeRecycle3;
    RecipesAdapter recipesAdapter;
    private TextView textViewResult;
    ImageButton filterSearchView;
    ImageButton button_login;
    ImageButton button_add_recipe;

    DbRecipe dbRecipe;

    @Override
    protected void onStart() {
        super.onStart();

        setRecipes();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterSearchView = findViewById(R.id.filterSearchView);
        button_login = findViewById(R.id.button_login);
        button_add_recipe = findViewById(R.id.button_add_recipe);

        button_login.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);
        });

        button_add_recipe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipe.class);
            startActivity(intent);
        });

    }

    private void setRecipeRecycle(List<Recipe> recipeList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle = findViewById(R.id.recipeRecycle);
        recipeRecycle.setLayoutManager(layoutManager);

        recipesAdapter = new RecipesAdapter(this, recipeList);
        recipeRecycle.setAdapter(recipesAdapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle2 = findViewById(R.id.recipeRecycle2);
        recipeRecycle2.setLayoutManager(layoutManager2);

        recipeRecycle2.setAdapter(recipesAdapter);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle3 = findViewById(R.id.recipeRecycle3);
        recipeRecycle3.setLayoutManager(layoutManager3);

        recipeRecycle3.setAdapter(recipesAdapter);
    }

    public void setRecipes() {

        dbRecipe = new DbRecipe(this);

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase database = dbRecipe.getWritableDatabase();

        Cursor cursor = database.query(DbRecipe.TABLE_RECIPES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbRecipe.KEY_ID);
            int imageIndex = cursor.getColumnIndex(DbRecipe.KEY_IMAGE);
            int titleIndex = cursor.getColumnIndex(DbRecipe.KEY_TITLE);
            int descriptionIndex = cursor.getColumnIndex(DbRecipe.KEY_DESCRIPTION);
            int durationIndex = cursor.getColumnIndex(DbRecipe.KEY_DURATION);
            int ingredientsAmountIndex = cursor.getColumnIndex(DbRecipe.KEY_INGREDIENTS_AMOUNT);
            int duration;
            StringBuilder durationFormat = new StringBuilder();

            do {
                duration = cursor.getInt(durationIndex);
                if (duration / 60 >= 1) {
                    durationFormat.append(duration / 60).append("h ");
                }
                durationFormat.append(duration % 60).append("min");

                recipeList.add(new Recipe(
                        cursor.getInt(idIndex),
                        cursor.getInt(ingredientsAmountIndex),
                        cursor.getString(titleIndex),
                        durationFormat.toString(),
                        cursor.getString(imageIndex),
                        cursor.getString(descriptionIndex)));

                durationFormat.delete(0, durationFormat.length());
            } while (cursor.moveToNext());
        }

        cursor.close();

        recipeList.add(new Recipe(1,
                12,
                "Cherry Winks",
                "15min",
                "https://c4.wallpaperflare.com/wallpaper/869/719/717/cuisine-food-india-indian-wallpaper-preview.jpg",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text."));

        recipeList.add(new Recipe(2,
                7,
                "Barbie Shot",
                "40min",
                "https://img.delo-vcusa.ru/2016/12/10-shikarnyh-goryachih-blyud-na-noviy-god.jpg",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text."));

        recipeList.add(new Recipe(3,
                5,
                "Antelope Goulash",
                "30min",
                "https://p4.wallpaperbetter.com/wallpaper/797/364/1014/food-plates-wooden-surface-still-life-wallpaper-preview.jpg",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text."));
        setRecipeRecycle(recipeList);
    }

    private class GetDatas extends AsyncTask<String, String, String> {

        @SuppressLint("SetTextI18n")
        protected void onPreExecute() {
            super.onPreExecute();
            textViewResult.setText("Wait...");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();
            } catch (MalformedURLException e) {
                textViewResult.setText("Something went wrong 1");
            } catch (IOException e) {
                e.printStackTrace();
                textViewResult.setText("Something went wrong" + System.err);
            } finally {
                if (connection != null)
                    connection.disconnect();

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                textViewResult.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
                textViewResult.setText("Ops...");
            }
        }

    }

}


