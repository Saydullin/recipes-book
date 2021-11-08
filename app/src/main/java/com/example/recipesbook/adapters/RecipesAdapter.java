package com.example.recipesbook.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipesbook.R;
import com.example.recipesbook.RecipeItem;
import com.example.recipesbook.models.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    Context context;
    List<Recipe> recipes;

    public RecipesAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View RecipesItem = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(RecipesItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recipeTitle.setText(recipes.get(position).getTitle());
        Glide.with(context)
                .load(recipes.get(position).getImg())
                .into(holder.recipeImage);
        holder.recipeDuration.setText(recipes.get(position).getDuration());
        holder.recipesIngredientsAmount.setText(recipes.get(position).getIngredientsAmount() + " ingredients");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeItem.class);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) context,
                    new Pair<View, String>(holder.recipeImageCard, "ImageTransform")
            );

            intent.putExtra("recipeImage", recipes.get(position).getImg());
            intent.putExtra("recipeTitle", recipes.get(position).getTitle());
            intent.putExtra("recipeDuration", recipes.get(position).getDuration());
            intent.putExtra("recipeIngredients", recipes.get(position).getIngredientsAmount());
            intent.putExtra("recipeDescription", recipes.get(position).getDescription());

            context.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static final class RecipesViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle;
        TextView recipeDuration;
        TextView recipesIngredientsAmount;
        ImageView recipeImage;
        CardView recipeImageCard;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeDuration = itemView.findViewById(R.id.recipeDuration);
            recipeImageCard = itemView.findViewById(R.id.CardImage);
            recipesIngredientsAmount = itemView.findViewById((R.id.recipesIngredientsAmount));

        }
    }

}
