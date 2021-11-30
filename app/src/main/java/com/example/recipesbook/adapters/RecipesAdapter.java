package com.example.recipesbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
//        getPicture.execute(recipes.get(position).getImage());

//        Uri imageUri = pictureManager.getPicture(recipes.get(position).getImage());

        holder.recipeTitle.setText(recipes.get(position).getTitle());
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/recipes-book-1637352602907.appspot.com/o/images%2F" + recipes.get(position).getImage() + "?alt=media&token=4130be0a-d3ea-419e-b388-f25d07499f22")
                .into(holder.recipeImage);
//        holder.recipeImage.setImageURI(Uri.parse(recipes.get(position).getImg()));
        holder.recipeDuration.setText(String.valueOf(recipes.get(position).getDuration()));
//        holder.recipesIngredientsAmount.setText(recipes.get(position).getIngredientsAmount() + " ingredients");

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, RecipeItem.class);
//
////            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
////                    (Activity) context,
////                    new Pair<View, String>(holder.recipeImageCard, "ImageTransform")
////            );
//
//            intent.putExtra("recipeImage", recipes.get(position).getImage());
//            intent.putExtra("recipeTitle", recipes.get(position).getTitle());
//            intent.putExtra("recipeDuration", recipes.get(position).getDuration());
////            intent.putExtra("recipeIngredients", recipes.get(position).getIngredientsAmount());
//            intent.putExtra("recipeDescription", recipes.get(position).getDescription());
//
////            context.startActivity(intent, options.toBundle());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static final class RecipesViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTitle;
        TextView recipeDuration;
//        TextView recipesIngredientsAmount;
        ImageView recipeImage;
//        CardView recipeImageCard;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeDuration = itemView.findViewById(R.id.recipeDuration);
//            recipeImageCard = itemView.findViewById(R.id.CardImage);
//            recipesIngredientsAmount = itemView.findViewById((R.id.recipesIngredientsAmount));

        }
    }

}
