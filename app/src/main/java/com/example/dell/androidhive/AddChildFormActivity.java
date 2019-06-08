package com.example.dell.androidhive;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

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

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class AddChildFormActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    private Button buttonBack, buttonRegisterChld;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;
    private EditText editName, editPassword, editEmail, editAge, editPhone;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;

    DatabaseReference currentParentRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Current Parent").child("ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_form);

        buttonBack = findViewById(R.id.button_Back);
        buttonRegisterChld = findViewById(R.id.button_RegisterChild);
        editName = findViewById(R.id.editText_Name);
        editAge = findViewById(R.id.editText_Age);
        editPhone = findViewById(R.id.editText_Phone);
        editEmail = findViewById(R.id.editText_Email);
        editPassword = findViewById(R.id.editText_Password);

        if (checkPermission(Manifest.permission.SEND_SMS)){
            Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddChildFormActivity.this, ViewChildActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegisterChld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentParentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String cid = dataSnapshot.getValue(String.class);

                        addFunc(cid);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void addFunc(String cid){
//        System.out.println("``````````` C-ID 1 +++```` "+ cid);
        final String mEmail = editEmail.getText().toString().trim();
        final String mName = editName.getText().toString().trim();
        final String mPhone = editPhone.getText().toString().trim();
        final String mAge = editAge.getText().toString().trim();
        final String mPassword = editPassword.getText().toString().trim();

        final String parentID = cid;

        auth = getInstance();
        auth.createUserWithEmailAndPassword(mEmail,mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(mEmail,mName, mPhone,mAge);

                            message ="Hello, "+ mName + " here is your E-mail: "+mEmail+", and Password: "+mPassword + ", for Secure Child Application";
                            onSend(mPhone, message);


                            //Add on Parents Node
                            FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Parents/" + parentID)
                                    .child("Children").child(getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddChildFormActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                        Toast.makeText(AddChildFormActivity.this, "Not Successful", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //Add in Children Node
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child("Children").child(getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddChildFormActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                                    } else {
                                        //display a failure message
                                        Toast.makeText(AddChildFormActivity.this, "Not Successful", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
//                            auth.signOut();
                            startActivity(new Intent(AddChildFormActivity.this, ViewChildActivity.class));
                            finish();

                        } else {
                            Toast.makeText(AddChildFormActivity.this, "Authentication failed."/* + task.getException()*/,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Intent intent = new Intent(AddChildFormActivity.this, ViewChildActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSend(String phoneNumber, String smsMessage){
//        String phoneNumber = number.getText().toString();
//        String smsMessage = message.getText().toString();

        if (phoneNumber == null || phoneNumber.length() == 0 ||
                smsMessage == null || smsMessage.length() == 0){
            return;
        }
        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null, smsMessage,null, null);
            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }

        return;
    }

    public Boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
