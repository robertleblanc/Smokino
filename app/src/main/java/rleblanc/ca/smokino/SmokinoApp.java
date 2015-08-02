package rleblanc.ca.smokino;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoApp extends Application {
    public BluetoothAdapter mBluetoothAdapter;

    static SmokinoApp smokinoApp;

    public static SmokinoApp getInstance() {
        return smokinoApp;
    }

    @Override
    public void onCreate() {
        smokinoApp = this;

        //Check to see that the device running this app has a bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            System.exit(0); // Device does not support Bluetooth
        }

        super.onCreate();
    }
}