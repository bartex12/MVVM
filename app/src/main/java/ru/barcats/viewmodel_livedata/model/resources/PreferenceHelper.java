package ru.barcats.viewmodel_livedata.model.resources;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferenceHelper {

    private final Context context;
    private static final String TAG = "33333";

    public PreferenceHelper(Context context) {
        this.context = context;
    }

    //записываем номер следующего запуска = текущий запуск+1
    public  void saveNumber(String key){

        //defaultValue = 0 - чтобы не делать 2 параметра
        Integer number = loadNumber(key, 0);

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(key, ++number)
                .apply();

        Log.d(TAG, "PreferenceHelper saveNumber запись полученного числа number = " + number);
    }

    //получаем номер текущего запкска приложения по ключу из LaunchCountRepositoryImpl
    public Integer loadNumber(String key, int defaultValue) {
        Integer number = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(key, defaultValue);
        Log.d(TAG, "PreferenceHelper loadNumber получение номера запуска = " + number );
        return number;
    }
}
