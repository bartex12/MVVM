package ru.barcats.viewmodel_livedata.view;

import androidx.paging.DataSource;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.viewModel.MyViewModel;

class MySourceFactory extends DataSource.Factory<Integer, Photo> {

    private final MyViewModel myViewModel;

    MySourceFactory(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public DataSource create() {
        return new MyPositionalDataSource(myViewModel);
    }
}
