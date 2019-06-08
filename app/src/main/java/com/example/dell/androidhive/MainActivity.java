package com.example.dell.androidhive;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.nephi.getoffyourphone.Main;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
//    Main AppMain;

    private Button signOut, viewChild_button , btnRemoveUser,btncalllog,btnHistory,btngetmsg,btnlock;
    private TextView currentEmail;
    private ProgressBar progressBar;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
//    private String cEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

//        currentEmail = (TextView) findViewById(R.id.current_parent_email);
        signOut = findViewById(R.id.sign_out2);
        viewChild_button = findViewById(R.id.button_view_child);

        /***/
//        btncalllog = findViewById(R.id.call_log);
//        btnHistory = findViewById(R.id.Bhistory);
//        btngetmsg = findViewById(R.id.getmsg);
        /***/
//        btnlock = findViewById(R.id.applock);
//        btnRemoveUser = (Button) findViewById(R.id.btnRemoveUser) ;

//        cEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

//        currentEmail.setText(cEmail);




        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity( new Intent(this,LoginActivity.class));
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Current Parent").setValue(null);

                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
//
//        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                if (user != null) {
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//
//                                        Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(MainActivity.this, SignupActivity.class));
//                                        finish();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                }
//            }
//        });

        viewChild_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewChildActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        btnHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, BrowserHistory.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btncalllog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CustomCallLog.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        btngetmsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, GetMessage.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        btnlock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Main.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
