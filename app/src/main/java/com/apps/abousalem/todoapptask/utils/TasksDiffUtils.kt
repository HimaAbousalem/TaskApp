package com.apps.abousalem.todoapptask.utils

import androidx.recyclerview.widget.DiffUtil
import com.apps.abousalem.todoapptask.ui.task.TaskRecyclerItem

class TasksDiffUtils(private val oldList: MutableList<TaskRecyclerItem>, private val newList: MutableList<TaskRecyclerItem>): DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].task.id == newList[newItemPosition].task.id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].task.done == newList[newItemPosition].task.done &&
                oldList[oldItemPosition].task.taskPriority == newList[newItemPosition].task.taskPriority
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}