package com.apps.abousalem.todoapptask.dagger.component

import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.module.AppModule
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface TodoTaskAppComponent{
    fun inject(app:TodoTaskApplication)
    fun inject(baseViewModel: BaseViewModel)
}