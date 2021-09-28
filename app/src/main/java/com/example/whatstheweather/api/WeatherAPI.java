package com.example.whatstheweather.api;

import com.example.whatstheweather.models.basicweather.Weather;
import com.example.whatstheweather.models.dayweather.DayWeather;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.Call;
import retrofit2.http.Query;

public interface WeatherAPI {

    @Headers({"x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
             "x-rapidapi-key: bce9480f1amsh7fca09b8b00740ep14fb02jsnd6be1391fc20"})
    @GET("weather")
    Call<Weather> getCityWeather(@Query("q") String city,@Query("units") String unit);

    @Headers({"x-rapidapi-host: community-open-weather-map.p.rapidapi.com",
            "x-rapidapi-key: bce9480f1amsh7fca09b8b00740ep14fb02jsnd6be1391fc20"})
    @GET("forecast")
    Call<DayWeather> getCityMoreWeather(@Query("q") String city, @Query("units") String unit, @Query("cnt") int count);



}
