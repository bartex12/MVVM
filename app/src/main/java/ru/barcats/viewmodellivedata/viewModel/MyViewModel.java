package ru.barcats.viewmodellivedata.viewModel;

import android.app.Application;
import android.util.Log;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.barcats.viewmodellivedata.model.flicr.ApiKeyProvider;
import ru.barcats.viewmodellivedata.model.flicr.FlickrApi;
import ru.barcats.viewmodellivedata.model.entities.Photo;
import ru.barcats.viewmodellivedata.model.PhotoDataSource;
import ru.barcats.viewmodellivedata.model.PhotoDataSourceImpl;
import ru.barcats.viewmodellivedata.model.flicr.FlickrApiKeyProvider;
import ru.barcats.viewmodellivedata.model.flicr.FlickrHostProvider;
import ru.barcats.viewmodellivedata.model.flicr.HostProvider;
import ru.barcats.viewmodellivedata.model.repository.LaunchCountRepository;
import ru.barcats.viewmodellivedata.model.repository.LaunchCountRepositoryImpl;
import ru.barcats.viewmodellivedata.model.repository.PhotoRepository;
import ru.barcats.viewmodellivedata.model.repository.PhotosRepositoryImpl;
import ru.barcats.viewmodellivedata.model.resources.PreferenceHelper;
import ru.barcats.viewmodellivedata.model.resources.ResourceManager;
import ru.barcats.viewmodellivedata.model.resources.ResourceManagerImpl;

public class MyViewModel extends AndroidViewModel {

    private static final String TAG = "33333";
    private MutableLiveData <Integer> numberOfLaunch;
    private PhotoRepository photoRepository;
    private LaunchCountRepository launchCountRepository;
    private String search;
    
    //Subclasses must have a constructor which accepts Application as the only parameter.
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

    //метод получения числа запусков приложения
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
    public List<Photo> loadData(int requestedStartPosition, int requestedLoadSize, String searchStr) {
        Log.d(TAG, "MyViewModel loadData search = " + searchStr);
        return photoRepository.loadData(requestedStartPosition, requestedLoadSize, searchStr);
    }

    //получаем число запусков приложения
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
        //сохраняем номер запучка приложения
        launchCountRepository.saveNumber();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
