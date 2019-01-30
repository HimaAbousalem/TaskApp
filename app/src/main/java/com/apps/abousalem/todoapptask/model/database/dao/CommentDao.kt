package com.apps.abousalem.todoapptask.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import io.reactivex.Completable

@Dao
interface CommentDao{

    @Insert
    fun insertComment(taskComments: TaskComments):Completable

    //underTest
    @Query("Select * From task_comments Where task_id = :taskId")
    fun getTaskComment(taskId: Int): LiveData<List<TaskComments>>
}