package ru.barcats.viewmodel_livedata.viewModel;

import androidx.paging.DataSource;
import ru.barcats.viewmodel_livedata.model.entities.Photo;


//класс для создания  MyPositionalDataSource с передачей всех зависимостей MyViewModel
public class MySourceFactory extends DataSource.Factory<Integer, Photo> {

    private final MyViewModel myViewModel;

    public MySourceFactory(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    //создаём класс MyPositionalDataSource с зависимостью от MyViewModel,
    // в которойв свою очередь описаны все остальные зависимости для получения данных
    @Override
    public DataSource create() {
        return new MyPositionalDataSource(myViewModel);
    }
}
