package com.apps.abousalem.todoapptask.ui.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.dagger.component.DaggerActivityComponent
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.ui.task.TaskActivity
import com.apps.abousalem.todoapptask.ui.task.TaskViewModel
import kotlinx.android.synthetic.main.task_dialoge.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DialogView: DialogFragment(){
    @Inject
    lateinit var taskViewModel:TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.task_dialoge,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as TaskActivity).component.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       view.task_create_btn.setOnClickListener {
           createTask(view)
       }

    }

    private fun createTask(view: View) {
        val taskName = view.task_text_dialoge.text.toString().trim()
        val id = taskViewModel.getUserId()
        if(taskName.isEmpty())return
        taskViewModel.addNewTask(Task(taskName = taskName, userId = id, done = false, taskPriority = 0))
            .subscribe({Timber.d("Task Added Successfully.")
                dismiss()},{Timber.d("Task didn't added!")})
    }
}