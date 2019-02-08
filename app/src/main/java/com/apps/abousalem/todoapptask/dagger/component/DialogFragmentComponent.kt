package com.apps.abousalem.todoapptask.dagger.component

import androidx.fragment.app.DialogFragment
import com.apps.abousalem.todoapptask.dagger.module.TaskDialogModule
import com.apps.abousalem.todoapptask.dagger.scope.DialogFragmentScope
import dagger.Component

@DialogFragmentScope
@Component(modules = [TaskDialogModule::class], dependencies = [TodoTaskAppComponent::class])
interface DialogFragmentComponent{
    fun inject(dialogFragment: DialogFragment)
}