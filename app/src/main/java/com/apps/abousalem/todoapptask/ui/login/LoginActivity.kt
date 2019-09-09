package com.apps.abousalem.todoapptask.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.extenstion.hideKeyboard
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.ui.task.TaskActivity
import com.apps.abousalem.todoapptask.ui.base.BaseActivity
import com.apps.abousalem.todoapptask.ui.base.SharedViewModel
import com.apps.abousalem.todoapptask.ui.base.ViewModelFactory
import com.apps.abousalem.todoapptask.utils.userExtra
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private val disposable: CompositeDisposable? = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getActivityComponent().inject(this)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        sharedViewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]
        ifUserSignedIn()
    }

    override fun onStart() {
        super.onStart()
        login_btn.setOnClickListener {
            checkUserName()
            hideKeyboard()
        }
    }

    override fun onStop() {
        super.onStop()
        disposable?.clear()
    }

    private fun ifUserSignedIn() {
        val userName =sharedViewModel.getUserName()
        val id = sharedViewModel.getUserId()
        if(id == -1)return
        gotoTaskActivity(User(id,userName))
    }

    private fun checkUserName() {
        val userName = username_login.text.toString().trim()
        if(userName.isEmpty())return
        val savedUserName= sharedViewModel.getUserName()
        if(userName == savedUserName){
            val savedId = sharedViewModel.getUserId()
            gotoTaskActivity(User(savedId,savedUserName))
        }else{
            getUserData(userName)
        }
    }

    private fun getUserData(userName: String){
        disposable?.add(loginViewModel.getUser(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({gotoTaskActivity(User(it.id, it.userName))
            saveToSharedPref(User(it.id,it.userName))},{insertNewUser(userName)}))
    }

    private fun saveToSharedPref(user: User) {
        loginViewModel.saveUser(user)
    }

    private fun insertNewUser(userName: String) {
        disposable?.add(loginViewModel.addUser(User(userName = userName))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getUserData(userName)
                Toast.makeText(this, "user Inserted!",Toast.LENGTH_SHORT).show()},
                { Timber.d("Unsuccessfully insertion, ${it.message}")}))
    }
    private fun gotoTaskActivity(user:User) {
        startActivity(Intent(this, TaskActivity::class.java).putExtra(userExtra,user))
    }
}
