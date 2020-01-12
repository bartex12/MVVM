package ru.barcats.viewmodel_livedata.model;

import java.util.List;

public interface PhotoDataSource {
    List<Photo> loadData(int pageNumber, int perPage);
}
