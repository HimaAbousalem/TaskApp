package com.apps.abousalem.todoapptask.ui.taskdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import io.reactivex.Completable
import javax.inject.Inject

class TaskDetailsViewModel @Inject constructor(private val repository: TaskRepository): ViewModel(){

    fun addComment(taskComments: TaskComments): Completable {
        return repository.insertComment(taskComments)
    }

    fun getAllComments(taskId: Int): LiveData<List<TaskComments>>{
        return repository.getAllComments(taskId)
    }

    fun getTaskById(taskId: Int):LiveData<Task>{
        return repository.getTaskById(taskId)
    }

}