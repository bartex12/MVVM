package ru.barcats.viewmodellivedata.model.repository;

import java.util.List;

import ru.barcats.viewmodellivedata.model.entities.Photo;

public interface PhotoRepository {
    List<Photo> loadData(int pageNumber, int pageSize, String textSearch);
}

