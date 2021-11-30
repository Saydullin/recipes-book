package com.example.recipesbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipesbook.db.FirebaseManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * UserProfile, where user can see his information
 */

public class UserProfile extends AppCompatActivity {

    final static private String USER_DATA_PREFS = "userData";

    GoogleSignInClient mGoogleSignInClient;
    ImageView recipeImagePreview;
    TextView userName;
    TextView userEmail;
    Button change_accounts;
    LinearLayout no_signed_in;
    LinearLayout signed_in;
    Button sign_in;
    Button sign_out;
    int RC_SIGN_IN = 0;
    Button button_cancel_activity;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personImage = acct.getPhotoUrl();
            userName.setText(personName);
            userEmail.setText(personEmail);
            Glide.with(this).load(String.valueOf(personImage)).into(recipeImagePreview);
        } else {
            no_signed_in.setVisibility(View.VISIBLE);
            signed_in.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        no_signed_in = findViewById(R.id.no_signed_in);
        signed_in = findViewById(R.id.signed_in);

        change_accounts = findViewById(R.id.change_accounts_button);
        sign_out = findViewById(R.id.sign_out_button);
        sign_in = findViewById(R.id.sign_in_button);

        button_cancel_activity = findViewById(R.id.button_cancel_activity);
        button_cancel_activity.setOnClickListener(v -> finish());

        recipeImagePreview = findViewById(R.id.recipeImagePreview);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);

        change_accounts.setOnClickListener(v -> {
            if (v.getId() == R.id.change_accounts_button) {
                switchAccounts();
            }
        });

        sign_in.setOnClickListener(v -> {
            if (v.getId() == R.id.sign_in_button) {
                signIn();
            }
        });

        sign_out.setOnClickListener(v -> {
            if (v.getId() == R.id.sign_out_button) {
                signOut();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void switchAccounts() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences.Editor editor = getSharedPreferences(USER_DATA_PREFS, MODE_PRIVATE).edit();

                        editor.remove("name");
                        editor.remove("email");
                        editor.remove("image");
                        editor.remove("id");
                        editor.apply();
                        Toast.makeText(UserProfile.this, "Sign out successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            finish();

            SharedPreferences.Editor editor = getSharedPreferences(USER_DATA_PREFS, MODE_PRIVATE).edit();

            String userPhoto = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

            editor.putString("name", account.getDisplayName());
            editor.putString("email", account.getEmail());
            editor.putString("image", userPhoto);
            editor.putString("id", account.getId());
            editor.apply();
            Toast.makeText(this, "Authenticate Successfully", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            if (e.getStatusCode() != 12501) {
                Toast.makeText(this, "log: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}


