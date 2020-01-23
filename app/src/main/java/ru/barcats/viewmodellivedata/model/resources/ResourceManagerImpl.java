package ru.barcats.viewmodellivedata.model.resources;

import android.content.Context;

import androidx.annotation.StringRes;

public class ResourceManagerImpl implements ResourceManager {
    private final Context context;

    public ResourceManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getString(@StringRes int id) {
        //
        return context.getString(id);
    }

}
