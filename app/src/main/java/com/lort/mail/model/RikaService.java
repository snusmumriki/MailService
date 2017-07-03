package com.lort.mail.model;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.lort.mail.App;
import com.lort.mail.R;
import com.lort.mail.TaskActivity;

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
        rika = ((App) getApplication()).getRika();
        startForeground(1, new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Waiting for new tasks")
                .setContentText(". . .")
                .build());
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i("Test", "Service: onStartCommand");
        rika.open().subscribe(task -> NotificationManagerCompat.from(RikaService.this)
                .notify(2, new NotificationCompat.Builder(RikaService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("One new task")
                        .setContentText(task.getAddress())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(PendingIntent.getActivity(this, 1,
                                new Intent(this, TaskActivity.class).putExtra("task", task),
                                PendingIntent.FLAG_CANCEL_CURRENT))
                        .build()), Throwable::printStackTrace);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
