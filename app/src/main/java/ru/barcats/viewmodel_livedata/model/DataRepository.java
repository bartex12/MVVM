package ru.barcats.viewmodel_livedata.model;

import java.util.List;

import ru.barcats.viewmodel_livedata.model.Photo;

public interface DataRepository {

    List<Photo> loadData(int pageNumber, int pageSize);

    //void loadData();
    //это пока не работает
    //void loadData(Callback<String> callback);
}
