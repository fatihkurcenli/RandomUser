package com.autumnsun.randomuser.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("results")
        var userList : ArrayList<Results>
)