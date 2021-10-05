package com.app.focusongithub.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusongithub.room.dao.FocusDao
import com.app.focusongithub.viewmodel.UsersViewModel

class ViewModelFactory(private val focusDao: FocusDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(focusDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}