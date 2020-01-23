package ru.barcats.viewmodellivedata.model.photomodelapi;

import com.google.gson.annotations.SerializedName;

public final class ApiPhoto {

    @SerializedName("id")
    private String id;

    @SerializedName("owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private int farm;

    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

    @SerializedName("url_s")
    private String url;

    @SerializedName("height_s")
    private String height;

    @SerializedName("width_s")
    private String width;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public int getIsFamily() {
        return isFamily;
    }

    public String getUrl() {
        return url;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }
}