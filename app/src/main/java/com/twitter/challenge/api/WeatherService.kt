package com.twitter.challenge.api

import com.twitter.challenge.models.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("/{query}")
    fun getCurrentWeather(@Path("query") query: String): Single<WeatherModel>


    @GET("/{query}")
    fun getFutureWeather(@Path("query") query: String): Single<WeatherModel>
}
