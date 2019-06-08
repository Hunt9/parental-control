package com.example.dell.androidhive;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nephi.getoffyourphone.Main;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class LoginActivity extends AppCompatActivity {

    String[] appPermissions = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CALL_PRIVILEGED,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,

            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };

    private int PERMISSION_REQUEST_CODE = 1;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnForget;
    private DatabaseReference database;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkAndRequestPermissions()){

            Intent intent = new Intent(this, Main.class);
            startActivity(intent);

            /**
             * Permission Check  */
        }

        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() != null) {

            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance().getReference();

            database.child("Users")
                    .child("Parents")
                    .child(userID) // Create a reference to the child node directly
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This callback will fire even if the node doesn't exist, so now check for existence
                            if (dataSnapshot.exists()) {

                                /*```````````````````````````````` TESTING ``````````````````````````````````````*/
//                                FirebaseDatabase.getInstance().getReference("Users").child("Current Parent").child("ID").setValue(userID);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                                /*``````````````````````````````````````````````````````````````````````````````*/

                                System.out.println("The node exists.");
                                System.out.println("Parent");
                            } else {

//                                FirebaseDatabase.getInstance().getReference("Users").child("Current Child").child("ID").setValue(userID);

                                Intent intent = new Intent(LoginActivity.this, ChildrenMainActivity.class);
                                startActivity(intent);
                                finish();
                                System.out.println("The node does not exist.");
                                System.out.println("Child");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });

/////////////////////////////////////////////////////////bhai yahan na yeh dekhna hay kay kon login hay chil ya parent agar parent hay tou yehi intent lagayga agar child hay tou childrenmain activity ka intent session jab lagayga
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
        }

        setContentView(R.layout.activity_login);


        Log.i("MESSAGE","LOGIN ACTIVITY");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnForget = findViewById(R.id.btn_forgetPass);

        //Get Firebase auth instance
        auth = getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!inputEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(inputEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    inputEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
//                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                     userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    database = FirebaseDatabase.getInstance().getReference();

                                    database.child("Users")
                                            .child("Parents")
                                            .child(userID) // Create a reference to the child node directly
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // This callback will fire even if the node doesn't exist, so now check for existence
                                                    if (dataSnapshot.exists()) {

                                                        /*```````````````````````````````` TESTING ``````````````````````````````````````*/
                                                        FirebaseDatabase.getInstance().getReference("Users").child("Current Parent").child("ID").setValue(userID);
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                        /*``````````````````````````````````````````````````````````````````````````````*/

                                                        System.out.println("The node exists.");
                                                        System.out.println("Parent");
                                                    } else {

                                                        FirebaseDatabase.getInstance().getReference("Users").child("Current Child").child("ID").setValue(userID);

                                                        Intent intent = new Intent(LoginActivity.this, ChildrenMainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                        System.out.println("The node does not exist.");
                                                        System.out.println("Child");
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) { }
                                            });

                                }

                                else {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }

                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });
    }

    public boolean checkAndRequestPermissions(){
        List<String> listpermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions){
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED){
                listpermissionsNeeded.add(perm);
            }
        }

        if (!listpermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    listpermissionsNeeded.toArray(new String[listpermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
}