package com.example.mvvmstructurekotlin.repository.rest.response.productlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ProductListResponse(
    val devices: MutableList<Device>,
    val user: User
) {
    @Entity(tableName = "employee")
    class Device (
        @PrimaryKey
        @SerializedName("id")
        val id: Int = 0,
        val deviceName: String = "",
        val intensity: Int = 0,
        val mode: String = "",
        val position: Int = 0,
        val productType: String = "",
        val temperature: Int = 0
    )
}