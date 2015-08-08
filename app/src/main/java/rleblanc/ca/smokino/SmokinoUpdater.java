package rleblanc.ca.smokino;

import android.content.Intent;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Robert LeBlanc - Aug 4, 2015
 */
public class SmokinoUpdater extends Thread {

    SmokinoApp app;
    public final String TAG = "SmokinoUpdater: ";
    boolean doWork = true;
    final byte delimiter = 10; //This is the ASCII code for a newline character
    byte[] readBuffer = new byte[1024];
    int readBufferPosition = 0;


    Time now = new Time();

    public SmokinoUpdater(SmokinoApp _app) {
        app = _app;
    }

    @Override
    public void run() {

        while (doWork && !Thread.interrupted()) {

            int bytesAvailable = app.available();
            if (bytesAvailable > 0 && !Thread.interrupted()) {
                byte[] packetBytes = new byte[bytesAvailable];
                app.read(packetBytes);

                for (int i = 0; i < bytesAvailable; i++) {
                    byte b = packetBytes[i];

                    if (b == delimiter) {

                        byte[] encodedBytes = new byte[readBufferPosition - 1];
                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                        try {
                            final String data = new String(encodedBytes, "US-ASCII");
                            readBufferPosition = 0;
                            Intent intent = new Intent("UPDATE");
                            intent.putExtra("Data", data);
                            app.sendBroadcast(intent);


                        } catch (UnsupportedEncodingException e) {
                            Log.e(TAG, "Encoding of bytes messed up after reading");
                        }

                    } else {
                        readBuffer[readBufferPosition++] = b;
                    }
                }
            }
            if (Thread.interrupted()) {
                doWork = false;
                Log.i(TAG, "Updater was interrupted");
            }
        }

        Log.i(TAG, "Updater is halted");
    }
}