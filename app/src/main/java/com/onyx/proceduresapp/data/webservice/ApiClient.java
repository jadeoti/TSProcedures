package com.onyx.proceduresapp.data.webservice;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private ProcedureService service;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(ProcedureService.class);
    }

    public ProcedureService getService() {
        return service;
    }
}
