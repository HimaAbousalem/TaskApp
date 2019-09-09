package com.apps.abousalem.todoapptask.dagger.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.TaskDatabase
import com.apps.abousalem.todoapptask.utils.MyPREFERENCES
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application){

    @AppScope
    @Provides
    fun context(): Application {
        return application
    }

    @AppScope
    @Provides
    fun provideTaskDatabase(): TaskDatabase {
        return TaskDatabase.getInstance(application)
    }

    @AppScope
    @Provides
    fun provideSharedPrefrence():SharedPreferences {
        return application.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
    }

}