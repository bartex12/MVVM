package ru.barcats.viewmodel_livedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyServer myServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(myServer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myServer.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myServer.disconnect();
    }
}
