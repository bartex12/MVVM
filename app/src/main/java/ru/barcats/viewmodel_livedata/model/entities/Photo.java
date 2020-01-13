package ru.barcats.viewmodel_livedata.model.entities;

import java.util.Objects;

public class Photo {

    private final String url;

    public Photo(String url) {
        //
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(url, photo.url);
    }
    @Override
    public int hashCode() {

        return Objects.hash(url);
    }
    @Override
    public String toString() {
        return "Photo{" +
                "url='" + url + '\'' +
                '}';
    }
}
