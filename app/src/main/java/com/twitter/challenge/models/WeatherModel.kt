package com.twitter.challenge.models

data class WeatherModel(
    val coord: Coord,
    val weather: Weather,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    val name: String
)

data class Weather(
    val temp: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Rain(
    val volume: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Clouds(
    val cloudiness: Int
)
