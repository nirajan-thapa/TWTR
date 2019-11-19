package com.twitter.challenge.api

import com.twitter.challenge.models.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

interface WeatherRepository {

    fun getCurrentWeather(query: String): Single<WeatherModel>
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {

    override fun getCurrentWeather(query: String): Single<WeatherModel> =
        weatherService.getCurrentWeather(query)
}
