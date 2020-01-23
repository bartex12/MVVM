package ru.barcats.viewmodellivedata.model.repository;

import java.util.List;
import ru.barcats.viewmodellivedata.model.entities.Photo;
import ru.barcats.viewmodellivedata.model.PhotoDataSource;


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

