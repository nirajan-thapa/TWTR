package com.twitter.challenge.weather

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface WeatherModule {
    @ContributesAndroidInjector fun provideWeatherFragment(): WeatherFragment
}
