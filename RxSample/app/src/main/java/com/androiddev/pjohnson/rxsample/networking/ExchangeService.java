package com.androiddev.pjohnson.rxsample.networking;

import com.androiddev.pjohnson.rxsample.networking.responses.ExchangeResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Pablo Johnson (pablo.88j@gmail.com)
 */
public interface ExchangeService {

    @GET("latest")
    Observable<ExchangeResponse> getLatestExchanges();

    @GET("{date}")
    Observable<ExchangeResponse> getLatestExchangesFrom(@Path("date") String date);

    @GET("latest")
    Observable<ExchangeResponse> getLatestExchangesWithBase(@Query("base") String base);

}
