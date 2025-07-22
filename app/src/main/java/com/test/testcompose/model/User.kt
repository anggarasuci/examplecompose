package com.test.testcompose.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)

data class UserDetail(
    val id: Int = 0,
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val image: String = "",
    val email: String = "",
    val phone: String = "",
    val address: Address = Address(),
    val company: Company = Company(),
    val age: Int = 0,
    val gender: String = "",
    val birthDate: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val eyeColor: String = ""
)

data class Address(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",
    val country: String = ""
)

data class Company(
    val department: String = "",
    val name: String = "",
    val title: String = ""
)
