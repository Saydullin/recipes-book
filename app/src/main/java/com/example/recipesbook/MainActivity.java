package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipesbook.adapters.RecipesAdapter;
import com.example.recipesbook.models.Recipe;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recipeRecycle;
    RecipesAdapter recipesAdapter;
    Drawable profileImage;
    TextView textViewResult;
    ImageButton cookLaterTab;
    ImageView button_login;
    ImageButton button_add_recipe;

    List<Recipe> getData;

    FirebaseFirestore db;

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
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        button_login = findViewById(R.id.button_login);
        button_add_recipe = findViewById(R.id.button_add_recipe);

        textViewResult = findViewById(R.id.textViewResult);

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

        eventChangeListener();
    }

    private void updateAllRecipes() {
        getRecipesByTag("soups", findViewById(R.id.recipeSoupRecycler));
        getRecipesByTag("desserts", findViewById(R.id.recipeDessertsRecycler));
        getRecipesByTag("salads", findViewById(R.id.recipeSaladsRecycler));
        getRecipesByTag("fastFood", findViewById(R.id.recipeFastFoodRecycler));
        getRecipesByTag("seafood", findViewById(R.id.recipeSeafoodRecycler));
        getRecipesByTag("drinks", findViewById(R.id.recipeDrinksRecycler));
        getRecipesByTag("authors", findViewById(R.id.recipeAuthorsRecycler));
        getRecipesByTag("other", findViewById(R.id.recipeOtherRecycler));
        getRecipesByTag("dietary", findViewById(R.id.recipeDietaryRecycler));
        getRecipesByTag("sauces", findViewById(R.id.recipeSaucesRecycler));
    }

    private void eventChangeListener() {

        db.collection("recipes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d("ErrorEVENT", "Error: " + error.getMessage());
                            return;
                        }

                        assert value != null;
                        updateAllRecipes();
                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void setRecipeRecycle(List<Recipe> recipeList, RecyclerView recyclerView) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recipeRecycle = recyclerView;
        recipeRecycle.setLayoutManager(layoutManager);

        recipesAdapter = new RecipesAdapter(this, recipeList);
        recipeRecycle.setAdapter(recipesAdapter);

    }

    private void getRecipesByTag(String tag, RecyclerView recyclerView) {
        db = FirebaseFirestore.getInstance();
        getData = new ArrayList<>();

        db.collection("recipes").whereEqualTo("tag", tag.toLowerCase()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            getData = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("SuccessQuery", tag + " => " + document.getString("tag"));
                                getData.add(new Recipe(
                                        document.getString("description"),
                                        document.getLong("duration"),
                                        document.getTimestamp("date"),
                                        document.getString("id"),
                                        document.getString("image"),
                                        document.getString("ingredients"),
                                        document.getString("tag"),
                                        document.getString("title"),
                                        document.getString("userEmail"),
                                        document.getString("userName")
                                ));
                            }
                            setRecipeRecycle(getData, recyclerView);
                        } else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}


