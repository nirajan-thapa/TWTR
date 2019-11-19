package com.twitter.challenge.di.modules

import com.twitter.challenge.api.WeatherRepository
import com.twitter.challenge.api.WeatherRepositoryImpl
import com.twitter.challenge.api.WeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetModule::class
    ]
)
class RepoModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(weatherService)
    }
}
