package com.apps.abousalem.todoapptask.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apps.abousalem.todoapptask.TodoTaskApplication
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.User
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel(application: Application): BaseViewModel(application){
   /* @Inject
    lateinit var repository: TaskRepository
    init{
        (application as TodoTaskApplication).getComponent().inject(this)
    }*/
    fun addUser(user: User): Completable {
        return repository.saveUser(user)
            .subscribeOn(Schedulers.io())
    }

    fun getUser(userName: String): Single<User> {
        return repository.getUser(userName)
            .subscribeOn(Schedulers.io())
    }


}