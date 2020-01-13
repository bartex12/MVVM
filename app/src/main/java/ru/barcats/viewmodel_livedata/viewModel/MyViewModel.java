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

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE_RESENT = 33;
    private MutableLiveData<List<Photo>> data;
    //private List<Photo> photos = new ArrayList<>();

    private FlickrApi flickrApi = new FlickrApi();
    private PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi);
    private DataRepository dataRepository = new PhotosRepositoryImpl(photoDataSource);

    public LiveData<List<Photo>> getData() {
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
        //получаем список фото из PAGE_SIZE_RESENT = 33 штук
        List<Photo> photos = dataRepository.loadData(PAGE_NUMBER, PAGE_SIZE_RESENT);
        //получаем url первого фото из загруженных
        //String url = photos.get(0).getUrl();
        //загружаем в LiveData
        data.setValue(photos);
    }
}
