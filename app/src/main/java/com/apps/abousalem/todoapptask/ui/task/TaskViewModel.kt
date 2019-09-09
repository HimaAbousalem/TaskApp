package com.apps.abousalem.todoapptask.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.Task
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    fun addNewTask(task: Task): Completable {
        return repository.insertTask(task)
    }

    fun getAllTasks(taskId: Int):LiveData<List<Task>>{
        return repository.getAllTasks(taskId)
    }
    fun clearPrefs(){
        repository.clearSharedPrefs()
    }
}