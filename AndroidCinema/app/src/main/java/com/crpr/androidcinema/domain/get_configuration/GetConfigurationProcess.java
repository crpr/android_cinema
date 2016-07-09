package com.crpr.androidcinema.domain.get_configuration;

import com.crpr.androidcinema.data.api.models.configuration.ApiConfiguration;
import com.crpr.androidcinema.data.api.models.configuration.enums.Size;
import com.crpr.androidcinema.data.api.services.ApiConfigurationService;
import com.crpr.androidcinema.domain.common.ImageUrlProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by claudioribeiro on 09/07/16.
 */
public class GetConfigurationProcess implements GetConfiguration.Process {

    private final GetConfiguration.Service _service;

    public GetConfigurationProcess(GetConfiguration.Service service){
        this._service = service;
    }

    /**************** GET UPDATED CONFIGURATION ***********************/
    public Observable<ConfigurationModel> getConfiguration(){
        return _service.getConfiguration()
                    .flatMap(this::configureImageUrlProvider);
    }

    private Observable<ConfigurationModel> configureImageUrlProvider(ApiConfiguration configuration) {

        ConfigurationModel model = ConfigurationModel
                                    .url(configuration.getImagesConfiguration().getBaseUrl())
                                    .secureUrl(configuration.getImagesConfiguration().getSecureBaseUrl())
                                    .mapUrls(buildUrlsMap(configuration.getImagesConfiguration()))
                                    .build();

        if(model == null){
            return null;
        }

        ImageUrlProvider.sharedInstance().setCurrentConfig(model);

        return Observable.just(model);
    }

    private Map<String, String> buildUrlsMap(ApiConfiguration.ApiImagesConfiguration imagesConfiguration){
        Map<String, String> values = new HashMap<>();

        //process backdrop urls
        process(imagesConfiguration.getStillSizes(), ConfigurationModel.BACKDROP,
                imagesConfiguration.getBaseUrl(), values);

        //process backdrop urls
        process(imagesConfiguration.getLogoSizes(), ConfigurationModel.LOGO,
                imagesConfiguration.getBaseUrl(), values);

        //process backdrop urls
        process(imagesConfiguration.getPosterSizes(), ConfigurationModel.POSTER,
                imagesConfiguration.getBaseUrl(), values);

        //process backdrop urls
        process(imagesConfiguration.getProfileSizes(), ConfigurationModel.PROFILE,
                imagesConfiguration.getBaseUrl(), values);

        //process stills urls
        process(imagesConfiguration.getStillSizes(), ConfigurationModel.STILL,
                imagesConfiguration.getBaseUrl(), values);

        return values;
    }

    private void process(List<Size> list, int type, String baseUrl, Map<String, String> container){
        for(Size size : list){
            String url = baseUrl + size.raw();
            container.put(type + size.raw(), url);
        }
    }
}
