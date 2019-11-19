package com.twitter.challenge.weather

import androidx.lifecycle.ViewModel
import com.twitter.challenge.api.WeatherRepository
import com.twitter.challenge.models.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun getCurrentWeather(query: String): Single<WeatherModel> =
        weatherRepository.getCurrentWeather(query)
}
