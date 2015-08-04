package rleblanc.ca.smokino;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Robert LeBlanc - Aug 2, 2015
 * This will create a dialog that lists the names of all the paired bluetooth devices
 * Once the user selects a device it is stored in the application class and a
 * connection is attempted from the application class
 */
public class ConnectButtonListener implements View.OnClickListener {
    private View v;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<String> deviceNameList = new ArrayList<>();
    private ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
    private SmokinoApp app;
    private int deviceIndex;


    public ConnectButtonListener(SmokinoApp _app) {
        super();
        app = _app;
    }

    @Override
    public void onClick(View _v) {

        //v is used to get the activity context and applicationContext
        v = _v;

        if (BluetoothAdapter.getDefaultAdapter().isEnabled() && !app.isConnected()) {
            buildDeviceDialog();
        }

        //This will double check to see if bluetooth is still enabled and will reprompt if it isn't.
        //ToDo: Probably throw something like this in a receiver for bluetooth status change
        v.getContext().startActivity(new Intent(v.getContext(), StartUpActivity.class));
    }


    private void buildDeviceDialog() {

        //Populates the array adapter with the names of all paired bt devices
        ArrayAdapter<String> mArrayAdapter = findDevices();

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Choose a Paired Device")
                .setAdapter(mArrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selected_device) {

                        //Sets the selected device for the entire application
                        app.setRemoteDevice(deviceList.get(selected_device));
                        app.connect();
                    }
                });

        builder.show();

    }

    private ArrayAdapter<String> findDevices() {
        //The deviceNameList is used to display text names of paired devices
        //The deviceList is used as a reference to the actual BluetoothDevice
        pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        deviceList = new ArrayList<>();
        deviceNameList = new ArrayList<>();

        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device);
                deviceNameList.add(device.getName());
            }
        }
        return new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, deviceNameList);
    }
}
