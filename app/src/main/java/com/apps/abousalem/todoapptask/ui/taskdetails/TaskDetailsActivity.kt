package com.apps.abousalem.todoapptask.ui.taskdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.component.DaggerActivityComponent
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.utils.TaskItemListener
import com.apps.abousalem.todoapptask.utils.taskExtra
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.activity_task_details.*
import java.util.*
import javax.inject.Inject

class TaskDetailsActivity : AppCompatActivity(), TaskItemListener {

    @Inject
    lateinit var taskDetailsViewModel: TaskDetailsViewModel
    @Inject
    lateinit var adapter: GroupAdapter<ViewHolder>
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    var listItem:  MutableList<Group> = mutableListOf()
    var flagForComments =0
    var flagTask = 0
    var taskId: Int? = null
    var task: Task? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        DaggerActivityComponent.builder()
            .todoTaskAppComponent((application as TodoTaskApplication).getComponent())
            .activityModule(ActivityModule(this))
            .build().inject(this)
        taskId= intent.getIntExtra(taskExtra,-1)
        setupRecycler()
        send_comment_btn.setOnClickListener {
            addComment()
        }
        delete_task_btn_details.setOnClickListener {
            taskDetailsViewModel.deleteTask(task!!)
                .subscribe({
                    Toast.makeText(this,"Task Deleted Successfully!", Toast.LENGTH_SHORT).show()
                    finish()},{})
        }
        listenToHeaderChange()
    }
    private fun setupToolbar(){
        setSupportActionBar(details_toolbar)
        if(supportActionBar != null) {
            if(task!=null)
            supportActionBar!!.title = task!!.taskName
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun listenToHeaderChange() {
        taskDetailsViewModel.getTaskById(taskId!!).observe(this, Observer<Task>{
            task = it
            setupToolbar()
            if(it!=null){
                if(flagTask ==0) {
                    listItem.add(0, TaskDetailsHeader(it, this))
                    listenToCommentsChange()
                    flagTask +=1
                }else{
                    listItem.removeAt(0)
                    listItem.add(0,TaskDetailsHeader(it,this))
                }
                adapter.update(listItem)
            }
        })
    }

    override fun updateTask(task: Task) {
        taskDetailsViewModel.updateTask(task)
            .subscribe({ Log.d("updateTask", "updated successfully!")},{ Log.d("updateTask", "something went wrong!")})
    }

    private fun listenToCommentsChange() {
        taskDetailsViewModel.getAllComments(taskId!!).observe(this, Observer<List<TaskComments>> { allComments ->
            if(flagForComments == 0) {
                allComments.forEach {
                    listItem.add(1,CommentItem(it))
                }
                flagForComments += 1
            }else{
                if(allComments.isNotEmpty())
                  listItem.add(1, CommentItem(allComments[allComments.size-1]))
            }
            adapter.update(listItem)
        })
    }

    private fun addComment() {
        val commentText = comment_edit_text.text.toString().trim()
        if(commentText.isEmpty())return
        taskDetailsViewModel.addComment(TaskComments(commentText = commentText, commentDate = Date(), taskId = taskId!!))
            .subscribe({ Log.d("comment", "comment Added Successfully!")
            comment_edit_text.text.clear()},{})
    }

    private fun setupRecycler() {
        task_details_recycler_view.layoutManager = linearLayoutManager
        task_details_recycler_view.adapter = adapter
    }
}
