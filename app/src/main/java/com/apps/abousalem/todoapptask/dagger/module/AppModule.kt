package com.apps.abousalem.todoapptask.dagger.module

import android.content.Context
import android.content.SharedPreferences
import com.apps.abousalem.todoapptask.dagger.qualifier.AppQualifier
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.TaskDatabase
import com.apps.abousalem.todoapptask.utils.MyPREFERENCES
import dagger.Module
import dagger.Provides

@Module
class AppModule(@AppQualifier val context: Context){

    @AppScope
    @Provides
    fun provideRepository(taskDatabase: TaskDatabase, sharedPreferences: SharedPreferences): TaskRepository {
        return TaskRepository(taskDatabase, sharedPreferences)
    }

    @AppScope
    @Provides
    fun provideTaskDatabase(): TaskDatabase {
        return TaskDatabase.getInstance(context)
    }

    @AppScope
    @Provides
    fun provideSharedPrefrence():SharedPreferences {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
    }


}