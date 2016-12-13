package com.androiddev.pjohnson.rxsample.networking;



import com.androiddev.pjohnson.rxsample.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Pablo Johnson (pablo.88j@gmail.com)
 */
public class ExchangeApi {

    private static ExchangeApi instance;
    private ExchangeService service;


    private ExchangeApi() {
    }

    public static ExchangeApi get() {
        if (instance == null) {
            instance = new ExchangeApi();
        }
        return instance;
    }

    public ExchangeService getRetrofitService() {
        if (service == null) {
            OkHttpClient client = OkHttpSingleton.getOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            service = retrofit.create(ExchangeService.class);
        }
        return service;
    }



}
