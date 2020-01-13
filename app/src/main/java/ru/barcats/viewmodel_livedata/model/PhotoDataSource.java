package ru.barcats.viewmodel_livedata.model;

import java.util.List;

import ru.barcats.viewmodel_livedata.model.entities.Photo;

public interface PhotoDataSource {
    List<Photo> loadData(int pageNumber, int perPage);
}
