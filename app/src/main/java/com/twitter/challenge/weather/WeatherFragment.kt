package com.twitter.challenge.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.twitter.challenge.R
import com.twitter.challenge.base.BaseFragment
import com.twitter.challenge.base.simpleController
import com.twitter.challenge.weather.views.loadingView
import com.twitter.challenge.weather.views.textRow
import com.twitter.challenge.weather.views.weatherRow

class WeatherFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_weather

    private val weatherViewModel: WeatherViewModel by fragmentViewModel(WeatherViewModel::class)

    override fun epoxyController() = simpleController(weatherViewModel) { state ->
        if (state.weatherRequest is Loading) {
            loadingView {
                id("loading-view")
            }
            return@simpleController
        }

        if (state.futureWeatherRequest is Loading) {
            loadingView {
                id("future-loading-view")
            }
        }

        state.currentWeather?.apply {
            val currTmp: Float = this.weather.temp.toFloat()
            val windSpeed = this.wind.speed
            val showCloud = this.clouds.cloudiness > 50
            weatherRow {
                id("current-weather")
                temperature(getString(R.string.temperature, currTmp, TemperatureConverter.celsiusToFahrenheit(currTmp)))
                wind(getString(R.string.wind, windSpeed))
                cloudVisibility(showCloud)
                clickListener { _ ->
                    weatherViewModel.getFutureWeather()
                }
            }
        }

        state.standardDeviation?.apply {
            val value = this
            textRow {
                id("text-row-standard")
                title(getString(R.string.std_dev, value))
            }
        }

        state.futureWeathers?.forEach {
            Log.d(TAG, "$it")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        toolbar = view.findViewById(R.id.toolbar)
        coordinatorLayout = view.findViewById(R.id.coordinator_layout)
        recyclerView.setController(epoxyController)
        toolbar.title = getString(R.string.app_name)

        // Subscribe to the emissions of the weather data from the view model.
        // In case of error, log the exception.
        weatherViewModel.asyncSubscribe(
            WeatherState::weatherRequest,
            onSuccess = {
                Snackbar.make(coordinatorLayout, "Weather request successful.", Snackbar.LENGTH_LONG).show()
                Log.i(TAG, "Weather request successful")
            },
            onFail = { error ->
                Snackbar.make(coordinatorLayout, "Weather request failed.", Snackbar.LENGTH_LONG).show()
                Log.e(TAG, "Weather request failed", error)
            }
        )

        weatherViewModel.asyncSubscribe(
            WeatherState::futureWeatherRequest,
            onSuccess = {
                weatherViewModel.calculateStandardDeviation()
                Snackbar.make(coordinatorLayout, "Future Weather request successful.", Snackbar.LENGTH_LONG).show()
                Log.i(TAG, "Weather request successful")
            },
            onFail = { error ->
                Snackbar.make(coordinatorLayout, "Future Weather request failed.", Snackbar.LENGTH_LONG).show()
                Log.e(TAG, "Weather request failed", error)
            }
        )

        // request current weather
        weatherViewModel.getCurrentWeather("current.json")
    }

    companion object {
        private const val TAG = "WeatherFragment"
    }
}
