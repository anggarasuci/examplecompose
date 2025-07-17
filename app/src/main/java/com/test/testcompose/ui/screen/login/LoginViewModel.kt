package com.test.testcompose.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel() : ViewModel() {
    private val _mockUsername = "testuser"
    private val _mockPassword = "password123"

    var state by mutableStateOf(LoginState())
        private set

    suspend fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    formData = state.formData.copy(
                        password = state.formData.password.copy(
                            value = event.value,
                            errorMessage = "",
                            isError = false
                        )
                    )
                )
            }

            is LoginEvent.UsernameChanged -> {
                state = state.copy(
                    formData = state.formData.copy(
                        username = state.formData.username.copy(
                            value = event.value,
                            errorMessage = "",
                            isError = false
                        )
                    )
                )
            }

            is LoginEvent.LoginButtonClicked -> {
                handleOnLogin(event.onSuccessLogin)
            }
        }
    }

    private suspend fun handleOnLogin(onSuccessLogin: () -> Unit) {
        state = state.copy(isLoading = true, errorMessage = null)
        delay(1000)

        val usernameValue = state.formData.username.value
        val passwordValue = state.formData.password.value

        val isUsernameEmpty = usernameValue.isEmpty()
        val isPasswordEmpty = passwordValue.isEmpty()
        if (isUsernameEmpty) {
            state = state.copy(
                formData = state.formData.copy(
                    username = state.formData.username.copy(
                        isError = true,
                        errorMessage = "Username cannot be empty"
                    ),
                ),
                errorMessage = null
            )
        }

        if (isPasswordEmpty) {
            state = state.copy(
                formData = state.formData.copy(
                    password = state.formData.password.copy(
                        isError = true,
                        errorMessage = "Password cannot be empty"
                    )
                ),
                errorMessage = null
            )
        }

        if (isUsernameEmpty || isPasswordEmpty) {
            state = state.copy(isLoading = false)
            return
        }

        if (usernameValue != _mockUsername || passwordValue != _mockPassword) {
            state = state.copy(
                isLoading = false,
                errorMessage = "Invalid username or password"
            )
            return
        }

        onSuccessLogin()
    }
}