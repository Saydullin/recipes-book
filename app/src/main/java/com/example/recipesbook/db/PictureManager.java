package com.example.recipesbook.db;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
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
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, "Image upload", Toast.LENGTH_LONG).show();
                        }
                    })
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

    public class GetPicture extends AsyncTask<String, Void, Uri> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public void getPictures(String fileName) {

            StorageReference pictureRef = storageReference.child("images/" + fileName);

            pictureRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    onPostExecute(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

        @Override
        protected Uri doInBackground(String... strings) {
            final Uri[] picture = new Uri[1];

            Log.d("GivenName", strings[0]);

            StorageReference pictureRef = storageReference.child("gs://recipes-book-1637352602907.appspot.com/images/" + strings[0]);

            pictureRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    picture[0] = uri;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            return picture[0];
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
        }
    }

//    public Uri getPicture(String fileName) {

//        StorageReference pictureRef = storageReference.child("images/" + fileName);
//
//        pictureRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                downloadedPicture = uri;
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//
//        return downloadedPicture;
//    }

}
