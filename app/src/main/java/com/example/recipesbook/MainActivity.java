package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipesbook.adapters.RecipesAdapter;
import com.example.recipesbook.db.DatabaseAccess;
import com.example.recipesbook.db.DbRecipe;
import com.example.recipesbook.db.RecipeManager;
import com.example.recipesbook.models.Recipe;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recipeRecycle;
    RecyclerView recipeRecycle2;
    RecyclerView recipeRecycle3;
    RecipesAdapter recipesAdapter;
    Drawable profileImage;
    private TextView textViewResult;
    ImageButton filterSearchView;
    ImageButton cookLaterTab;
    ImageView button_login;
    ImageButton button_add_recipe;

    GoogleSignInClient mGoogleSignInClient;

    DbRecipe dbRecipe;

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Uri personImage = acct.getPhotoUrl();
            Glide.with(this).load(String.valueOf(personImage)).into(button_login);
        } else {
            profileImage = ContextCompat.getDrawable(this, R.drawable.profile);
            button_login.setImageDrawable(profileImage);
        }

        setRecipes();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);

        filterSearchView = findViewById(R.id.filterSearchView);
        button_login = findViewById(R.id.button_login);
        button_add_recipe = findViewById(R.id.button_add_recipe);

        cookLaterTab = findViewById(R.id.cookLaterTab);

        cookLaterTab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BasketActivity.class);
            startActivity(intent);
        });

        button_login.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);
        });

        button_add_recipe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipe.class);
            startActivity(intent);
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void setRecipeRecycle(List<Recipe> recipeLists) {
        dbRecipe = new DbRecipe(this);

        List<Recipe> recipeList;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        RecipeManager recipeManager = new RecipeManager(this);

//      Получение всех рецептов с названием тега в аргументах метода get().

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle = findViewById(R.id.recipeRecycle);
        recipeRecycle.setLayoutManager(layoutManager);

        recipeList = databaseAccess.getRecipesByTag("soups");
        recipesAdapter = new RecipesAdapter(this, recipeList);
        recipeRecycle.setAdapter(recipesAdapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle2 = findViewById(R.id.recipeRecycle2);
        recipeRecycle2.setLayoutManager(layoutManager2);

        recipeList = databaseAccess.getRecipesByTag("desserts");
        recipesAdapter = new RecipesAdapter(this, recipeList);
        recipeRecycle2.setAdapter(recipesAdapter);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle3 = findViewById(R.id.recipeRecycle3);
        recipeRecycle3.setLayoutManager(layoutManager3);

        recipeList = databaseAccess.getRecipesByTag("salads");
        recipesAdapter = new RecipesAdapter(this, recipeList);
        recipeRecycle3.setAdapter(recipesAdapter);

        databaseAccess.close();
    }

    public void setRecipes() {

        dbRecipe = new DbRecipe(this);

        List<Recipe> recipeList = new ArrayList<>();

//        RecipeManager recipeManager = new RecipeManager(this);

//      Получение всех рецептов с названием тега в аргументах метода get().
//        recipeList = recipeManager.get("all");


        setRecipeRecycle(recipeList);
    }
}


