package com.example.recipesbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipesbook.R;
import com.example.recipesbook.RecipeItemFull;
import com.example.recipesbook.RecipeItemLater;
import com.example.recipesbook.RecipeSingleItem;
import com.example.recipesbook.db.PictureManager;
import com.example.recipesbook.models.Recipe;

import java.util.List;

public class RecipeLaterAdapter extends RecyclerView.Adapter<RecipeLaterAdapter.RecipeLaterViewHolder> {

    Context context;
    List<Recipe> recipes;
    PictureManager pictureManager;

    public RecipeLaterAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
        this.pictureManager = new PictureManager(context);
    }

    @NonNull
    @Override
    public RecipeLaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View RecipesItem = LayoutInflater.from(context).inflate(R.layout.activity_recipe_single_item, parent, false);
        return new RecipeLaterViewHolder(RecipesItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipeLaterViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.recipeTitle.setText(recipes.get(position).getTitle());
        holder.recipeDate.setText(recipes.get(position).getDate());
        Glide.with(context)
                .load(recipes.get(position).getImage())
                .into(holder.recipeImageFull);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeItemLater.class);

            intent.putExtra("recipeImage", recipes.get(position).getImage());
            intent.putExtra("recipeDate", recipes.get(position).getDate());
            intent.putExtra("recipeTitle", recipes.get(position).getTitle());
            intent.putExtra("recipeDuration", recipes.get(position).getDuration());
            intent.putExtra("recipeDescription", recipes.get(position).getDescription());
            intent.putExtra("recipeIngredients", recipes.get(position).getIngredients());
            intent.putExtra("docKey", recipes.get(position).getUserEmail());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static final class RecipeLaterViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle;
        TextView recipeDate;
        ImageView recipeImageFull;

        public RecipeLaterViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImageFull = itemView.findViewById(R.id.recipeImageFull);
            recipeDate = itemView.findViewById(R.id.recipeDate);

        }
    }

}
