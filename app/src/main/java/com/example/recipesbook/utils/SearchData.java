package com.example.recipesbook.utils;

import android.content.Context;

import com.example.recipesbook.models.Recipe;

import java.util.List;

public class SearchData {

    Context context;

    public SearchData(Context context) {
        this.context = context;
    }

    public Recipe searchByTitle(String keyword, List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equals(keyword)) {
                return recipe;
            }
        }
        return null;
    }


}
