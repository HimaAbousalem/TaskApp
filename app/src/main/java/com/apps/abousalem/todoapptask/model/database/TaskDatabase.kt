package com.apps.abousalem.todoapptask.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apps.abousalem.todoapptask.model.database.dao.CommentDao
import com.apps.abousalem.todoapptask.model.database.dao.TaskDao
import com.apps.abousalem.todoapptask.model.database.dao.UserDao
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.utils.Converters
import com.apps.abousalem.todoapptask.utils.databaseName

@Database(entities = [User::class, Task::class, TaskComments::class], version = 1 )
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun commentDao(): CommentDao

    companion object {
        private lateinit var INSTANCE: TaskDatabase
        fun getInstance(context: Context): TaskDatabase {
            if(INSTANCE ==null){
                synchronized(TaskDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java,
                        databaseName)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}