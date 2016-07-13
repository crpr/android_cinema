package com.crpr.androidcinema.domain.discover;

import com.crpr.androidcinema.data.api.models.ApiMovie;
import com.crpr.androidcinema.data.api.responses.ApiResponse;
import com.crpr.androidcinema.domain.common.viewmodels.ListConverter;
import com.crpr.androidcinema.domain.common.viewmodels.converters.ApiListMovieConverter;

import java.util.List;

import rx.Observable;

/**
 * Created by claudioribeiro on 12/07/16.
 */
public class DiscoverProcess implements Discover.Process {

    private final Discover.Service _service;
    private final ApiListMovieConverter _converter;

    public DiscoverProcess(Discover.Service service, ApiListMovieConverter converter){
        this._service = service;
        this._converter = converter;
    }

    public Observable<List<ListMovieModel>> discoverMovies(){
        return _service.discoverMovies()
                .flatMap(this::processResponse);
    }

    private Observable<List<ListMovieModel>> processResponse(ApiResponse<ApiMovie> response){
        return Observable.just(convert(response));
    }

    private List<ListMovieModel> convert(ApiResponse<ApiMovie> response) {
        return ListConverter.map(response.getResults(), _converter);
    }

}
