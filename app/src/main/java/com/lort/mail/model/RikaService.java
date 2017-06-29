package com.lort.mail.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.lort.mail.R;

public class RikaService extends Service {
    private Rika rika;

    public RikaService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Test", "Service: onCreate");
        startForeground(1, new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build());
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        rika.open().subscribe(task -> NotificationManagerCompat.from(RikaService.this)
                .notify(2, new NotificationCompat.Builder(RikaService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("One new task")
                        .setContentText(task.getAddress())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build()), Throwable::printStackTrace);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
