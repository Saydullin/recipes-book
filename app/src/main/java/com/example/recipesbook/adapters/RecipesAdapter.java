package com.example.recipesbook.adapters;

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
import com.example.recipesbook.RecipeItem;
import com.example.recipesbook.db.PictureManager;
import com.example.recipesbook.models.Recipe;
import java.util.List;

/**
 * The RecipesAdapter for the Application
 * @author Saydullin
 * @version 1.1
 * This is not the first Screen of the user
 */
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

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        String picturePath = "https://firebasestorage.googleapis.com/v0/b/recipes-book-1637352602907.appspot.com/o/images%2F" + recipes.get(position).getImage() + "?alt=media&token=4130be0a-d3ea-419e-b388-f25d07499f22";

        holder.recipeTitle.setText(recipes.get(position).getTitle());
        holder.recipeTitle.setText(recipes.get(position).getTitle());
        Glide.with(context)
                .load(picturePath)
                .into(holder.recipeImage);

        if (recipes.get(position).getId().equals("RECIPES_LIMIT")) {
            String authorText = "Category " + recipes.get(position).getUserName();
            holder.recipeAuthor.setText(authorText);
            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, "See more", Toast.LENGTH_SHORT).show();
            });
            Intent intent = new Intent(context, RecipeItem.class);
            intent.putExtra("tag", recipes.get(position).getUserName());
            context.startActivity(intent);
        } else {
            String authorText = "By " + recipes.get(position).getUserName();
            holder.recipeDuration.setText(recipes.get(position).getDuration().trim());
            holder.recipeAuthor.setText(authorText);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RecipeItem.class);

                intent.putExtra("recipeImage", picturePath);
                intent.putExtra("recipeAuthor", recipes.get(position).getUserName());
                intent.putExtra("recipeDate", recipes.get(position).getDate());
                intent.putExtra("recipeDateLong", String.valueOf(recipes.get(position).getDateLong()));
                intent.putExtra("recipeTitle", recipes.get(position).getTitle());
                intent.putExtra("recipeDurationLog", String.valueOf(recipes.get(position).getLongDuration()));
                intent.putExtra("recipeDuration", String.valueOf(recipes.get(position).getDuration()));
                intent.putExtra("recipeDescription", recipes.get(position).getDescription());
                intent.putExtra("recipeIngredients", recipes.get(position).getIngredients());
                intent.putExtra("recipeTag", recipes.get(position).getTag());
                intent.putExtra("recipeId", recipes.get(position).getId());

                context.startActivity(intent);
            });
        }
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

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeDuration = itemView.findViewById(R.id.recipeDuration);
            recipeAuthor = itemView.findViewById(R.id.recipeAuthor);

        }
    }

}
