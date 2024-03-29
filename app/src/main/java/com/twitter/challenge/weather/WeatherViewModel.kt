package com.twitter.challenge.weather

import android.util.Log
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.twitter.challenge.api.WeatherService
import com.twitter.challenge.base.BaseViewModel
import com.twitter.challenge.models.WeatherModel
import io.reactivex.Single
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class WeatherState(
    val currentWeather: WeatherModel? = null,
    val futureWeathers: List<WeatherModel>? = null,
    val weatherRequest: Async<WeatherModel> = Uninitialized,
    val futureWeatherRequest: Async<List<WeatherModel>> = Uninitialized,
    val standardDeviation: Double? = null
) : MvRxState

class WeatherViewModel(
    initialState: WeatherState,
    private val weatherService: WeatherService
) : BaseViewModel<WeatherState>(initialState) {

    fun getCurrentWeather(query: String) = withState { state ->
        if (state.weatherRequest is Loading) return@withState

        weatherService
            .getCurrentWeather(query)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    weatherRequest = it,
                    currentWeather = it()
                )
            }
    }

    fun getFutureWeather() = withState { state ->
        if (state.futureWeatherRequest is Loading) return@withState
        val futureWeatherList = mutableListOf<WeatherModel>()
        Single.zip(
            weatherService.getFutureWeather("future_1.json"),
            weatherService.getFutureWeather("future_2.json"),
            weatherService.getFutureWeather("future_3.json"),
            weatherService.getFutureWeather("future_4.json"),
            weatherService.getFutureWeather("future_5.json"),
            Function5<WeatherModel, WeatherModel, WeatherModel, WeatherModel, WeatherModel, List<WeatherModel>>
            { one, two, three , four, five->
                futureWeatherList.add(one)
                futureWeatherList.add(two)
                futureWeatherList.add(three)
                futureWeatherList.add(four)
                futureWeatherList.add(five)
                futureWeatherList.toList()
            }
        )
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    futureWeatherRequest = it,
                    futureWeathers = it()
                )
            }
    }

    fun calculateStandardDeviation() = withState {
        if (it.futureWeathers != null) {
            val temps = mutableListOf<Double>().apply {
                it.futureWeathers.forEach {
                    add(it.weather.temp)
                }
            }
            val standardDeviation = TemperatureConverter.calculateStandardDeviation(temps)
            Log.d("WeatherViewModel", "Standard: $standardDeviation")
            setState {
                copy(
                    standardDeviation = standardDeviation
                )
            }
        }
    }

    companion object : MvRxViewModelFactory<WeatherViewModel, WeatherState> {

        override fun create(viewModelContext: ViewModelContext, state: WeatherState): WeatherViewModel {
            val weatherService: WeatherService by viewModelContext.activity.inject()
            return WeatherViewModel(state, weatherService)
        }
    }
}
