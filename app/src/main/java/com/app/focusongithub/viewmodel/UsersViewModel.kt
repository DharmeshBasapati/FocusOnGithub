package com.app.focusongithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusongithub.api.RetrofitBuilder
import com.app.focusongithub.model.Users
import com.app.focusongithub.room.dao.FocusDao
import com.app.focusongithub.room.entity.BookmarkedUsers
import com.app.focusongithub.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(private val focusDao: FocusDao) : ViewModel() {

    private val users = MutableLiveData<Resource<List<Users>>>()

    fun getUsers(): LiveData<Resource<List<Users>>> = users

    init {

        fetchUsers()

    }

    private fun fetchUsers() {

        users.postValue(Resource.loading(null))

        viewModelScope.launch(Dispatchers.IO) {

            RetrofitBuilder.apiServices.getAllUsers().enqueue(object : Callback<List<Users>> {
                override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                    users.postValue(Resource.success(response.body()))
                }

                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    users.postValue(Resource.error(null, t.message))
                }
            }


            )

        }

    }

    fun bookmarkThisUser(bookmarkedUsers: BookmarkedUsers){

        viewModelScope.launch(Dispatchers.IO) {

            focusDao.bookmarkThisUser(bookmarkedUsers)

        }

    }

    fun unBookmarkThisUser(bookmarkedUsers: BookmarkedUsers){

        viewModelScope.launch(Dispatchers.IO) {

            focusDao.unbookmarkThisUser(bookmarkedUsers)
            fetchBookmarkedUsersFromDB()
        }

    }

    private val bookMarkedUsers = MutableLiveData<List<BookmarkedUsers>>()

    fun getBookMarkedUsers(): LiveData<List<BookmarkedUsers>> = bookMarkedUsers

    fun fetchBookmarkedUsersFromDB(){

        viewModelScope.launch(Dispatchers.IO) {

            bookMarkedUsers.postValue(focusDao.getAllBookmarkedUsers())

        }

    }

}