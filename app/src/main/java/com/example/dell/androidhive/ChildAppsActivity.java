package com.example.dell.androidhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChildAppsActivity extends AppCompatActivity {

    ListView lvApps;
    Button btnLock;

    private DatabaseReference childrenRreference;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;

    private ArrayList<ChildAppListModel> appList = new ArrayList<>();
    private ArrayList<ChildAppListModel> lockedApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_apps);


        lvApps = findViewById(R.id.AppsListView);
        btnLock = findViewById(R.id.LockApp);

        getAppList();


        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apps = "";

                for (int i = 0; i < appList.size(); i++) {

//                    if (appList.get(i).isLocked()) {

                        FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(AfterChildClick.childID).child("Locked Apps").child(appList.get(i).getS_id()).setValue(appList.get(i));

//                    }

                }


//                FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(AfterChildClick.childID).child("Locked Apps").child("57").child("locked").setValue(false);
//
//
//                for (int j = 0; j < appList.size(); j++) {
//
//                    for (int k = 0; k < lockedApps.size(); k++) {
//
//
//                        if (appList.get(j).getPackageName().equals(lockedApps.get(k).getPackageName())) {
//
//                            if ((!appList.get(j).isLocked()) && (lockedApps.get(k).isLocked())) {
//
//                                FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(AfterChildClick.childID).child("Locked Apps").child(appList.get(j).getS_id()).child("locked").setValue(false);
//                                getAppList();
//
//                            }
//                        }
//
//
//                    }
//
//                }


                Toast.makeText(ChildAppsActivity.this, "App(s) Locked|Unlocked", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void getAppList() {


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child("Children").child(AfterChildClick.childID).child("Apps");

        myRef1 = database.getReference("Users").child("Children").child(AfterChildClick.childID).child("Locked Apps");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    ChildAppListModel childAppListModel = dataSnapshot1.getValue(ChildAppListModel.class);

                    lockedApps.add(childAppListModel);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    ChildAppListModel childAppListModel = dataSnapshot1.getValue(ChildAppListModel.class);

                    childAppListModel.setS_id(dataSnapshot1.getKey());


                    for (int i = 0; i < lockedApps.size(); i++) {

                        if (childAppListModel.getS_id().equals(lockedApps.get(i).getS_id())) {
                            childAppListModel.setLocked(lockedApps.get(i).isLocked());
                        }

                    }

                    appList.add(childAppListModel);

                }


                AppListAdapter appListAdapter = new AppListAdapter(ChildAppsActivity.this, appList);
                lvApps.setAdapter(appListAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });
    }
}
