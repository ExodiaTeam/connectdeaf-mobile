package com.connectdeaf.network.dtos

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ServiceRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("value") val value: Double,
    //val categories: String,
    // val imageUrl: String
)