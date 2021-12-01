package com.example.recipesbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipesbook.adapters.RecipeLaterAdapter;
import com.example.recipesbook.db.RecipeManager;
import com.example.recipesbook.models.Recipe;

import java.util.List;

public class BasketActivity extends AppCompatActivity {

    Button cancelButton;
    RecyclerView recipeRecycle;
    RecipeLaterAdapter recipeLaterAdapter;
    TextView captionCookLater;
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
        captionCookLater = findViewById(R.id.captionCookLater);

        cancelButton.setOnClickListener(v -> finish());
    }

    public void updateRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recipeRecycle.setLayoutManager(layoutManager);

        recipeManager = new RecipeManager(this);

        recipeList = recipeManager.getFromLater();
        recipeRecycle.setVisibility(View.GONE);
        captionCookLater.setVisibility(View.VISIBLE);
        if (recipeList.size() > 0) {
            recipeRecycle.setVisibility(View.VISIBLE);
            captionCookLater.setVisibility(View.GONE);
        }

        recipeLaterAdapter = new RecipeLaterAdapter(this, recipeList);
        recipeRecycle.setAdapter(recipeLaterAdapter);
    }

}


