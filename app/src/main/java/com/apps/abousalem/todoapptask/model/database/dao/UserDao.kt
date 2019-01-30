package com.apps.abousalem.todoapptask.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps.abousalem.todoapptask.model.database.entities.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao{
    @Insert
    fun addUser(user: User):Completable

    @Query("Select * From user_table where user_name = :userName")
    fun getUserByUserName(userName: String): Single<User>
}