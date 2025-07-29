package com.test.testcompose.ui.screen.login

sealed class LoginEvent {
    object OnInit: LoginEvent()
    data class UsernameChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    data class LoginButtonClicked(val onSuccessLogin: () -> Unit) : LoginEvent()
}