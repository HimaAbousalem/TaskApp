package com.apps.abousalem.todoapptask.ui.taskdetails

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.extenstion.hideKeyboard
import com.apps.abousalem.todoapptask.model.database.entities.Task
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.ui.base.BaseActivity
import com.apps.abousalem.todoapptask.ui.base.SharedViewModel
import com.apps.abousalem.todoapptask.ui.base.ViewModelFactory
import com.apps.abousalem.todoapptask.utils.TaskItemListener
import com.apps.abousalem.todoapptask.utils.taskExtra
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_task_details.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TaskDetailsActivity : BaseActivity(), TaskItemListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var taskDetailsViewModel: TaskDetailsViewModel
    private val disposable: CompositeDisposable? = CompositeDisposable()

    private lateinit var adapter: GroupAdapter<ViewHolder>
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    private var listItem:  MutableList<Group> = mutableListOf()
    private var flagForComments =0
    private var flagTask = 0
    private var taskId: Int? = null
    var task: Task? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        getActivityComponent().inject(this)
        sharedViewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]
        taskDetailsViewModel = ViewModelProvider(this, viewModelFactory)[TaskDetailsViewModel::class.java]
        taskId= intent.getIntExtra(taskExtra,-1)
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
        send_comment_btn.setOnClickListener {
            addComment()
            hideKeyboard()
        }
        delete_task_btn_details.setOnClickListener {
            disposable?.add(sharedViewModel.deleteTask(task!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this,"Task Deleted Successfully!", Toast.LENGTH_SHORT).show()
                    finish()},{}))
        }
        listenToHeaderChange()
    }

    override fun onStop() {
        super.onStop()
        disposable?.clear()
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
        disposable?.add(sharedViewModel.updateTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d("updated successfully!")},{ Timber.d("something went wrong!")}))
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
        disposable?.add(taskDetailsViewModel.addComment(TaskComments(commentText = commentText, commentDate = Date(), taskId = taskId!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d("comment Added Successfully!")
            comment_edit_text.text.clear()},{}))
    }

    private fun setupRecycler() {
        adapter = GroupAdapter()
        task_details_recycler_view.layoutManager = linearLayoutManager
        task_details_recycler_view.adapter = adapter
    }
}
