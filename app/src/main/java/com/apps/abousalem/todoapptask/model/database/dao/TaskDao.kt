package com.apps.abousalem.todoapptask.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apps.abousalem.todoapptask.model.database.entities.Task
import io.reactivex.Completable

@Dao
interface TaskDao{

    @Query("Select * From task_table Where user_id = :userId")
    fun getTasks(userId: Int):LiveData<List<Task>>

    @Insert
    fun insertTask(task: Task):Completable

    @Delete
    fun deleteTask(task: Task):Completable
}