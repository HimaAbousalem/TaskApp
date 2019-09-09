package com.apps.abousalem.todoapptask

import android.app.Application
import com.apps.abousalem.todoapptask.dagger.component.DaggerTodoTaskAppComponent
import com.apps.abousalem.todoapptask.dagger.component.TodoTaskAppComponent
import com.apps.abousalem.todoapptask.dagger.module.AppModule
import timber.log.Timber

class TodoTaskApplication: Application(){

    lateinit var component: TodoTaskAppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        component = DaggerTodoTaskAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        component.inject(this)
    }
}