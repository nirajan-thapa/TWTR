package com.twitter.challenge.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.twitter.challenge.R

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // val temperatureView = findViewById(R.id.temperature) as TextView
        // temperatureView.text = getString(R.string.temperature, 34f, TemperatureConverter.celsiusToFahrenheit(34f))
    }
}
