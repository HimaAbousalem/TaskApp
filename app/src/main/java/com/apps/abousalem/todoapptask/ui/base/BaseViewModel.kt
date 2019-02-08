package com.apps.abousalem.todoapptask.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.Task
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BaseViewModel (application: Application): AndroidViewModel(application){
    @Inject
    lateinit var repository: TaskRepository
    init{
        (application as TodoTaskApplication).getComponent().inject(this)
    }
    fun getUserId():Int{
        return repository.getUserIdFromPrefs()
    }

    fun getUserName():String{
        return repository.getUserNameFromPrefs()
    }
    fun updateTask(task: Task): Completable {
        return repository.updateTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTask(task: Task): Completable {
        return repository.deleteTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}