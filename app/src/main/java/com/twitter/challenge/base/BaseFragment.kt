package com.twitter.challenge.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory

    protected abstract fun injectDependencies()

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }
}
