package ru.barcats.viewmodel_livedata.viewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.barcats.viewmodel_livedata.model.DataRepository;
import ru.barcats.viewmodel_livedata.model.FlickrApi;
import ru.barcats.viewmodel_livedata.model.Photo;
import ru.barcats.viewmodel_livedata.model.PhotoDataSource;
import ru.barcats.viewmodel_livedata.model.PhotoDataSourceImpl;
import ru.barcats.viewmodel_livedata.model.PhotosRepositoryImpl;

public class MyViewModel extends ViewModel {
    private MutableLiveData<String> data;
    private FlickrApi flickrApi = new FlickrApi();
    private PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi);
    private DataRepository dataRepository = new PhotosRepositoryImpl(photoDataSource);

    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }
    //Метод loadData должен быть асинхронным, потому что он вызывается из метода getData,
    // а getData в свою очередь вызывается из Activity и все это происходит в UI потоке.
    // Если loadData начнет грузить данные синхронно, то он заблокирует UI поток.
    private void loadData() {
        //получаем список фото из 3 штук
        List<Photo> photos = dataRepository.loadData(1, 3);
        //получаем url первого фото из трёх загруженных
        String url = photos.get(0).getUrl();
        //загружаем в LiveData
        data.setValue(url);
    }
}
