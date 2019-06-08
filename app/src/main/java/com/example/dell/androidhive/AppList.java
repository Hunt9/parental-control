package com.example.dell.androidhive;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AppList extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private Button buttonBack;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<AppInfo> installedApps;
    private AppManager appManager;
    private final String baseURL = "http://192.168.50.48/post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        buttonBack = findViewById(R.id.button_Back);

        installedApps = new ArrayList<AppInfo>();
        mRecyclerView = findViewById(R.id.recycleView);
        //shareButton = (FloatingActionButton) findViewById(R.id.sharebutton);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        appManager = new AppManager(this);
        installedApps = appManager.getApps();

        // Initialize a new adapter for RecyclerView
        mAdapter = new InstalledAppsAdapter(
                getApplicationContext(),
                installedApps
        );
        mRecyclerView.setAdapter(mAdapter);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppList.this, ViewChildActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
