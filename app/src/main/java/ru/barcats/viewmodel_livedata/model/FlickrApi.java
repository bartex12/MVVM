package ru.barcats.viewmodel_livedata.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrApi {

    public FlickrApi() {}

    public FlickrPhotoApiService getService() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(FlickrPhotoApiService.class);
    }
}
