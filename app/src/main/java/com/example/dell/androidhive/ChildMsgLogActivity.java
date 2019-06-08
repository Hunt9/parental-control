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

public class ChildMsgLogActivity extends AppCompatActivity {

    String CurrentCID = AfterChildClick.childID;

    ListView mlistView;
    Button backButton;
    FirebaseListAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_msg_log);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(CurrentCID).child("Msg Log");
        Query query = databaseReference;

        mlistView = findViewById(R.id.msg_listView);
        backButton = findViewById(R.id.msg_Btn_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildMsgLogActivity.this, AfterChildClick.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseListOptions<ListMsg_Class> showMsg = new FirebaseListOptions.Builder<ListMsg_Class>()
                .setLayout(R.layout.my_msg_log_view)
                .setQuery(query, ListMsg_Class.class)
                .build();
//        Log.i("check 1: ", "HERE 1");

        adapter = new FirebaseListAdapter(showMsg) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {

//                Log.i("check 2: ", "HERE 2");

                TextView address = v.findViewById(R.id.address);
                TextView body = v.findViewById(R.id.body);

                ListMsg_Class listMsg_class = (ListMsg_Class) model;

                if (listMsg_class.getBody() != null){
                    address.setText("Number: " + listMsg_class.getAddress());
                    body.setText("Message: " + listMsg_class.getBody());
                }

//                address.setText("Number: " + listMsg_class.getAddress());
//                body.setText("Message: " + listMsg_class.getBody());

//                Log.i("Address: ", listMsg_class.getAddress());

            }
        };
        mlistView.setAdapter(adapter);
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
