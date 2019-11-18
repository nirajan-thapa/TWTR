package com.twitter.challenge.weather

import android.os.Bundle
import android.view.View
import com.twitter.challenge.base.BaseFragment
import dagger.android.support.AndroidSupportInjection

class WeatherFragment : BaseFragment() {

    override fun injectDependencies() = AndroidSupportInjection.inject(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
