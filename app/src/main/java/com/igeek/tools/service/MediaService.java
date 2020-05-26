package com.igeek.tools.service;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.igeek.tools.receiver.AlarmReceiver;

import java.util.Date;

public class MediaService extends Service {
    public MediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*每次调用startService启动该服务都会执行*/
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "启动服务：" + new Date().toString());
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long triggerTime = SystemClock.elapsedRealtime() + 60000;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
}