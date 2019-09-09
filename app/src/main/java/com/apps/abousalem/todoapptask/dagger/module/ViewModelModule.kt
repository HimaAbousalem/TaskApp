package com.apps.abousalem.todoapptask.dagger.module

import androidx.lifecycle.ViewModel
import com.apps.abousalem.todoapptask.dagger.scope.ViewModelKey
import com.apps.abousalem.todoapptask.ui.base.SharedViewModel
import com.apps.abousalem.todoapptask.ui.login.LoginViewModel
import com.apps.abousalem.todoapptask.ui.task.TaskViewModel
import com.apps.abousalem.todoapptask.ui.taskdetails.TaskDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    internal abstract fun sharedViewModel(viewModel: SharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    internal abstract fun taskViewModel(viewModel: TaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskDetailsViewModel::class)
    internal abstract fun taskDetailsViewModel(viewModel: TaskDetailsViewModel): ViewModel

}