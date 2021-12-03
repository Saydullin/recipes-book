package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipesbook.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TagListRecipes extends AppCompatActivity {

    FirebaseFirestore db;
    TextView tagTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list_recipes);

        tagTitle = findViewById(R.id.tagTitle);

        String intentTag = getIntent().getStringExtra("tag");

        tagTitle.setText(intentTag);

        getAllRecipesByTag(intentTag);
    }

    public void getAllRecipesByTag(String tag) {
        db = FirebaseFirestore.getInstance();
        db.collection("recipes").whereEqualTo("tag", tag.toLowerCase()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(TagListRecipes.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}