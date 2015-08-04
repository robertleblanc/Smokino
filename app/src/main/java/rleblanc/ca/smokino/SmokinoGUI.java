package rleblanc.ca.smokino;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoGUI extends Activity {
    private final String TAG = "SmokinoGUI: ";

    /* Views */
    private Button btn_connect;
    private Button btn_disconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smokino_gui_layout);

        /* Register with the application class -- Useful for getting a reference to this activity
        from other classes
         */
        ((SmokinoApp) (getApplicationContext())).setSmokinoGUI(this);

        /* Views */
        btn_connect = (Button) (findViewById(R.id.btn_connect));
        btn_disconnect = (Button) (findViewById(R.id.btn_disconnect));

        /* Attach Listeners */
        btn_connect.setOnClickListener(new ConnectButtonListener((SmokinoApp) this.getApplicationContext()));
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SmokinoApp) getApplicationContext()).disconnect();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void indicateBTConnection() {
        //Do something to indicate that the bluetooth connection has been made
        Toast.makeText(this, "Bluetooth Connection Established", Toast.LENGTH_SHORT).show();
    }

    public void indicateBTDisconnection() {
        //Do something to indicate that the bluetooth connection has been disconnected
        Toast.makeText(this, "Bluetooth has been disconnected", Toast.LENGTH_SHORT).show();
    }
}

