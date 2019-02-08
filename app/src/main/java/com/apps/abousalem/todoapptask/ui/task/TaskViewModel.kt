package com.apps.abousalem.todoapptask.ui.task

import android.app.Application
import androidx.lifecycle.LiveData
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class TaskViewModel(application: Application): BaseViewModel(application) {

    fun addNewTask(task: Task): Completable {
        return repository.insertTask(task)
            .subscribeOn(Schedulers.io())
    }

    fun getAllTasks(taskId: Int):LiveData<List<Task>>{
        return repository.getAllTasks(taskId)
    }
    fun clearPrefs(){
        repository.clearSharedPrefs()
    }
}