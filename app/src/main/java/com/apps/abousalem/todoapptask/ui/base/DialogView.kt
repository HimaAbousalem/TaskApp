package com.apps.abousalem.todoapptask.ui.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.ui.task.TaskActivity
import com.apps.abousalem.todoapptask.ui.task.TaskViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.task_dialoge.view.*
import timber.log.Timber
import javax.inject.Inject

class DialogView: DialogFragment(){
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var taskViewModel:TaskViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private val disposable: CompositeDisposable? = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.task_dialoge,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as TaskActivity).getActivityComponent().inject(this)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
        sharedViewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       view.task_create_btn.setOnClickListener {
           createTask(view)
       }

    }

    private fun createTask(view: View) {
        val taskName = view.task_text_dialoge.text.toString().trim()
        val id = sharedViewModel.getUserId()
        if(taskName.isEmpty())return
        disposable?.add(taskViewModel.addNewTask(Task(taskName = taskName, userId = id, done = false, taskPriority = 0))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({Timber.d("Task Added Successfully.")
                dismiss()},{Timber.d("Task didn't added!")}))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.clear()
    }
}