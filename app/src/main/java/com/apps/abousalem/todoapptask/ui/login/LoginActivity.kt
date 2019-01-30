package com.apps.abousalem.todoapptask.ui.login

import android.content.Intent
import android.os.Bundle
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.component.DaggerActivityComponent
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.ui.TaskActivity
import com.apps.abousalem.todoapptask.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       val component = DaggerActivityComponent.builder()
           .todoTaskAppComponent((application as TodoTaskApplication).getComponent())
           .activityModule(ActivityModule(this))
           .build().inject(this)
        login_btn.setOnClickListener {
            checkUserName()
        }
    }

    private fun checkUserName() {
        val userName = username_login.text.toString().trim()
        if(userName.isEmpty())return
        loginViewModel.getUser(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({gotoTaskActivity()},{insertNewUser(userName)})
    }

    private fun insertNewUser(userName: String) {
        loginViewModel.addUser(User(userName = userName))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({Timber.d("inserted successfully")
            gotoTaskActivity()},{ Timber.d("Unsuccessfully insertion, "+ it.message)})
    }

    private fun gotoTaskActivity() {
        startActivity(Intent(this,TaskActivity::class.java))
    }
}
