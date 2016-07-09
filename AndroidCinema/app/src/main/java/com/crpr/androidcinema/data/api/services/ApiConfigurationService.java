package com.crpr.androidcinema.data.api.services;

import com.crpr.androidcinema.data.api.models.configuration.ApiConfiguration;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by claudioribeiro on 08/07/16.
 */
public class ApiConfigurationService {

    public interface ServiceClient {
        @GET("configuration")
        Observable<ApiConfiguration> getConfiguration();
    }

    private final ServiceClient _client;

    public ApiConfigurationService(ServiceClient client){
        _client = client;
    }

    public Observable<ApiConfiguration> getConfiguration(){
        return _client.getConfiguration();
    }

}
