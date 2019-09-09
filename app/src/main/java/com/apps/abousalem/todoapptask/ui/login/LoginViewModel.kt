package com.apps.abousalem.todoapptask.ui.login

import androidx.lifecycle.ViewModel
import com.apps.abousalem.todoapptask.model.TaskRepository
import com.apps.abousalem.todoapptask.model.database.entities.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: TaskRepository): ViewModel(){

    fun addUser(user: User): Completable {
        return repository.saveUser(user)
    }

    fun getUser(userName: String): Single<User> {
        return repository.getUser(userName)
    }

    fun saveUser(user: User){
        repository.saveUserToPrefs(user)
    }
}