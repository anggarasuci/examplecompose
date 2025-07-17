package com.test.testcompose.ui.screen.login

import com.test.testcompose.model.LoginInputModel

data class LoginState(
    val isLoading: Boolean = false,
    val formData: LoginInputModel = LoginInputModel(),
    val errorMessage: String? = null,
)