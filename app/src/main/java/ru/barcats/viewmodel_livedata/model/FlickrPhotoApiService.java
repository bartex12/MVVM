package ru.barcats.viewmodel_livedata.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.barcats.viewmodel_livedata.model.photoModel.ApiResult;

public interface FlickrPhotoApiService {

    @GET("services/rest")
    Call<ApiResult> getRecentPhotos(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") String noJsonCallback,
            @Query("per_page") int perPage,
            @Query("page") int pageNumber,
            @Query("extras") String extras);
}
