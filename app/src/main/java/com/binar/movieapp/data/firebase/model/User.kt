package com.binar.movieapp.data.firebase.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var email: String,
    var password: String? = "",
    var fullName: String? = "",
    var dateOfBirth: String? = "",
    var address: String? = ""
)
