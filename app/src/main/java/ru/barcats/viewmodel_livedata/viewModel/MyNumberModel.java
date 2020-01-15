package ru.barcats.viewmodel_livedata.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.barcats.viewmodel_livedata.model.repository.PhotoRepository;

public class MyNumberModel extends ViewModel {

    private MutableLiveData <Integer> number;
    private PhotoRepository photoRepository;

    public MyNumberModel(){

    }

    public LiveData<Integer> getNumber(){
        if (number == null){
            number = new MutableLiveData();
            loadNumber();
        }
        return number;
    }

    private void loadNumber() {


    }


}
