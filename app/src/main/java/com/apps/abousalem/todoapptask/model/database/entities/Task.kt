package com.apps.abousalem.todoapptask.model.database.entities

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task_table", foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"],onDelete = CASCADE)])
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "user_id", index = true)
    var userId: Int,
    @ColumnInfo(name = "task_done")
    var done: Boolean,
    @ColumnInfo(name = "task_name")
    var taskName: String,
    @ColumnInfo(name = "task_priority")
    var taskPriority : Int
):Parcelable{
    @ColumnInfo(name = "task_date")
    var taskDate: Date? = null
    override fun equals(other: Any?): Boolean {
        val task = other as Task
        return (taskName == task.taskName && taskPriority == task.taskPriority && done == task.done)
    }
}