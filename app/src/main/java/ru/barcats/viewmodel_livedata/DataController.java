package ru.barcats.viewmodel_livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DataController {

    private static  DataController ourInstance;
    private MutableLiveData<String> liveData = new MutableLiveData<>();

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

    public LiveData<String> getData() {
        return liveData;
    }

    public void setInLiveData(String liveData){
        this.liveData.setValue(liveData);
    }
}
