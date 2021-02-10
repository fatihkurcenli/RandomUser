package com.autumnsun.randomuser.data

import com.autumnsun.randomuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://randomuser.me/api/?results=10
    @GET(".")
    fun getUsers(@Query("results") results:Int): Call<UserResponse>
}