package com.twitter.challenge.weather

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.twitter.challenge.api.WeatherService
import com.twitter.challenge.base.BaseViewModel
import com.twitter.challenge.models.WeatherModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class WeatherState(
    val currentWeather: WeatherModel? = null,
    val weatherRequest: Async<WeatherModel> = Uninitialized
) : MvRxState

class WeatherViewModel(
    initialState: WeatherState,
    private val weatherService: WeatherService
) : BaseViewModel<WeatherState>(initialState) {

    fun getCurrentWeather(query: String) = withState { state ->
        if (state.weatherRequest is Loading) return@withState

        weatherService
            .getCurrentWeather("current.json")
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    weatherRequest = it,
                    currentWeather = it()
                )
            }
    }

    companion object : MvRxViewModelFactory<WeatherViewModel, WeatherState> {

        override fun create(viewModelContext: ViewModelContext, state: WeatherState): WeatherViewModel {
            val weatherService: WeatherService by viewModelContext.activity.inject()
            return WeatherViewModel(state, weatherService)
        }
    }
}
