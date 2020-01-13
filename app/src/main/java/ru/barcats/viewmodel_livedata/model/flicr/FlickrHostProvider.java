package ru.barcats.viewmodel_livedata.model.flicr;

import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.model.resources.ResourceManager;

public class FlickrHostProvider implements HostProvider {
    private final ResourceManager resourceManager;

    public FlickrHostProvider(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public String getHostUrl() {
        //
        return resourceManager.getString(R.string.flick_host);
    }
}
