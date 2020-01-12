package ru.barcats.viewmodel_livedata;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyServer implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void connect() {
            // ...
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void disconnect() {
            // ...
        }

}
