package com.apps.abousalem.todoapptask.dagger.component

import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.dagger.module.ViewModelModule
import com.apps.abousalem.todoapptask.dagger.scope.ActivityScope
import com.apps.abousalem.todoapptask.ui.base.DialogView
import com.apps.abousalem.todoapptask.ui.login.LoginActivity
import com.apps.abousalem.todoapptask.ui.task.TaskActivity
import com.apps.abousalem.todoapptask.ui.taskdetails.TaskDetailsActivity
import dagger.Component
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelModule::class])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(taskActivity:TaskActivity)
    fun inject(taskDetailsActivity: TaskDetailsActivity)
    fun inject(dialog: DialogView)
}