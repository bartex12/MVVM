package ru.barcats.viewmodellivedata.model.flicr;

import ru.barcats.viewmodellivedata.R;
import ru.barcats.viewmodellivedata.model.resources.ResourceManager;

public class FlickrApiKeyProvider implements ApiKeyProvider {

    private final ResourceManager resourceManager;

    public FlickrApiKeyProvider(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public String getApiKey() {
        // Мой API key
        // see https://www.flickr.com/services/apps/create/apply/
        return resourceManager.getString(R.string.flick_api_key);
    }
}
