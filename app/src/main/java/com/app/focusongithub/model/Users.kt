package com.app.focusongithub.model

data class Users(
    val id: Int,
    val login: String,
    val avatar_url: String,
    var isBookmarked: Boolean = false
)
