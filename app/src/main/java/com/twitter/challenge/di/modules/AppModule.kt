package com.twitter.challenge.di.modules

import dagger.Module

@Module(
    includes = [
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
class AppModule
