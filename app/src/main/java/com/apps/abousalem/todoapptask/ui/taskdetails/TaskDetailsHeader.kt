package com.apps.abousalem.todoapptask.ui.taskdetails

import android.app.DatePickerDialog
import android.view.View
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.utils.TaskItemListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.details_header.view.*
import kotlinx.android.synthetic.main.task_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsHeader(val task: Task, val listener: TaskItemListener): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.details_header
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val item = viewHolder.itemView
        if(task.done){
            item.done_btn_details.setBackgroundResource(R.drawable.task_done)
        }else{
            item.done_btn_details.setBackgroundResource(R.drawable.task_not_done)
        }
        if(task.taskDate !=null){
            val df = SimpleDateFormat("MMMM dd yyyy", Locale.US)
            item.date_txt_details.text = df.format(task.taskDate)
        }else{
            item.date_txt_details.text = "Open Calender"
        }
        setupDatePicker(item)
        setPriorityToView(item)
        handleClickListener(item)
    }

    private fun setupDatePicker(item: View) {
        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            var cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val newTask = createNewTask()
            newTask.taskDate = cal.time
            listener.updateTask(newTask)
        }
        item.date_txt_details.setOnClickListener{
            openDatePicker(item, datePickerListener)
        }
    }

    private fun handleClickListener(item: View) {
        item.done_btn_details.setOnClickListener {
            val updatedTask =createNewTask()
            updatedTask.done = !task.done
            listener.updateTask(updatedTask)
        }
        item.priority_one_details_activity.setOnClickListener {
            if(task.taskPriority!=1) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 1
                listener.updateTask(updatedTask)
            }
        }
        item.priority_two_details_activity.setOnClickListener {
            if(task.taskPriority!=2) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 2
                listener.updateTask(updatedTask)
            }
        }
        item.priority_three_details_activity.setOnClickListener {
            if(task.taskPriority!=3) {
                val updatedTask = createNewTask()
                updatedTask.taskPriority = 3
                listener.updateTask(updatedTask)
            }
        }

    }
    private fun openDatePicker(v: View, datePickerListener: DatePickerDialog.OnDateSetListener){
        val date = task.taskDate
        val calendar = Calendar.getInstance()

        if (date != null){
            calendar.time = date
        }
        DatePickerDialog(v.context,datePickerListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()

    }
    private fun createNewTask(): Task {
        val newTask = Task(task.id, task.userId,task.done,task.taskName,task.taskPriority)
        newTask.taskDate = task.taskDate
        return newTask
    }

    private fun setPriorityToView(item: View) {
        when {
            task.taskPriority == 1 -> {
                item.priority_one_details_activity.setBackgroundResource(R.drawable.p1)
                item.priority_two_details_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_details_activity.setBackgroundResource(R.drawable.r3)
            }
            task.taskPriority == 2 -> {
                item.priority_one_details_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_details_activity.setBackgroundResource(R.drawable.p2)
                item.priority_three_details_activity.setBackgroundResource(R.drawable.r3)
            }
            task.taskPriority == 3 -> {
                item.priority_one_details_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_details_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_details_activity.setBackgroundResource(R.drawable.p3)
            }
            else -> {
                item.priority_one_details_activity.setBackgroundResource(R.drawable.r1)
                item.priority_two_details_activity.setBackgroundResource(R.drawable.r2)
                item.priority_three_details_activity.setBackgroundResource(R.drawable.r3)
            }
        }
    }

}