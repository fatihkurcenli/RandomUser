package com.autumnsun.randomuser.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Name(
        @SerializedName("first")
        var first:String,
        @SerializedName("last")
        var last:String
):Serializable