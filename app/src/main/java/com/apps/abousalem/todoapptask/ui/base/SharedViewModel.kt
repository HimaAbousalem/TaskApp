package com.apps.abousalem.todoapptask.ui.base

import androidx.lifecycle.ViewModel
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.Task
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SharedViewModel @Inject constructor(private val repository: TaskRepository): ViewModel(){

    fun getUserId():Int{
        return repository.getUserIdFromPrefs()
    }

    fun getUserName():String{
        return repository.getUserNameFromPrefs()
    }
    fun updateTask(task: Task): Completable {
        return repository.updateTask(task)
    }

    fun deleteTask(task: Task): Completable {
        return repository.deleteTask(task)
    }
}