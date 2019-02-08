package com.apps.abousalem.todoapptask.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.apps.abousalem.todoapptask.model.database.TaskDatabase
import com.apps.abousalem.todoapptask.model.database.dao.CommentDao
import com.apps.abousalem.todoapptask.model.database.dao.TaskDao
import com.apps.abousalem.todoapptask.model.database.dao.UserDao
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.utils.userIdSharedPref
import com.apps.abousalem.todoapptask.utils.userNameShardPref
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TaskRepository @Inject constructor(taskDatabase: TaskDatabase, private val sharedPreferences: SharedPreferences){
    private var userDao: UserDao? = null
    private var taskDao: TaskDao? = null
    private var commentDao: CommentDao? = null

    private var allTasks: LiveData<List<Task>>? = null
    private var allComments: LiveData<List<TaskComments>>? = null
    private var task : LiveData<Task>? = null

    private var prefsEditor: SharedPreferences.Editor

    init {
        userDao = taskDatabase.userDao()
        commentDao = taskDatabase.commentDao()
        taskDao = taskDatabase.taskDao()
        prefsEditor = sharedPreferences.edit()
    }
    //userDao
    fun saveUser(user: User):Completable{
        return userDao!!.addUser(user)
    }
    fun getUser(userName: String): Single<User> {
        return userDao!!.getUserByUserName(userName)
    }

    //task
    fun insertTask(task: Task):Completable{
        return taskDao!!.insertTask(task)
    }

    fun deleteTask(task: Task):Completable{
        return taskDao!!.deleteTask(task)
    }
    fun getAllTasks(userId: Int):LiveData<List<Task>>{
        allTasks = taskDao!!.getTasks(userId)
        return allTasks!!
    }
    fun updateTask(task: Task):Completable{
        return taskDao!!.updateTask(task)
    }

    //comment
    fun insertComment(taskComments: TaskComments): Completable {
        return commentDao!!.insertComment(taskComments)
    }
    fun getAllComments(taskId: Int):LiveData<List<TaskComments>>{
        allComments = commentDao!!.getTaskComment(taskId)
        return allComments!!
    }

    fun getTaskById(taskId: Int):LiveData<Task>{
        task = taskDao!!.getTaskById(taskId)
        return task!!
    }
    //sharedPref
    fun saveUserToPrefs(user: User){
        prefsEditor.putString(userNameShardPref, user.userName)
        prefsEditor.putInt(userIdSharedPref, user.id)
        prefsEditor.commit()
    }

    fun getUserNameFromPrefs():String{
        return sharedPreferences.getString(userNameShardPref,"NoValue")
    }

    fun getUserIdFromPrefs():Int{
        return sharedPreferences.getInt(userIdSharedPref,-1)
    }
    fun clearSharedPrefs() {
        return prefsEditor.clear().apply()
    }


}