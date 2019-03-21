package com.apps.abousalem.todoapptask.ui.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.component.ActivityComponent
import com.apps.abousalem.todoapptask.dagger.component.DaggerActivityComponent
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.ui.base.BaseActivity
import com.apps.abousalem.todoapptask.ui.base.DialogView
import com.apps.abousalem.todoapptask.ui.taskdetails.TaskDetailsActivity
import com.apps.abousalem.todoapptask.utils.ListBinder
import com.apps.abousalem.todoapptask.utils.TaskItemListener
import com.apps.abousalem.todoapptask.utils.TasksDiffUtils
import com.apps.abousalem.todoapptask.utils.taskExtra
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_task.*
import javax.inject.Inject
import android.view.MenuItem
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.service.AlarmReceiver


class TaskActivity : BaseActivity(), TaskItemListener,RecyclerSwiping.RecyclerItemTouchHelperListener {

    @Inject
    lateinit var taskViewModel: TaskViewModel
    @Inject
    lateinit var adapter:GroupAdapter<ViewHolder>
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var component: ActivityComponent
    var isFiltered = false
    val REQUESTED_CODE = 1
    var tasks: MutableList<TaskRecyclerItem> = mutableListOf()
    var filteredList: MutableList<TaskRecyclerItem> = mutableListOf()
    var unFilteredList: MutableList<TaskRecyclerItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        component = DaggerActivityComponent.builder()
            .todoTaskAppComponent((application as TodoTaskApplication).getComponent())
            .activityModule(ActivityModule(this))
            .build()
        component.inject(this)
        setupRecycler()
        listenToDataChanged()
        filter_btn.setOnClickListener {
            isFiltered =!isFiltered
            if(isFiltered){
                filter_btn.setBackgroundResource(R.drawable.filter_blue)
                updateTaskRecycle()
            }else{
                filter_btn.setBackgroundResource(R.drawable.filter_black)
                updateTaskRecycle()
            }
        }
        add_task_btn.setOnClickListener{
            showDialog()
        }
        setupToolbar()
     }

    private fun setupRecycler() {
        tasks_recycler_view.layoutManager = linearLayoutManager
        tasks_recycler_view.adapter = adapter
        ItemTouchHelper(RecyclerSwiping(0,ItemTouchHelper.LEFT, this)).attachToRecyclerView(tasks_recycler_view)
        adapter.setOnItemClickListener { taskRecyclerItem, _ ->
            startActivity(Intent(this, TaskDetailsActivity::class.java).putExtra(taskExtra,(taskRecyclerItem as TaskRecyclerItem).task.id))
        }
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        taskViewModel.deleteTask(tasks[position].task).subscribe({Log.d("Delete Task", "Task deleted!")},
            {Log.d("Delete Task", "Task still there!")})
    }

    private fun setupToolbar(){
        setSupportActionBar(task_toolbar)
        if(supportActionBar != null) {
            supportActionBar!!.title = taskViewModel.getUserName() + "'Tasks"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun listenToDataChanged() {
        val id = taskViewModel.getUserId()
        taskViewModel.getAllTasks(id).observe(this, Observer<List<Task>> { allTasks ->
            filteredList.clear()
            unFilteredList.clear()
            allTasks.forEach{
                val taskItem = TaskRecyclerItem(it,this)
                if(it.done){filteredList.add(taskItem)}
                unFilteredList.add(taskItem)
            }
            updateTaskRecycle()
        })
    }

    private fun updateTaskRecycle() {
        if(isFiltered){ findDiff(tasks,filteredList)}
        else{ findDiff(tasks, unFilteredList)}
        adapter.update(tasks)
    }

    private fun findDiff(oldTasks: MutableList<TaskRecyclerItem>, newTask: MutableList<TaskRecyclerItem>){
        val taskDiffCallBack = TasksDiffUtils(oldTasks,newTask)
        val diffResult = DiffUtil.calculateDiff(taskDiffCallBack)
        diffResult.dispatchUpdatesTo(ListBinder(oldTasks,newTask))
    }
    override fun updateTask(task: Task) {
        taskViewModel.updateTask(task)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Log.d("updateTask", "updated successfully!")},{ Log.d("updateTask", "something went wrong!")})
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("unDoneTasks", (unFilteredList.size - filteredList.size))
        val pendingIntent = PendingIntent.getBroadcast(this, REQUESTED_CODE , intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, pendingIntent)
    }
    private fun showDialog() {
        val fm = supportFragmentManager
        val taskDialog = DialogView()
        taskDialog.show(fm,"fragment_dialog")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                taskViewModel.clearPrefs()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
