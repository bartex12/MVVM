package ru.barcats.viewmodel_livedata.view;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.viewModel.MyViewModel;

public class MyPositionalDataSource extends PositionalDataSource<Photo> {
    private static final String TAG = "33333";
    private final MyViewModel myViewModel;

    public MyPositionalDataSource(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Photo> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        List<Photo> result = myViewModel.loadData(params.requestedStartPosition, params.requestedLoadSize, null);
        callback.onResult(result, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Photo> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<Photo> result = myViewModel.loadData(params.startPosition, params.loadSize, null);
        callback.onResult(result);
    }
}
