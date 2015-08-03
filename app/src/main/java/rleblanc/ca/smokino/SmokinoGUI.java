package rleblanc.ca.smokino;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Robert LeBlanc - Aug 1, 2015
 */
public class SmokinoGUI extends Activity {
    private final String TAG = "SmokinoGUI: ";


    private Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smokino_gui_layout);

        /* Views */
        btn_connect = (Button) (findViewById(R.id.btn_connect));

        /* Attach Listeners */
        btn_connect.setOnClickListener(new ConnectButtonListener());
    }
}

