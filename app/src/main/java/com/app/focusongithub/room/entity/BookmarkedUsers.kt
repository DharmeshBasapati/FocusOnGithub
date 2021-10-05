package com.app.focusongithub.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bookmarkedUsers")
data class BookmarkedUsers(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String,

):Serializable
