package com.example.recipesbook.db;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.recipesbook.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {

    Context context;
    FirebaseFirestore db;
    String errorMessage;
    Map<String, Object> result;

    public FirebaseManager(Context context) {
        this.context = context;

        this.result = new HashMap<>();
        this.result.put("ok", "true");
        this.result.put("description", "Success");
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

}


