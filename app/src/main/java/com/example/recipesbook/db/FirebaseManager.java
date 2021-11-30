package com.example.recipesbook.db;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.recipesbook.models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public Map<String, Object> add(Map<String, Object> data, String collectionPath, String docKey) {

        db = FirebaseFirestore.getInstance();
        db.collection(collectionPath)
                .document(docKey)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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

//    public Map<String, Object> add(Map<String, Object> data, String collectionPath, String docKey) {
//
//        db = FirebaseFirestore.getInstance();
//        db.collection(collectionPath)
//                .add(data)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(@NonNull DocumentReference documentReference) {
//                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                errorMessage = e.getMessage();
//                result.replace("ok", "false");
//                result.replace("description", e.getMessage());
//            }
//        });
//
//        return result;
//    }

    public List<Recipe> get(String tag) {

        db = FirebaseFirestore.getInstance();
        getData = new ArrayList<>();

        db.collection("recipes").get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (DocumentSnapshot document : task.getResult()) {
                            Log.d("SuccessQuery", document.getId() + " => " + document.getString("title"));
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

                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }

                });

        return recipeList;
    }

}














