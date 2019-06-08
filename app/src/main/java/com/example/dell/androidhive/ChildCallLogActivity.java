package com.example.dell.androidhive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChildCallLogActivity extends AppCompatActivity {

    String CurrentCID = AfterChildClick.childID;

    ListView listView;
    Button backButton;
    FirebaseListAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_call_log);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(CurrentCID).child("Call Log");


        listView = findViewById(R.id.call_listView);
        backButton = findViewById(R.id.call_Btn_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildCallLogActivity.this, AfterChildClick.class);
                startActivity(intent);
                finish();
            }
        });

        Query query = databaseReference;

        FirebaseListOptions<ListCall_Class> showCall = new FirebaseListOptions.Builder<ListCall_Class>()
                .setLayout(R.layout.my_call_log_view)
                .setQuery(query, ListCall_Class.class)
                .build();

        adapter = new FirebaseListAdapter(showCall) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView dir = v.findViewById(R.id.dir);
                TextView phNumber = v.findViewById(R.id.phNumber);
                TextView callDuration = v.findViewById(R.id.callDuration);
                TextView callDayTime = v.findViewById(R.id.callDayTime);

                ListCall_Class listCall_class = (ListCall_Class) model;

                dir.setText("Call Type: " + listCall_class.getDir());
                phNumber.setText("Number: " + listCall_class.getPhNumber());
                callDuration.setText("Duration: " + listCall_class.getCallDuration() + " min.");
                callDayTime.setText("Time: " + listCall_class.getCallDayTime());
            }
        };
        listView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}