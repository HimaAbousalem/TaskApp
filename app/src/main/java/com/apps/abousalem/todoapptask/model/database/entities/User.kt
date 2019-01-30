package com.apps.abousalem.todoapptask.model.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table",indices = [Index(value = ["user_name"],unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int= 0,
    @ColumnInfo(name = "user_name")
    var userName: String
): Parcelable
