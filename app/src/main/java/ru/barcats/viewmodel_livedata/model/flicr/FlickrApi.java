package ru.barcats.viewmodel_livedata.model.flicr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrApi {

    private HostProvider hostProvider;

    public FlickrApi(HostProvider hostProvider) {
        this.hostProvider = hostProvider;
    }

    public FlickrPhotoApiService getService() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hostProvider.getHostUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(FlickrPhotoApiService.class);
    }
}
