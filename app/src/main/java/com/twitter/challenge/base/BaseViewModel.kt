package com.twitter.challenge.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.twitter.challenge.BuildConfig

abstract class BaseViewModel<S : MvRxState>(initialState: S) :
    BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG)
