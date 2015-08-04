package rleblanc.ca.smokino;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoApp extends Application {
    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothDevice remoteDevice;
    public BTDeviceManager btManager;
    private SmokinoGUI smokinoGUI;


    @Override
    public void onCreate() {
        btManager = null;

        //Check to see that the device running this app has a bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            System.exit(0); // Device does not support Bluetooth
        }

        smokinoGUI = null;
        super.onCreate();
    }

    public void setSmokinoGUI(SmokinoGUI _smokinoGUI) {
        smokinoGUI = _smokinoGUI;
    }

    public SmokinoGUI getSmokinoGUI() {
        return smokinoGUI;
    }

    //Maintains a reference to the remote device the application should connect to and
    //creates a bluetooth manager for all the operations of that device
    public void setRemoteDevice(BluetoothDevice _remoteDevice) {
        remoteDevice = _remoteDevice;
        btManager = new BTDeviceManager(remoteDevice);
    }

    public void connect() {
        if (btManager != null) {
            btManager.connect();
        }
    }

    public boolean isConnected() {
        return (btManager != null && btManager.isConnected());
    }

    public void disconnect() {
        if (btManager != null) {
            btManager.disconnect();
        }
    }

}