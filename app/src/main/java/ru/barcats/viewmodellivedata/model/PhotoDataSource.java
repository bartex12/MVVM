package ru.barcats.viewmodellivedata.model;

import java.util.List;

import ru.barcats.viewmodellivedata.model.entities.Photo;

public interface PhotoDataSource {
    List<Photo> loadData(int pageNumber, int perPage, String textSearch);
}
