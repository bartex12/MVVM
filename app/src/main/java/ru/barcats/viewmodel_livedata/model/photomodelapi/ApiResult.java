package ru.barcats.viewmodel_livedata.model.photomodelapi;

import com.google.gson.annotations.SerializedName;

public final class ApiResult {

    @SerializedName("photos")
    public ApiPhotoPage photos;

//    public ApiPhotoPage getPhotos() {
//        return photos;
//    }
}
