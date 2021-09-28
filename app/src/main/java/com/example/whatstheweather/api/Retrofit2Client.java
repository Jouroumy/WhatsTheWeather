package com.example.whatstheweather.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit api = null;

    public static Retrofit getRetrofit2Client() {
        if (api == null) {
            api = new Retrofit.Builder().baseUrl("https://community-open-weather-map.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return api;
    }
}
