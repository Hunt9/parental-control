package com.nephi.getoffyourphone;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BGService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        startService(new Intent(this, Timer_Service.class));

    }

    @Override
    public void onDestroy() {
       // Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();

    }
}