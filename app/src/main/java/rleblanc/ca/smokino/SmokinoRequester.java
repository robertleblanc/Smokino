package rleblanc.ca.smokino;

import android.content.Intent;
import android.util.Log;

/**
 * Created by Robert LeBlanc - Aug 4, 2015
 */
class SmokinoRequester extends Thread {

    private SmokinoApp app;
    private boolean doWork;
    private final String TAG = "SmokinoRequester: ";

    public SmokinoRequester(SmokinoApp _app) {
        super("Smokino Requester");
        doWork = true;
        app = _app;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && doWork) {
            app.writeToDevice("req\n");
            Log.i(TAG, "Request sent to device");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.i(TAG, "Requester was interrupted");
                doWork = false;
            }
        }
        Log.i(TAG, "Requester is halted");
    }
}
