package com.example.recipesbook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.recipesbook.MainActivity;
import com.example.recipesbook.models.Recipe;
import com.example.recipesbook.utils.SearchData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetData {

    Context context;
    RecipeManager recipeManager;
    FirebaseFirestore db;
    List<Recipe> getData;

    public GetData(Context context) {
        this.context = context;
    }

    public void all() {
        db = FirebaseFirestore.getInstance();
        recipeManager = new RecipeManager(context);
        getData = new ArrayList<>();

        Runnable runnable = () -> db.collection("recipes").get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        getData = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            getData.add(new Recipe(
                                    document.getString("description"),
                                    document.getLong("duration"),
                                    document.getLong("date"),
                                    document.getString("id"),
                                    document.getString("image"),
                                    document.getString("ingredients"),
                                    document.getString("tag"),
                                    document.getString("title"),
                                    document.getString("userEmail"),
                                    document.getString("userName")
                            ));
                        }
                        recipeManager.addToAll(getData);
                        Toast.makeText(context, "All data got successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        Thread thread = new Thread(runnable);
        thread.start();

    }
}
