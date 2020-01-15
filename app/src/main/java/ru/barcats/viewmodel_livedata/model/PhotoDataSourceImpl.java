package ru.barcats.viewmodel_livedata.model;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import retrofit2.Response;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.model.flicr.ApiKeyProvider;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrApi;
import ru.barcats.viewmodel_livedata.model.flicr.FlickrPhotoApiService;
import ru.barcats.viewmodel_livedata.model.photomodelapi.ApiPhoto;
import ru.barcats.viewmodel_livedata.model.photomodelapi.ApiResult;

public class PhotoDataSourceImpl implements PhotoDataSource {

    private static final String TAG = "33333";
    private static final String FLICKR_PHOTOS_GET_RECENT = "flickr.photos.getRecent";
    private static final String FLICKR_PHOTOS_SEARCH = "flickr.photos.search";
    private static final String JSON = "json";
    private static final String NO_JSON_CALLBACK = "1";
    private static final String URL_S = "url_s";
    private FlickrApi flickrApi;
    private ApiKeyProvider apiKeyProvider;

    public PhotoDataSourceImpl(FlickrApi flickrApi, ApiKeyProvider apiKeyProvider) {
        this.flickrApi = flickrApi;
        this.apiKeyProvider = apiKeyProvider;
    }

    @Override
    public List<Photo> loadData(final int pageNumber, final int perPage, final String textSearch) {

        Log.i(TAG, "PhotoDataSourceImpl List<ApiResult> getRecent()");
        final List<Photo> photoList =  new ArrayList<>();

        //TODO *************синхронизация нужна другая******************
        //возможно, нужно вместо execute - в том же потоке , сделать enqueue() - в своём потоке
        //это описано в видеоуроке по ретрофиту Андроид2 урок 5
        //для синхронизации потоков - чтобы основной ждал пока закончится поиск фото на сервере
        final CountDownLatch startSignal = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //создаём запрос с помощью ретрофита
                    FlickrPhotoApiService iService = flickrApi.getService();
                    Log.i(TAG, "PhotoDataSourceImpl getRecent iService = " + iService);
                    Response<ApiResult> response;
                    if (textSearch == null){
                        //выполняем запрос @GET("services/rest") к серверу
                        response =iService.getRecentPhotos(
                                FLICKR_PHOTOS_GET_RECENT,
                                apiKeyProvider.getApiKey(),
                                JSON,
                                NO_JSON_CALLBACK,
                                perPage,
                                pageNumber,
                                URL_S).execute();
                    }else {
                        //выполняем запрос @GET("services/rest") к серверу
                         response =iService.getRecentSearchedPhotos(
                                FLICKR_PHOTOS_SEARCH,
                                apiKeyProvider.getApiKey(),
                                JSON,
                                NO_JSON_CALLBACK,
                                perPage,
                                pageNumber,
                                URL_S,
                                textSearch).execute();
                    }
                    //получаем модель данных как она есть на сервере
                    ApiResult apiResults = response.body();
                    Log.i(TAG, "PhotoDataSourceImpl getRecent getTotal  = " +
                            Objects.requireNonNull(apiResults).photos.getTotal());
                    //переходим к списку фото на сервере  - photos сделан public для разнообразия
                    List<ApiPhoto> fotoList = apiResults.photos.getPhotos();
                    //теперь надо перейти к списку фоток с единственным полем - URL
                    for (int i=0; i<fotoList.size(); i++) {
                        photoList.add(new Photo(fotoList.get(i).getUrl()));
                    }
                    Log.i(TAG, "PhotoDataSourceImpl getRecent 1 photoList.size() = " + photoList.size());
                    //разрешаем работу основного потока для возврата данных после окончания этого
                    startSignal.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            //заставляем основной поток ждать, пока не закончит работу
            // другой поток по команде  startSignal.countDown();
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "PhotoDataSourceImpl getRecent 2 photos.size() = " + photoList.size());
        return photoList;
    }

}





