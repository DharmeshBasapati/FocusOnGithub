package com.app.focusongithub.api

import com.app.focusongithub.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("users")
    fun getAllUsers(): Call<List<Users>>
}