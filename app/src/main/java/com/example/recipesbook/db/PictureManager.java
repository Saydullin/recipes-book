package com.example.recipesbook.db;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PictureManager {

    Context context;
    FirebaseStorage storage;
    StorageReference storageReference;

    public PictureManager(Context context) {
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
        this.storageReference = storage.getReference();
    }

    public String addPicture(Uri imageUri, String fileName) {

        String randomKey = UUID.randomUUID().toString();

        try {
            if (fileName != null) {
                randomKey = fileName;
            }

            StorageReference mountainsRef = storageReference.child("images/" + randomKey);

            mountainsRef.putFile(imageUri)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Image failed", Toast.LENGTH_LONG).show();
                        }
                    });

            return randomKey;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return "";
    }

}
