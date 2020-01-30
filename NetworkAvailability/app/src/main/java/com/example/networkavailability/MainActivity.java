package com.example.networkavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ConnectivityManager mConnectivityManager;
    final BroadcastReceiver mConnectivityReceiver = new ConnectivityReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.view_text);
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)); //CONNECTIVITY_ACTION?
    }

//    void setTextConnectivity(final ConnectivityManager.NetworkCallback networkCallback) {
//        final String state;
//        if (networkCallback != null && networkCallback) {
//            state = "Connected";
//        } else state = "Not connected";
//
//        textView.setText(String.format(Locale.US, "Network: %s", state));
//    }

    void setTextConnectivity(final NetworkInfo networkInfo) { //NetworkInfo?
        final String state;
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) { //?
            state = "Connected";
        } else state = "Not connected";

        textView.setText(String.format(Locale.US, "Network: %s", state));
    }

    class ConnectivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setTextConnectivity(mConnectivityManager.getActiveNetworkInfo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectivityReceiver);
    }
}
