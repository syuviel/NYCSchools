package com.codechallenge.nycschools.model.network;


import com.codechallenge.nycschools.model.pojo.NYCSATResponse;
import com.codechallenge.nycschools.model.pojo.NYCSchoolResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Network {
    private Network() {
    }

    private static final Object lock = new Object();
    private static Network INSTANCE;
    private SchoolService SERVICE;

    public static Network getInstance() {
        if (INSTANCE != null) return INSTANCE;

        synchronized (lock) {
            if (INSTANCE != null) return INSTANCE;

            INSTANCE = new Network();

            return INSTANCE;
        }
    }

    public SchoolService getSERVICE() {
        if (SERVICE != null)
            return SERVICE;
        else {
            SERVICE = initRetrofit();
        }
        return SERVICE;
    }

    private SchoolService initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINTS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(SchoolService.class);
    }
}

class ENDPOINTS {
    public static final String BASE_URL = "https://data.cityofnewyork.us/";
    public static final String ENDPOINT_SCHOOL = "resource/s3k6-pzi2.json";
    public static final String ENDPOINT_SAT = "resource/f9bf-2cp4.json";
}


