package ru.barcats.viewmodel_livedata.viewModel;

import android.app.Application;
import android.util.Log;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.barcats.viewmodel_livedata.model.flicr.ApiKeyProvider;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApi;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.model.PhotoDataSource;
import ru.barcats.viewmodel_livedata.model.PhotoDataSourceImpl;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApiKeyProvider;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrHostProvider;
import ru.barcats.viewmodel_livedata.model.flicr.HostProvider;
import ru.barcats.viewmodel_livedata.model.repository.LaunchCountRepository;
import ru.barcats.viewmodel_livedata.model.repository.LaunchCountRepositoryImpl;
import ru.barcats.viewmodel_livedata.model.repository.PhotoRepository;
import ru.barcats.viewmodel_livedata.model.repository.PhotosRepositoryImpl;
import ru.barcats.viewmodel_livedata.model.resources.PreferenceHelper;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManager;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManagerImpl;

public class MyViewModel extends AndroidViewModel {

    private static final String TAG = "33333";
    private MutableLiveData <Integer> numberOfLaunch;
    private PhotoRepository photoRepository;
    private LaunchCountRepository launchCountRepository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        //здесь нужен контекст - его и передаём в ResourceManagerImpl и PreferenceHelper
         PreferenceHelper preferenceHelper = new PreferenceHelper(application);
         launchCountRepository = new LaunchCountRepositoryImpl(preferenceHelper);

         ResourceManager resourceManager = new ResourceManagerImpl(application);
         HostProvider hostProvider = new FlickrHostProvider(resourceManager);
         FlickrApi flickrApi = new FlickrApi(hostProvider);
         ApiKeyProvider apiKeyProvider = new FlickrApiKeyProvider(resourceManager);
         PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi, apiKeyProvider);
         photoRepository = new PhotosRepositoryImpl(photoDataSource);
    }

    public LiveData<Integer> getNumber() {
        if (numberOfLaunch == null) {
            numberOfLaunch = new MutableLiveData<>();
            loadNumber();
        }
        return numberOfLaunch;
    }

    //Метод loadData должен быть асинхронным, потому что он вызывается из метода getData,
    // а getData в свою очередь вызывается из Activity и все это происходит в UI потоке.
    // Если loadData начнет грузить данные синхронно, то он заблокирует UI поток.
    public List<Photo> loadData(int pageNumber, int pageSize, String search) {
        return photoRepository.loadData(pageNumber, pageSize, search);
    }

    private void loadNumber() {
        //получаем номер запуска приложения
        Integer number = launchCountRepository.loadNumber();
        Log.d(TAG, "MyViewModel loadNumber number = " + number);
        //загружаем в LiveData
        numberOfLaunch.setValue(number);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "MyViewModel onCleared");
        launchCountRepository.saveNumber();
    }
}
