package ru.barcats.viewmodel_livedata.viewModel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.barcats.viewmodel_livedata.model.flicr.ApiKeyProvider;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApi;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.model.PhotoDataSource;
import ru.barcats.viewmodel_livedata.model.PhotoDataSourceImpl;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApiKeyProvider;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrHostProvider;
import ru.barcats.viewmodel_livedata.model.flicr.HostProvider;
import ru.barcats.viewmodel_livedata.model.repository.PhotoRepository;
import ru.barcats.viewmodel_livedata.model.repository.PhotosRepositoryImpl;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManager;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManagerImpl;

public class MyViewModel extends AndroidViewModel {

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE_RESENT = 33;
    private static final int PAGE_SIZE_SEARCH = 100;
    private MutableLiveData<List<Photo>> data;
    private PhotoRepository photoRepository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        //здесь нужен контекст - его и передаём в ResourceManagerImpl
         ResourceManager resourceManager = new ResourceManagerImpl(application);
         HostProvider hostProvider = new FlickrHostProvider(resourceManager);
         FlickrApi flickrApi = new FlickrApi(hostProvider);
         ApiKeyProvider apiKeyProvider = new FlickrApiKeyProvider(resourceManager);
         PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi, apiKeyProvider);
         photoRepository = new PhotosRepositoryImpl(photoDataSource);
    }

    public LiveData<List<Photo>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData(null);
        }
        return data;
    }
    //Метод loadData должен быть асинхронным, потому что он вызывается из метода getData,
    // а getData в свою очередь вызывается из Activity и все это происходит в UI потоке.
    // Если loadData начнет грузить данные синхронно, то он заблокирует UI поток.
    public void loadData(String search) {
        List<Photo> photos = null;
        if (search == null) {
            //получаем список фото из PAGE_SIZE_RESENT = 33 штук
            photos = photoRepository.loadData(PAGE_NUMBER, PAGE_SIZE_RESENT, search);
        }else {
            //получаем список фото из PAGE_SIZE_SEARCH = 100 штук
            photos = photoRepository.loadData(PAGE_NUMBER, PAGE_SIZE_SEARCH, search);
        }
        //загружаем в LiveData
        data.setValue(photos);
    }
}
