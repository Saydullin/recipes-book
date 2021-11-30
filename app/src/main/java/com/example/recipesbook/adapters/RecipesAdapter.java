package com.example.recipesbook.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.recipesbook.db.PictureManager;
import com.example.recipesbook.models.Recipe;

import java.io.CharArrayWriter;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    Context context;
    List<Recipe> recipes;
    PictureManager pictureManager;

    public RecipesAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
        this.pictureManager = new PictureManager(context);
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

        String picturePath = "https://firebasestorage.googleapis.com/v0/b/recipes-book-1637352602907.appspot.com/o/images%2F" + recipes.get(position).getImage() + "?alt=media&token=4130be0a-d3ea-419e-b388-f25d07499f22";

        holder.recipeTitle.setText(recipes.get(position).getTitle());
        holder.recipeAuthor.setText("By " + recipes.get(position).getUserName());
        Glide.with(context)
                .load(picturePath)
                .into(holder.recipeImage);
        holder.recipeDuration.setText(recipes.get(position).getDuration().trim() + " of cooking");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeItem.class);

            intent.putExtra("recipeImage", picturePath);
            intent.putExtra("recipeAuthor", recipes.get(position).getUserName());
            intent.putExtra("recipeDate", recipes.get(position).getDate());
            intent.putExtra("recipeTitle", recipes.get(position).getTitle());
            intent.putExtra("recipeDuration", recipes.get(position).getDuration());
            intent.putExtra("recipeDescription", recipes.get(position).getDescription());
            intent.putExtra("recipeIngredients", recipes.get(position).getIngredients());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static final class RecipesViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle;
        TextView recipeDuration;
        TextView recipeAuthor;
        ImageView recipeImage;
//        CardView recipeImageCard;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeDuration = itemView.findViewById(R.id.recipeDuration);
            recipeAuthor = itemView.findViewById(R.id.recipeAuthor);
//            recipeImageCard = itemView.findViewById(R.id.CardImage);
//            recipesIngredientsAmount = itemView.findViewById((R.id.recipesIngredientsAmount));

        }
    }

}
