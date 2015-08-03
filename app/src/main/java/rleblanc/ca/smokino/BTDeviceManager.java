package rleblanc.ca.smokino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Robert LeBlanc - Aug 1, 2015 - Smokino App - BTDeviceManager.java.
 */
public class BTDeviceManager {

    public final String TAG = "BTDeviceManager: ";
    private BluetoothSocket mSocket;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private BluetoothDevice mDevice;
    private UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    public BTDeviceManager(BluetoothDevice _device) {
        mDevice = _device;
    }

    public void connect() {
        //ToDo: Change this function so that it is asychronous
        //Create the socket first
        try {
            //mSocket = mDevice.createRfcomSocketToServiceRecord(mUUID);
            mSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            Log.e(TAG, "Error creating Socket in connect()");
            return;
        }

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception

            //As recommended by android API cancel discovery before making a connection
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            mSocket.connect();

        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            Log.e(TAG, "Error connecting through socket in connect()");
            disconnect();
            return;
        }
        //Get Streams
        try {
            mInStream = mSocket.getInputStream();
            mOutStream = mSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error getting outputstream or inputstream in connect()");
        }
    }

    public void disconnect() {
        if (mInStream != null) {
            try {
                mInStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Error closing inputstream in disconnect()");
            }

            mInStream = null;
        }

        if (mOutStream != null) {
            try {
                mOutStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Error closing outputstream in disconnect");
            }

            mOutStream = null;
        }

        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (Exception e) {
                Log.e(TAG, "Error closing socket in disconnect()");
            }
            mSocket = null;
        }
    }

    public void writeToDevice(String msg) {
        try {
            mOutStream.write(msg.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to outputstream in writeToDevice");
            disconnect();
        }
    }

    //Returns an estimated number of bytes that can be read or skipped without blocking for more input
    public int available() {

        try {
            return mInStream.available();
        } catch (IOException e) {
            Log.e(TAG, "Error getting available bytes from inputstream in available()");
            return 0;
        }

    }

    public int read(byte[] bytes) {

        int input_byte = -1;

        try {
            input_byte = mInStream.read(bytes);
        } catch (IOException e) {
            Log.e(TAG, "Error reading bytes from inputstream in read()");
            disconnect();
        }

        return input_byte;
    }

    public boolean isConnected() {
        return (mSocket != null && mSocket.isConnected());
    }
}

