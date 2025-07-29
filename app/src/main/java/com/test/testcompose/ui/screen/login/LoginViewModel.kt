package com.test.testcompose.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testcompose.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
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
                var a = event.value
                when {
                    event.value.length == 2 -> {
                        a = event.value + "/"
                    }
                }
                state = state.copy(
                    formData = state.formData.copy(
                        username = state.formData.username.copy(
                            value = a,
                            errorMessage = "",
                            isError = false
                        )
                    )
                )
            }

            is LoginEvent.LoginButtonClicked -> {
                handleOnLogin(event.onSuccessLogin)
            }

            LoginEvent.OnInit -> handleOnInit()
        }
    }

    private fun handleOnInit() = viewModelScope.launch {
        state = state.copy(isLoadingAuth = true)
        delay(1000)
        val isAuthenticated = repository.checkAuth()

        if (!isAuthenticated && repository.getRefreshToken()?.isBlank() == false) {
            val isTokenRefreshed = repository.refreshToken()
            if (!isTokenRefreshed) {
                state = state.copy(
                    isAuthenticated = false,
                    isLoadingAuth = false,
                    errorMessage = "Authentication failed, please login again."
                )
                return@launch
            }
        }

        state = state.copy(
            isAuthenticated = isAuthenticated && repository.getJwt()?.isBlank() == false,
            isLoadingAuth = isAuthenticated
        )
    }


    private fun handleOnLogin(onSuccessLogin: () -> Unit) = viewModelScope.launch {
        state = state.copy(isLoading = true, errorMessage = null, isAuthenticated = false)
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
            return@launch
        }

        repository.login(
            username = usernameValue,
            password = passwordValue
        ).fold(
            onSuccess = {
                state = state.copy(
                    isLoading = false,
                    errorMessage = null,
                    isAuthenticated = true
                )
                onSuccessLogin()
            },
            onFailure = { exception ->
                state = state.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Login failed"
                )
            }
        )
    }
}