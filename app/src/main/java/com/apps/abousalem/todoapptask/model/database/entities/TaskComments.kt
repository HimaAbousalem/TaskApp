package com.apps.abousalem.todoapptask.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_comments", foreignKeys = [ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns =["task_id"], onDelete = CASCADE)])
data class TaskComments(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "comment_text")
    var commentText: String,
    @ColumnInfo(name = "task_id", index = true)
    var taskId: Int,
    @ColumnInfo(name = "comment_date")
    var commentDate: Date
)