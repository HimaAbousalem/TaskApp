package com.apps.abousalem.todoapptask.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel(application: Application): BaseViewModel(application){

    fun addUser(user: User): Completable {
        return repository.saveUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(userName: String): Single<User> {
        return repository.getUser(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveUser(user: User){
        repository.saveUserToPrefs(user)
    }



}