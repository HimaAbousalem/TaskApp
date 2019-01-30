package com.apps.abousalem.todoapptask.dagger.module

import android.content.Context
import com.apps.abousalem.todoapptask.dagger.qualifier.AppQualifier
import com.apps.abousalem.todoapptask.dagger.scope.AppScope
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.TaskDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(@AppQualifier val context: Context){

    @AppScope
    @Provides
    fun provideRepository(taskDatabase: TaskDatabase): TaskRepository {
        return TaskRepository(taskDatabase)
    }

    @AppScope
    @Provides
    fun provideTaskDatabase(): TaskDatabase {
        return TaskDatabase.getInstance(context)
    }

}