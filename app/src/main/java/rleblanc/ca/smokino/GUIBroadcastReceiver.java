package rleblanc.ca.smokino;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by Robert LeBlanc - Aug 3, 2015
 */
public class GUIBroadcastReceiver extends BroadcastReceiver {

    private SmokinoGUI smokinoGUI;

    @Override
    public void onReceive(Context context, Intent intent) {

        /* The GUI activity is stored in the application class which can be accessed from the
        context
         */
        smokinoGUI = ((SmokinoApp) context.getApplicationContext()).getSmokinoGUI();

        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
            if (smokinoGUI != null) {
                smokinoGUI.indicateBTConnection();
                smokinoGUI.tv_status.setText("Connected");
                smokinoGUI.tv_status.setTextColor(Color.GREEN);

                ((SmokinoApp) context.getApplicationContext()).startRequesting();
            }
        }

        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            if (smokinoGUI != null) {
                smokinoGUI.indicateBTDisconnection();
                smokinoGUI.tv_status.setText("Disconnected");
                smokinoGUI.tv_status.setTextColor(Color.RED);

                ((SmokinoApp) context.getApplicationContext()).disconnect();
            }
        }


        if (intent.getAction().equals("UPDATE")) {
            String extra = intent.getStringExtra("Data");

            updateGUIViews(extra);


        }
    }

    public void updateGUIViews(String update) {

        if (update.startsWith("cur:")) {
            try {
                float val = Float.parseFloat(update.substring(4, update.length()));
                String s = (val - 273) + " \u2103";
                smokinoGUI.tv_temperature.setText(s);
            } catch (Exception e) {
                Log.e("Broadcast Receiver", "Poor reading from thermocouple");

            }
        }
        if (update.startsWith("tar:")) {
            try {
                float val = Float.parseFloat(update.substring(4, update.length()));
                String s = (val - 273) + " \u2103";
                smokinoGUI.tv_target_temperature.setText(s);
            } catch (Exception e) {
                smokinoGUI.tv_target_temperature.setText("Error");
            }
        }
    }
}

