package ru.barcats.viewmodel_livedata.viewModel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApi;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.model.PhotoDataSource;
import ru.barcats.viewmodel_livedata.model.PhotoDataSourceImpl;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrHostProvider;
import ru.barcats.viewmodel_livedata.model.flicr.HostProvider;
import ru.barcats.viewmodel_livedata.model.repository.PhotoRepository;
import ru.barcats.viewmodel_livedata.model.repository.PhotosRepositoryImpl;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManager;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManagerImpl;

public class MyViewModel extends AndroidViewModel {

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE_RESENT = 33;
    private MutableLiveData<List<Photo>> data;
    private PhotoRepository photoRepository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        //context = application.getApplicationContext();
        //this.application = application;

         ResourceManager resourceManager = new ResourceManagerImpl(application);
         HostProvider hostProvider = new FlickrHostProvider(resourceManager);
         FlickrApi flickrApi = new FlickrApi(hostProvider);
         PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi);
         photoRepository = new PhotosRepositoryImpl(photoDataSource);
    }

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
        List<Photo> photos = photoRepository.loadData(PAGE_NUMBER, PAGE_SIZE_RESENT);
        //загружаем в LiveData
        data.setValue(photos);
    }
}
