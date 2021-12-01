package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.recipesbook.adapters.RecipeLaterAdapter;
import com.example.recipesbook.db.RecipeManager;
import com.example.recipesbook.models.Recipe;

import java.util.List;

public class BasketActivity extends AppCompatActivity {

    Button cancelButton;
    RecyclerView recipeRecycle;
    RecipeLaterAdapter recipeLaterAdapter;
    RecipeManager recipeManager;
    List<Recipe> recipeList;

    @Override
    protected void onStart() {
        super.onStart();

        updateRecycler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        recipeRecycle = findViewById(R.id.myRecipesRecyclerLater);
        cancelButton = findViewById(R.id.button_cancel_activity);

        cancelButton.setOnClickListener(v -> finish());
    }

    public void updateRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recipeRecycle.setLayoutManager(layoutManager);

        recipeManager = new RecipeManager(this);

        recipeList = recipeManager.getFromLater();

        recipeLaterAdapter = new RecipeLaterAdapter(this, recipeList);
        recipeRecycle.setAdapter(recipeLaterAdapter);
    }

}


