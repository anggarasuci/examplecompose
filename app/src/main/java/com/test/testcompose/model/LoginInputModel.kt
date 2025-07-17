package com.test.testcompose.model

data class LoginInputModel(
    val username: InputModel = InputModel(),
    val password: InputModel = InputModel(),
)

data class InputModel(
    val value: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
