package com.apps.abousalem.todoapptask.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.model.TaskRepository
import javax.inject.Inject

open class BaseViewModel (application: Application): AndroidViewModel(application){
    @Inject
    lateinit var repository: TaskRepository
    init{
        (application as TodoTaskApplication).getComponent().inject(this)
    }
}