package com.apps.abousalem.todoapptask.ui.taskdetails

import android.app.Application
import androidx.lifecycle.LiveData
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TaskDetailsViewModel(application: Application): BaseViewModel(application){

    fun addComment(taskComments: TaskComments): Completable {
        return repository.insertComment(taskComments)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllComments(taskId: Int): LiveData<List<TaskComments>>{
        return repository.getAllComments(taskId)
    }

    fun getTaskById(taskId: Int):LiveData<Task>{
        return repository.getTaskById(taskId)
    }

}