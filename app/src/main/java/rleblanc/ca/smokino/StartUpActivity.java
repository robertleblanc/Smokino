package rleblanc.ca.smokino;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class StartUpActivity extends Activity {

    /* Constants */
    private final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Check to see if bluetooth is enabled
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else
            startSmokinoGUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                startSmokinoGUI();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You need to enable bluetooth to use this app - Do it now or else...", Toast.LENGTH_SHORT).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                Toast.makeText(this, "An error likely occured while trying to enable bluetooth -- Try again", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startSmokinoGUI() {

        //This could be executed after the GUI has launched... check to see if it had already launched first
        SmokinoGUI smokinoGUI = ((SmokinoApp) getApplicationContext()).getSmokinoGUI();
        if (smokinoGUI == null)
            startActivity(new Intent(this, SmokinoGUI.class));

        finish();
    }
}
