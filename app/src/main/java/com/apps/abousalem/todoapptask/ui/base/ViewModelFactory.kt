package com.apps.abousalem.todoapptask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        val viewModel = viewModels[modelClass] ?: viewModels.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return viewModel.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}