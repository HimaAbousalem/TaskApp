package com.apps.abousalem.todoapptask.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.apps.abousalem.todoapptask.ui.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule{
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}