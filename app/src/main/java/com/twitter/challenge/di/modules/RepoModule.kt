package com.twitter.challenge.di.modules

import dagger.Module

@Module(
    includes = [
        NetModule::class
    ]
)
class RepoModule {

    // @Provides
    // @Singleton
    // fun provideRestaurantRepository(restaurantService: RestaurantService): RestaurantRepository {
    //     return RestaurantRepositoryImpl(restaurantService)
    // }
}
