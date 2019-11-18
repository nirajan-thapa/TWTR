package com.twitter.challenge.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.twitter.challenge.di.TwitterViewModelFactory
import com.twitter.challenge.di.ViewModelKey
import com.twitter.challenge.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds @IntoMap @ViewModelKey(WeatherViewModel::class)
    fun bindWeatherViewModel(vm: WeatherViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: TwitterViewModelFactory): ViewModelProvider.Factory
}
