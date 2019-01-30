package com.apps.abousalem.todoapptask.model.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

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
    var taskPriority : Int,
    @ColumnInfo(name = "task_date")
    var taskDate: Date
)