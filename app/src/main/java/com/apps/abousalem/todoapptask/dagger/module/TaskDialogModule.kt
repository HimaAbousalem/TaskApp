package com.apps.abousalem.todoapptask.dagger.module

import androidx.lifecycle.ViewModelProviders
import com.apps.abousalem.todoapptask.dagger.scope.DialogFragmentScope
import com.apps.abousalem.todoapptask.ui.base.DialogView
import com.apps.abousalem.todoapptask.ui.task.TaskViewModel
import dagger.Module
import dagger.Provides

@Module
class TaskDialogModule(val fragment: DialogView){

    @DialogFragmentScope
    @Provides
    fun providesTaskViewModel():TaskViewModel{
        return ViewModelProviders.of(fragment).get(TaskViewModel::class.java)
    }
}