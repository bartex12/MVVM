package ru.barcats.viewmodellivedata.model.photomodelapi;

import com.google.gson.annotations.SerializedName;

public final class ApiResult {

    @SerializedName("photos")
    public ApiPhotoPage photos;

}
