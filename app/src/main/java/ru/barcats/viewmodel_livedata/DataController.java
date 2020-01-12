package ru.barcats.viewmodel_livedata;

import androidx.lifecycle.LiveData;

public class DataController {

    private static  DataController ourInstance;
    LiveData<String> liveData;

    public static DataController getInstance()
    {
        return DataController();
    }

    private static DataController DataController() {
        if (ourInstance == null){
            return new DataController();
        }
        return ourInstance;
    }

    private LiveData<String> getData() {
        return liveData;
    }

    public void setLiveData(LiveData<String> liveData) {
        this.liveData = liveData;
    }
}
