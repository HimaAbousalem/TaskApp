package com.apps.abousalem.todoapptask.dagger.component

import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.dagger.module.AppModule
import com.apps.abousalem.todoapptask.dagger.module.ViewModelFactoryModule
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface TodoTaskAppComponent{
    fun inject(app:TodoTaskApplication)
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}