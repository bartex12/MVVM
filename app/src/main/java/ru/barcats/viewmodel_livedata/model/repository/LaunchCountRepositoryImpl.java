package ru.barcats.viewmodel_livedata.model.repository;

import android.util.Log;
import ru.barcats.viewmodel_livedata.model.resources.PreferenceHelper;

public class LaunchCountRepositoryImpl implements LaunchCountRepository {

    private static final String TAG = "33333";
    private static final String NUMBER_OF_LAUNCH = "NUMBER_OF_LAUNCH";

    private PreferenceHelper preferenceHelper;

    public LaunchCountRepositoryImpl(PreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
        //Log.d(TAG, "LaunchCountRepositoryImplNoRx Конструктор");
    }

    @Override
    public Integer loadNumber() {
        return preferenceHelper.loadNumber(NUMBER_OF_LAUNCH, 0);
    }

    @Override
    public void saveNumber() {
        preferenceHelper.saveNumber(NUMBER_OF_LAUNCH);
    }
}
