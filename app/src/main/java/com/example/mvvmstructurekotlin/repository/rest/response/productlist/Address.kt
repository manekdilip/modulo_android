package com.example.mvvmstructurekotlin.repository.rest.response.productlist

data class Address(
    val city: String,
    val country: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String
)