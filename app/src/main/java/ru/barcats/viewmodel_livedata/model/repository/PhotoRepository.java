package ru.barcats.viewmodel_livedata.model.repository;

import java.util.List;

import ru.barcats.viewmodel_livedata.model.entities.Photo;

public interface PhotoRepository {
    List<Photo> loadData(int pageNumber, int pageSize, String textSearch);
}
