package com.example.dell.androidhive;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.nephi.getoffyourphone.Main;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class ViewChildActivity extends AppCompatActivity {


    List<Listdata> list;
    RecyclerView recyclerview;

    String server_url = "http://192.168.0.106/app/dbconfig.php";
    AlertDialog.Builder builder;



    private Button buttonBack, buttonAddChld, buttonUpdate;
//    private RecyclerView childrenList;
    private DatabaseReference childrenRreference;

    DatabaseReference currentParentRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Current Parent").child("ID");

//    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

//    private FirebaseDatabase newdatabase;
//    private DatabaseReference newUser;

    private String uID;
    private ArrayList<String> mUserName = new ArrayList<>();

    public String pid = " ";



    private void renderListView(String myid){

//        listView = (ListView) findViewById(R.id.newListView);
        database = FirebaseDatabase.getInstance();
//        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference("Users").child("Parents").child(myid).child("Children");
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUserName);
//        listView.setAdapter(arrayAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Userdetails userdetails = dataSnapshot1.getValue(Userdetails.class);

                    Listdata listdata = new Listdata();
                    String phone = userdetails.getPhone();
                    String name = userdetails.getName();
                    String email = userdetails.getEmail();
                    String age = userdetails.getAge();
                    listdata.setName(name);
                    listdata.setEmail(email);
                    listdata.setPhone(phone);
                    listdata.setAge(age);
                    listdata.setId(dataSnapshot1.getKey());
                    list.add(listdata);
                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                }
//
                RecyclerviewAdapter recycler = new RecyclerviewAdapter(ViewChildActivity.this,list);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(ViewChildActivity.this);
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator( new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //  Log.w(TAG, "Failed to read value.", error.toException());
            }

        });






//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String value = dataSnapshot.getValue().toString();
////                Log.i("```````````+++",dataSnapshot.toString());
////                System.out.println("```````````+++```` "+pid);
//
//
//                mUserName.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ViewChildActivity.this,mUserName.get(position),Toast.LENGTH_SHORT).show();
//                System.out.println("++++++++++++++++++++ ID = " + id);
//                System.out.println("++++++++++++++++++++ Position = " + position);
//                System.out.println("UPDATE WORKING.");
//                Intent intent = new Intent(ViewChildActivity.this, ChildrenTab.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_child);

        buttonBack = findViewById(R.id.button_Back);
        buttonAddChld = findViewById(R.id.button_AddChild);
//        buttonUpdate = findViewById(R.id.button_UpdateList);
        childrenRreference = FirebaseDatabase.getInstance().getReference("Users").child("Children");

        recyclerview = findViewById(R.id.rview);


        currentParentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pid = dataSnapshot.getValue(String.class);
//                pid = id;

                renderListView(pid);

//                System.out.println("``````````` ID +++```` "+id);
//                System.out.println("``````````` PID 1 +++```` "+pid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewChildActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonAddChld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewChildActivity.this, AddChildFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("UPDATE WORKING.");
//                Connect_xampp();
//                Intent intent = new Intent(ViewChildActivity.this, AppList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }
    private void Connect_xampp() {

//        StringRequest stringRequest = new StringRequest(DownloadManager.Request.Method.POST, server_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                builder.setTitle("Server Response");
//                builder.setMessage("Response :"+response);
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Attributes.Name.setText("");
//                        ContactsContract.CommonDataKinds.Email.setText("");
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//            }
//        }
//
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,"some error found .....",Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map <String,String> Params = new HashMap<String, String>();
//                Params.put("name",name);
//                Params.put("email",email);
//                return Params;
//
//            }
//        };
//        Mysingleton.getInstance(MainActivity.this).addTorequestque(stringRequest);

    }
}
