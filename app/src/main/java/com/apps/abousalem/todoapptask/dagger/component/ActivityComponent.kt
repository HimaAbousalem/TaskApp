package com.apps.abousalem.todoapptask.dagger.component

import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.dagger.scope.ActivityScope
import com.apps.abousalem.todoapptask.ui.login.LoginActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [TodoTaskAppComponent::class])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
}