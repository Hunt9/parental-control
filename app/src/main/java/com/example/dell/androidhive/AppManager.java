package com.example.dell.androidhive;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppManager {
    private Context mContext;
    private AppInfo appInfo;
    private ArrayList<AppInfo> myApps;

    public AppManager(Context c) {
        mContext = c;
        myApps = new ArrayList<AppInfo>();
    }

    public ArrayList<AppInfo> getApps() {
        loadApps();
        return myApps;
    }


    private void loadApps() {

        List<ApplicationInfo> packages = mContext.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            AppInfo newApp = new AppInfo();
            newApp.setAppName(getApplicationLabelByPackageName(packageInfo.packageName));
            newApp.setAppPackage(packageInfo.packageName);
            newApp.setAppIcon(getAppIconByPackageName(packageInfo.packageName));
            myApps.add(newApp);
        }

        Collections.sort(myApps, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo s1, AppInfo s2) {
                return s1.getAppName().compareToIgnoreCase(s2.getAppName());

            }
        });

    }


    // Custom method to get application icon by package name
    private Drawable getAppIconByPackageName(String packageName) {
        Drawable icon;
        try {
            icon = mContext.getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            // Get a default icon
            icon = ContextCompat.getDrawable(mContext, R.drawable.ic_launcher_background);
        }
        return icon;
    }

    // Custom method to get application label by package name
    private String getApplicationLabelByPackageName(String packageName) {
        PackageManager packageManager = mContext.getPackageManager();
        ApplicationInfo applicationInfo;
        String label = "Unknown";
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                label = (String) packageManager.getApplicationLabel(applicationInfo);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return label;
    }
}