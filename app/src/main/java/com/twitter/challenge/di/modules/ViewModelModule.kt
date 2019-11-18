package com.twitter.challenge.di.modules

import androidx.lifecycle.ViewModelProvider
import com.twitter.challenge.di.TwitterViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: TwitterViewModelFactory): ViewModelProvider.Factory
}
