package com.apps.abousalem.todoapptask

import android.app.Application
import com.apps.abousalem.todoapptask.dagger.component.DaggerTodoTaskAppComponent
import com.apps.abousalem.todoapptask.dagger.component.TodoTaskAppComponent
import com.apps.abousalem.todoapptask.dagger.module.AppModule
import com.apps.abousalem.todoapptask.model.TaskRepository
import timber.log.Timber
import javax.inject.Inject

class TodoTaskApplication: Application(){

    private lateinit var component: TodoTaskAppComponent


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component = DaggerTodoTaskAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }
    fun getComponent(): TodoTaskAppComponent {
        return component
    }

}