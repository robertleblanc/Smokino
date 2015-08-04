package rleblanc.ca.smokino;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Robert LeBlanc - Aug 3, 2015
 */
public class GUIBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        /* The GUI activity is stored in the application class which can be accessed from the
        context
         */
        SmokinoGUI smokinoGUI = ((SmokinoApp) context.getApplicationContext()).getSmokinoGUI();

        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
            if (smokinoGUI != null)
                smokinoGUI.indicateBTConnection();
        }

        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            if (smokinoGUI != null)
                smokinoGUI.indicateBTDisconnection();
        }
    }
}

