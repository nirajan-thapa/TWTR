package com.twitter.challenge.weather

import kotlin.math.pow
import kotlin.math.sqrt

object TemperatureConverter {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */
    fun celsiusToFahrenheit(temperatureInCelsius: Float): Float {
        return temperatureInCelsius * 1.8f + 32
    }

    @Throws(IllegalArgumentException::class)
    fun calculateStandardDeviation(values: List<Double>): Double {
        if (values.size <= 1) {
            return 0.0
        }
        // Calculate Standard Deviation
        var sum = 0.0
        for (value in values) {
            sum += value
        }
        val mean = sum / values.size
        sum = 0.0 // Reset the sum to calculate summation
        for (value in values) {
            sum += (value - mean).pow(2.0)
        }
        return sqrt(sum / (values.size - 1))
    }
}
