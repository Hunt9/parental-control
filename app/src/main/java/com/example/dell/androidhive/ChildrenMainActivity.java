package com.example.dell.androidhive;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nephi.getoffyourphone.DB_Helper;
import com.nephi.getoffyourphone.DefaultSettings;
import com.nephi.getoffyourphone.Main;
import com.nephi.getoffyourphone.Timer_Service;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.StreamHandler;

public class ChildrenMainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth auth;
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private Button dashBoard;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String userID;

    private ArrayList<ChildAppListModel> lockedApps = new ArrayList<>();

    private ArrayList<ChildAppListModel> appList = new ArrayList<>();

    private TextView currentEmail;
//    private String cEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_main);

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        auth = FirebaseAuth.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        permission_check();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ChildrenMainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



//        currentEmail = (TextView) findViewById(R.id.current_child_email);
        signOut = findViewById(R.id.sign_out);
//        dashBoard = findViewById(R.id.button_dashbord);

//        cEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

//        currentEmail.setText(cEmail);

//        getPermission();



        try {
            appList = getInstalledComponentList();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < appList.size() ; i++) {

            FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Children").child(userID).child("Apps").child(String.valueOf(i+1)).setValue(appList.get(i));

        }






//        dashBoard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("DASHBOARD");
//                Intent intent = new Intent(ChildrenMainActivity.this, Main.class);
//                startActivity(intent);
//
////                database = FirebaseDatabase.getInstance();
////
////                myRef = database.getReference("Users").child("Children").child(userID).child("Locked Apps");
////
////                myRef.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////
////                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////
////                            ChildAppListModel childAppListModel = dataSnapshot1.getValue(ChildAppListModel.class);
////
////                            //lockedApps.add(childAppListModel);
////
////                            Toast.makeText(ChildrenMainActivity.this, childAppListModel.getPackageName(), Toast.LENGTH_SHORT).show();
////
////                            if(childAppListModel.isLocked()) {
////                                com.nephi.getoffyourphone.apps apps = new com.nephi.getoffyourphone.apps();
////
////                                apps.set_PKG(childAppListModel.getPackageName());
////                                apps.setS_id(Integer.getInteger(childAppListModel.getS_id()));
////
////
////                                com.nephi.getoffyourphone.DB_Helper.lock_apps(apps);
////                            }
////
////                        }
////
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError error) {
////                    }
////
////                });
//            }
//        });

        lockApps(userID);

        /**firebaseAuth = FirebaseAuth.getInstance();
         if (firebaseAuth.getCurrentUser()==null)
         {
         finish();
         startActivity( new Intent(this,LoginActivity.class));
         }*/

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://androidhive-124c5.firebaseio.com/Users/Current Child").setValue(null);

                firebaseAuth.signOut();
                Intent intent = new Intent(ChildrenMainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        FirebaseDatabase.getInstance().getReference().child("Users").child("Children").child(userID).child("Call Log").removeValue();
//        FirebaseDatabase.getInstance().getReference().child("Users").child("Children").child(userID).child("Msg Log").removeValue();


//        firebase.child(id).removeValue();
        sendCallLog();
        sendHistory();
        sendMsgLog();

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

    public void sendCallLog() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.i("", "===============Permission NOT granted..");
            requestPermission();
        } else {
            Log.i("", "===============Permission has been granted. Displaying CALL LOG preview.");
        }

        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {

                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            FirebaseDatabase.getInstance().getReference().child("Users").child("Children").child(userID).child("Call Log").push().setValue(new tempCall(phNumber, dir, callDayTime.toString(), callDuration));
        }
        managedCursor.close();
        return;
    }

    public void sendMsgLog() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i("", "===============Permission NOT granted..");
            requestPermission();
        } else {
            Log.i("", "===============Permission has been granted. Displaying CALL LOG preview.");
        }

        Uri inboxURI = Uri.parse("content://sms/inbox");

        String[] reqTags = new String[]{"address", "body"};

        Cursor cursor = getContentResolver().query(inboxURI, reqTags, null, null, null);

        if(cursor.moveToFirst())

        { // must check the result to prevent exception
            do {
                String msgData = "";

                Map<String, TempClass> myMsg = new HashMap<>();
                Map<String, String> m2 = new HashMap<>();


                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    m2.put(cursor.getColumnName(idx), cursor.getString(idx));

                    FirebaseDatabase.getInstance().getReference().child("Users").child("Children").child(userID).child("Msg Log").push().setValue(m2);
                }
                // use msgData
            } while (cursor.moveToNext());
        } else

        {
            // empty box, no SMS
        }
    }




    public void sendHistory(){

    }
    public void getPermission(){
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            Log.i("", "===============Permission NOT granted..");
//            requestPermission();
//        } else {
//            Log.i("", "===============Permission has been granted. Displaying CALL LOG preview.");
//        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
            // you can show dialog here for grant permission (call log) and handle dialog event according to your need

        } else {
            // call log permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 0);
        }
    }


    private ArrayList<ChildAppListModel> getInstalledComponentList()
            throws PackageManager.NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
        ArrayList<ChildAppListModel> componentList = new ArrayList<ChildAppListModel>();
        String name;
        String pkg = "";
        int i = 0;
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                Resources res = getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                } else {
                    name = ri.activityInfo.applicationInfo.loadLabel(
                            getPackageManager()).toString();
                }
                pkg = ri.activityInfo.packageName;
                ChildAppListModel childAppListModel = new ChildAppListModel();
                childAppListModel.setName(name);
                childAppListModel.setPackageName(pkg);
                componentList.add(childAppListModel);
                i++;
                //listOfApps.add(new MultiSelectModel(i, name));
            }
        }
        return componentList;
    }




    private void lockApps(String childID) {




        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Users").child("Children").child(childID).child("Locked Apps");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    ChildAppListModel childAppListModel = dataSnapshot1.getValue(ChildAppListModel.class);

                    //lockedApps.add(childAppListModel);

                    if(childAppListModel.isLocked()) {
                        com.nephi.getoffyourphone.apps apps = new com.nephi.getoffyourphone.apps();

                        apps.set_PKG(childAppListModel.getPackageName());
                        apps.setS_id(Integer.parseInt(childAppListModel.getS_id()));


                        com.nephi.getoffyourphone.DB_Helper.lock_apps(apps);

                    }else{

                        com.nephi.getoffyourphone.apps apps = new com.nephi.getoffyourphone.apps();

                        apps.set_PKG(childAppListModel.getPackageName());
                        apps.setS_id(Integer.parseInt(childAppListModel.getS_id()));


                        com.nephi.getoffyourphone.DB_Helper.delete_apps(apps);

                    }
                }

                com.nephi.getoffyourphone.DB_Helper.lock_AllTimerData("3_hours","Y",0,"3",1, String.valueOf(System.currentTimeMillis()));
                start_timer("");

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });



    }

    public void start_timer(String hours) {
//     hours   millisNow = System.currentTimeMillis();
//        db.set_Data(millisNow);
//        db.set_Hours(hours.replaceAll("[\\D]", ""));
//        db.set_LockTime(hours);
//        db.set_Running("Y");
//        db.set_once(1);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dnd_toggle();
        }

        Intent intent_service = new Intent(getApplicationContext(), Timer_Service.class);
        startService(intent_service);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void dnd_toggle() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (DefaultSettings.getCb2(this)) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
        }
    }

    //Show dialog if usage access permission not given
    public void permission_check() {
        //Usage Permission
        if (!isAccessGranted()) {
            new LovelyStandardDialog(ChildrenMainActivity.this)
                    .setTopColorRes(com.nephi.getoffyourphone.R.color.blue)
                    .setIcon(com.nephi.getoffyourphone.R.drawable.ic_perm_device_information_white_48dp)
                    .setTitle(getString(com.nephi.getoffyourphone.R.string.permission_check_title))
                    .setMessage(getString(com.nephi.getoffyourphone.R.string.permission_check_message))
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

        public boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode;
            assert appOpsManager != null;
            mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }







}
