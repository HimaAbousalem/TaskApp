package com.apps.abousalem.todoapptask.utils

import com.apps.abousalem.todoapptask.model.database.entities.Task

interface TaskItemListener{
    fun updateTask(task: Task)
}