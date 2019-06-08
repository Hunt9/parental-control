package com.example.dell.androidhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nephi.getoffyourphone.Main;

public class AfterChildClick extends AppCompatActivity {

    Button callLog, msgLog, history, lockApp, back;
    Bundle bundle;

    public static String childID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_child_click);

        callLog = findViewById(R.id.B_callLog);
        lockApp = findViewById(R.id.B_appLock);
//        history = findViewById(R.id.B_history);
        msgLog = findViewById(R.id.B_msg);
        back = findViewById(R.id.B_back);

        Intent intentExtras = getIntent();
        bundle = intentExtras.getExtras();

        if(bundle != null)
        {
            childID = bundle.getString("CID");

        }


//        Toast.makeText(this, childID, Toast.LENGTH_SHORT).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterChildClick.this, ViewChildActivity.class);
                startActivity(intent);
                finish();
            }
        });

        callLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterChildClick.this, ChildCallLogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lockApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterChildClick.this, ChildAppsActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AfterChildClick.this, ChildBHistoryActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        msgLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterChildClick.this, ChildMsgLogActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
