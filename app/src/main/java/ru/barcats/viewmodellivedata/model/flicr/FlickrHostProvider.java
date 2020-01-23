package ru.barcats.viewmodellivedata.model.flicr;

import ru.barcats.viewmodellivedata.R;
import ru.barcats.viewmodellivedata.model.resources.ResourceManager;

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
