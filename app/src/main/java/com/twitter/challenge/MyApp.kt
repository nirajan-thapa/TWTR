package com.twitter.challenge

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.Moshi.Builder
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.twitter.challenge.api.WeatherService
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(weatherPlayerService)
        }
    }
}

private val weatherPlayerService = module {
    factory {
        Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl("https://twitter-code-challenge.s3.amazonaws.com/")
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    single {
        get<Retrofit>().create(WeatherService::class.java)
    }
}
