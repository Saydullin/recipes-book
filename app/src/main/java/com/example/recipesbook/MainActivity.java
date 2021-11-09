package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.recipesbook.adapters.RecipesAdapter;
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
    ImageButton button_login;
    ImageButton button_add_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecipes();

        button_login = findViewById(R.id.button_login);
        button_add_recipe = findViewById(R.id.button_add_recipe);

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

//        new GetDatas().execute("https://jsonplaceholder.typicode.com/users/1");

        List<Recipe> recipeList = new ArrayList<>();

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

        recipeList.add(new Recipe(4,
                9,
                "Seminary Muffins",
                "1h 30min",
                "https://c1.wallpaperflare.com/preview/161/933/181/barbecue-cooking-delicious-dinner.jpg",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text."));

        recipeList.add(new Recipe(5,
                14,
                "Funeral Pie",
                "2h 30min",
                "https://images.unsplash.com/photo-1602192103300-47e66756152e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8anVuayUyMGZvb2R8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text."));

        recipeList.add(new Recipe(6,
                17,
                "Mississippi Six",
                "1h",
                "https://images.unsplash.com/photo-1609167830220-7164aa360951?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8ZmFzdGZvb2R8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80",
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


