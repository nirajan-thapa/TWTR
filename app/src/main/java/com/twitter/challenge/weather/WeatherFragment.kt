package com.twitter.challenge.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.twitter.challenge.R
import com.twitter.challenge.base.BaseFragment
import com.twitter.challenge.models.WeatherModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.data_container
import kotlinx.android.synthetic.main.fragment_weather.progressbar
import kotlinx.android.synthetic.main.fragment_weather.temperature
import kotlinx.android.synthetic.main.fragment_weather.wind

class WeatherFragment : BaseFragment() {

    private val weatherViewModel: WeatherViewModel by viewModels { factory }

    private val disposable = CompositeDisposable()

    override fun injectDependencies() = AndroidSupportInjection.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Subscribe to the emissions of the weather data from the view model.
        // Update the weather info at every onNext emission.
        // In case of error, log the exception.
        disposable.add(weatherViewModel.getCurrentWeather("current.json")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { toggleProgressBar(show = false) }
            .subscribe(
                {
                    renderWeatherData(it)
                },
                { error ->
                    Log.e(TAG, "Unable to get weather", error)
                }
            )
        )
    }

    private fun renderWeatherData(weatherModel: WeatherModel) {
        val currTmp: Float = weatherModel.weather.temp.toFloat()
        temperature.text = getString(R.string.temperature, currTmp, TemperatureConverter.celsiusToFahrenheit(currTmp))
        wind.text = weatherModel.wind.speed.toString()
        data_container.visibility = VISIBLE
    }

    private fun toggleProgressBar(show: Boolean) {
        if (show && !progressbar.isVisible)
            progressbar.visibility = VISIBLE
        else if (!show && progressbar.isVisible)
            progressbar.visibility = GONE
    }

    override fun onStop() {
        super.onStop()
        // clear all the subscription
        disposable.clear()
    }

    companion object {
        private const val TAG = "WeatherFragment"
    }
}
