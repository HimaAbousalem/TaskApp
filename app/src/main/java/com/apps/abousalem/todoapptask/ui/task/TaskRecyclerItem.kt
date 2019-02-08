package com.apps.abousalem.todoapptask.ui.task

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.utils.TaskItemListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.task_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskRecyclerItem(var task: Task, val listener: TaskItemListener): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.task_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val item =viewHolder.itemView
        if(task.done){
            item.done_task_btn.setBackgroundResource(R.drawable.task_done)
        }else{
            item.done_task_btn.setBackgroundResource(R.drawable.task_not_done)
        }
        item.task_name_task_activity.text = task.taskName
        if(task.taskDate!=null) {
            val df = SimpleDateFormat("MMMM dd yyyy", Locale.US)
            item.date_txt_task_activity.text = df.format(task.taskDate)
        }else{
            item.date_txt_task_activity.text = ""
        }
        clickListenerEvents(item)
        declareTaskPriority(item)
    }

    private fun declareTaskPriority(item: View) {
        when {
            task.taskPriority == 1 -> {
                item.priority_one_task_activity.setBackgroundResource(R.drawable.p1)
                item.priority_two_task_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_task_activity.setBackgroundResource(R.drawable.r3)
            }
            task.taskPriority == 2 -> {
                item.priority_one_task_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_task_activity.setBackgroundResource(R.drawable.p2)
                item.priority_three_task_activity.setBackgroundResource(R.drawable.r3)
            }
            task.taskPriority == 3  -> {
                item.priority_one_task_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_task_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_task_activity.setBackgroundResource(R.drawable.p3)
            }
            else ->{
                item.priority_one_task_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_task_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_task_activity.setBackgroundResource(R.drawable.r3)
            }
        }
    }

    private fun clickListenerEvents(item: View) {
        item.done_task_btn.setOnClickListener {
            val updatedTask =createNewTask()
            updatedTask.done = !task.done
            listener.updateTask(updatedTask)
        }
        item.priority_one_task_activity.setOnClickListener {
            if(task.taskPriority!=1) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 1
                listener.updateTask(updatedTask)
            }
        }
        item.priority_two_task_activity.setOnClickListener {
            if(task.taskPriority!=2) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 2
                listener.updateTask(updatedTask)
            }
        }
        item.priority_three_task_activity.setOnClickListener {
            if(task.taskPriority!=3) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 3
                listener.updateTask(updatedTask)
            }
        }
    }

    private fun createNewTask(): Task {
        val newTask = Task(task.id, task.userId,task.done,task.taskName,task.taskPriority)
        newTask.taskDate = task.taskDate
        return newTask
    }

    override fun equals(other: Any?): Boolean {
        val taskItem = other as TaskRecyclerItem
        return (task.id == taskItem.task.id && task.done == taskItem.task.done && task.taskPriority == taskItem.task.taskPriority &&
                task.taskDate == taskItem.task.taskDate)
    }
}