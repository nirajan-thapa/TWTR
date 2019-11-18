package com.twitter.challenge.di.modules

import com.twitter.challenge.weather.WeatherModule
import dagger.Module

@Module(
    includes = [
        WeatherModule::class
    ]
)
interface FeatureBindingModule
