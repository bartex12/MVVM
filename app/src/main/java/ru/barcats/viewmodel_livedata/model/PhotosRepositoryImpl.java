package ru.barcats.viewmodel_livedata.model;

import java.util.List;

public class PhotosRepositoryImpl implements DataRepository {

    private final PhotoDataSource photoDataSource;

    public PhotosRepositoryImpl(PhotoDataSource photoDataSource) {
        this.photoDataSource = photoDataSource;
    }

    @Override
    public List<Photo> loadData(int pageNumber, int pageSize) {
        return photoDataSource.loadData(pageNumber, pageSize);
    }
}
