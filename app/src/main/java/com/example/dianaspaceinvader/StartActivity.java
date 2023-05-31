package com.example.dianaspaceinvader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StartActivity extends AppCompatActivity {

    //google auth
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount acct;

    private StartView startView;
    private TextView txtWelcome, txtScore;
    private int score = 120;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("125323192336-vm9h0b4sb4f7nkdcab3huehu831bi7r4.apps.googleusercontent.com")
                .requestId()
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        acct = GoogleSignIn.getLastSignedInAccount(this);
        startView = new StartView(this);

        txtWelcome = findViewById(R.id.textView);
        //txtScore = findViewById(R.id)
        Button button = (Button) findViewById(R.id.startgame);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent secondActivityIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(secondActivityIntent);


            }
        });
    }


    @Override
    protected void onResume() {


        super.onResume();
        startView.resume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        startView.pause();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            txtWelcome.setText(acct.getEmail());
            //save data to firebase
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();

            myRef.child(acct.getId()).child("Score").setValue(score);
            myRef.child("GlobalScore").setValue(100);

            //get data from firebase
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int score = snapshot.child(acct.getId()).child("Score").getValue(Integer.class);
                    int globalscore = snapshot.child("GlobalScore").getValue(Integer.class);
                    if(score>globalscore){
                        myRef.child("GlobalScore").setValue(score);
                        txtWelcome.setText(new StringBuilder().append("Welcome ").append(acct.getEmail()).append("\nYou score: ").append(score).append("\nGlobal score: ").append(globalscore).toString());
                    }
                    else {
                        txtWelcome.setText(new StringBuilder().append("Welcome ").append(acct.getEmail()).append("\nYou score: ").append(score).append("\nGlobal score: ").append(globalscore).toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            signIn();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
}