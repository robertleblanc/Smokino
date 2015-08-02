package rleblanc.ca.smokino;

import android.app.Application;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoApp extends Application {
    BTDevice btDevice;

    static SmokinoApp smokinoApp;

    public static SmokinoApp getInstance() {
        return smokinoApp;
    }

    @Override
    public void onCreate() {
        smokinoApp = this;
        super.onCreate();
    }
}