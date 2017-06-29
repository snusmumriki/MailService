package com.lort.mail;

import android.app.Application;

import com.lort.mail.model.Rika;

/**
 * Created by megaman on 29.06.2017.
 */

public class App extends Application {
    private Rika rika;
    @Override
    public void onCreate() {
        super.onCreate();
        rika = new Rika(this);
    }

    public Rika getRika() {
        return rika;
    }
}
