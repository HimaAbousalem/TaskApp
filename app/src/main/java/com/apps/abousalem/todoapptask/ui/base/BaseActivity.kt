package com.apps.abousalem.todoapptask.ui.base

import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.component.ActivityComponent
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule

abstract class BaseActivity : AppCompatActivity(){
    @UiThread
    fun getActivityComponent(): ActivityComponent {
        return (application as TodoTaskApplication).component
            .activityComponent(ActivityModule(this))
    }
}