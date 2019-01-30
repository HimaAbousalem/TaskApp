package com.apps.abousalem.todoapptask.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.apps.abousalem.todoapptask.model.database.TaskDatabase
import com.apps.abousalem.todoapptask.model.database.dao.CommentDao
import com.apps.abousalem.todoapptask.model.database.dao.TaskDao
import com.apps.abousalem.todoapptask.model.database.dao.UserDao
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.model.database.entities.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TaskRepository @Inject constructor(taskDatabase: TaskDatabase){
    private var userDao: UserDao? = null
    private var taskDao: TaskDao? = null
    private var commentDao: CommentDao? = null

    private var allTasks: LiveData<List<Task>>? = null
    private var allComments: LiveData<List<TaskComments>>? = null

    init {
        userDao = taskDatabase.userDao()
        commentDao = taskDatabase.commentDao()
        taskDao = taskDatabase.taskDao()
        // allComments = commentDao!!.getTaskComment()
    }
    //userDao
    fun saveUser(user: User):Completable{
        return userDao!!.addUser(user)
    }
    fun getUser(userName: String): Single<User> {
        return userDao!!.getUserByUserName(userName)
    }


}