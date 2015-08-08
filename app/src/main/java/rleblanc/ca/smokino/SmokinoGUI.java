package rleblanc.ca.smokino;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoGUI extends Activity {
    private final String TAG = "SmokinoGUI: ";

    /* Views */
    private Button btn_connect;
    private Button btn_disconnect;
    public TextView tv_status;
    public Button btn_pid_on;
    public Button btn_pid_off;
    public Button btn_set_target_temperature;
    public TextView tv_temperature;
    public TextView tv_target_temperature;
    public SeekBar sb_fanThrottle;
    public EditText et_set_target_temperature;

    //public SmokinoApp app = ((SmokinoApp) (getApplicationContext()));


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
        btn_pid_off = (Button) (findViewById(R.id.btn_pid_off));
        btn_pid_on = (Button) (findViewById(R.id.btn_pid_on));
        btn_set_target_temperature = (Button) (findViewById(R.id.btn_set_target_temperature));

        tv_status = (TextView) (findViewById(R.id.tv_status));

        tv_temperature = (TextView) (findViewById(R.id.tv_temperature));
        tv_target_temperature = (TextView) (findViewById(R.id.tv_target_temperature));

        et_set_target_temperature = (EditText) (findViewById(R.id.et_set_target_temperature));

        sb_fanThrottle = (SeekBar) findViewById(R.id.sb_fanThrottle);


        /* Attach Listeners */
        btn_connect.setOnClickListener(new ConnectButtonListener((SmokinoApp) this.getApplicationContext()));
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SmokinoApp) getApplicationContext()).disconnect();
            }
        });

        sb_fanThrottle.setMax(100);
        sb_fanThrottle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((SmokinoApp) getApplicationContext()).writeToDevice("fan" + progress + "\n");
            }
        });

        btn_pid_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SmokinoApp) getApplicationContext()).writeToDevice("pidon\n");
                ((SmokinoApp) getApplicationContext()).writeToDevice("kpv5\n");
                ((SmokinoApp) getApplicationContext()).writeToDevice("kiv5\n");
                ((SmokinoApp) getApplicationContext()).writeToDevice("kdv5\n");
            }
        });

        btn_pid_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SmokinoApp) getApplicationContext()).writeToDevice("pidoff\n");
            }
        });

        btn_set_target_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String set_temp_string = et_set_target_temperature.getText().toString();
                    int kelvin = Integer.parseInt(set_temp_string) + 273;
                    ((SmokinoApp) getApplicationContext()).writeToDevice("set" + kelvin);
                } catch (Exception e) {
                    et_set_target_temperature.setText("Error getting temperature - Try again");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //app.stopRequesting();
        //((SmokinoApp) (getApplicationContext())).disconnect();
        ((SmokinoApp) (getApplicationContext())).setSmokinoGUI(null);

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

