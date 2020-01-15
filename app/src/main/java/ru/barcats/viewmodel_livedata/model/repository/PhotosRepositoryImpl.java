package ru.barcats.viewmodel_livedata.model.repository;

import java.util.List;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.model.PhotoDataSource;


public class PhotosRepositoryImpl implements PhotoRepository {

    private final PhotoDataSource photoDataSource;

    public PhotosRepositoryImpl(PhotoDataSource photoDataSource) {
        this.photoDataSource = photoDataSource;
    }

    @Override
    public List<Photo> loadData(int pageNumber, int pageSize, String textSearch) {
        return photoDataSource.loadData(pageNumber, pageSize, textSearch);
    }
}

