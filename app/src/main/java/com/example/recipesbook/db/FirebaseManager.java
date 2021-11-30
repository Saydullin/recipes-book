package com.example.recipesbook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.recipesbook.MainActivity;
import com.example.recipesbook.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    Context context;
    FirebaseFirestore db;
    String errorMessage;
    Map<String, Object> result;
    List<Recipe> recipeList;
    List<Recipe> getData;

    public FirebaseManager(Context context) {
        this.context = context;

        this.result = new HashMap<>();
        this.result.put("ok", "true");
        this.result.put("description", "Success");
        this.recipeList = new ArrayList<>();
    }

    public Map<String, Object> add(Map<String, Object> data, String collectionPath) {

        db = FirebaseFirestore.getInstance();
        db.collection(collectionPath)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(@NonNull DocumentReference documentReference) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                errorMessage = e.getMessage();
                result.replace("ok", "false");
                result.replace("description", e.getMessage());
            }
        });

        return result;
    }

    public List<Recipe> get(String tag) {

        db = FirebaseFirestore.getInstance();
        getData = new ArrayList<>();

        db.collection("recipes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("SuccessQuery", document.getId() + " => " + document.getString("title"));
                                getData.add(new Recipe(
                                        document.getString("description"),
                                        document.getLong("duration"),
                                        document.getString("id"),
                                        document.getString("image"),
                                        document.getString("ingredients"),
                                        document.getString("tag"),
                                        document.getString("title"),
                                        document.getString("userEmail"),
                                        document.getString("userName")
                                ));
                            }

//                            smthGo(getData);
                        } else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

//        db.collection("recipes")
//                .whereEqualTo("tag", tag)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<Recipe> recipeListItem = new ArrayList<>();
//                        Map<String, Object> data;
//                        int duration;
//                        int ingAmount;
//                        String id;
//                        String title;
//                        String image;
//                        String description;
//
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                Log.d("Error", document.getId() + " => " + document.getData());
////                                Log.d("ErrorDouble", document.getData().get("duration") + "");
//                                data = document.getData();
//                                id = (String) document.getId();
////                                ingAmount = Integer.parseInt((String) Objects.requireNonNull(data.get("ingAmount")));
//                                ingAmount = 12;
////                                duration = Integer.parseInt((String) Objects.requireNonNull(data.get("duration")));
//                                duration = 90;
//                                title = Objects.requireNonNull(data.get("duration")).toString();
//                                image = Objects.requireNonNull(data.get("image")).toString();
//                                description = Objects.requireNonNull(data.get("description")).toString();
//                                recipeList.add(new Recipe(
//                                        id,
//                                        ingAmount,
//                                        duration,
//                                        title,
//                                        image,
//                                        description));
//
//                                Log.d("ErrorDouble", "[FOR] IS LIST EMPTY - " + recipeList.isEmpty());
//                            }
//                        } else {
//                            Log.d("Error", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        return recipeList;
    }

}














