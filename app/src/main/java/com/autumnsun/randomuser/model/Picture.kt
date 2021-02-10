package com.autumnsun.randomuser.model

import com.google.gson.annotations.SerializedName

data class Picture(
        @SerializedName("large")
        var large:String
)