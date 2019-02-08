package com.apps.abousalem.todoapptask.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.dagger.component.DaggerActivityComponent
import com.apps.abousalem.todoapptask.dagger.module.ActivityModule
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.ui.task.TaskActivity
import com.apps.abousalem.todoapptask.ui.base.BaseActivity
import com.apps.abousalem.todoapptask.utils.userExtra
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
        ifUserSignedIn()
        login_btn.setOnClickListener {
            checkUserName()
        }
    }

    private fun ifUserSignedIn() {
        val userName =loginViewModel.getUserName()
        val id = loginViewModel.getUserId()
        if(id == -1)return
        gotoTaskActivity(User(id,userName))
    }

    private fun checkUserName() {
        val userName = username_login.text.toString().trim()
        if(userName.isEmpty())return
        val savedUserName= loginViewModel.getUserName()
        if(userName == savedUserName){
            val savedId = loginViewModel.getUserId()
            gotoTaskActivity(User(savedId,savedUserName))
        }else{
            getUserData(userName)
        }
    }
    private fun getUserData(userName: String){
        loginViewModel.getUser(userName)
            .subscribe({gotoTaskActivity(User(it.id, it.userName))
            saveToSharedPref(User(it.id,it.userName))},{insertNewUser(userName)})
    }

    private fun saveToSharedPref(user: User) {
        loginViewModel.saveUser(user)
    }

    private fun insertNewUser(userName: String) {
        loginViewModel.addUser(User(userName = userName))
            .subscribe({
                getUserData(userName)
                Toast.makeText(this, "user Inserted!",Toast.LENGTH_SHORT).show()},
                { Timber.d("Unsuccessfully insertion, "+ it.message)})
    }
    private fun gotoTaskActivity(user:User) {
        startActivity(Intent(this, TaskActivity::class.java).putExtra(userExtra,user))
    }
}
