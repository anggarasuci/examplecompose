package com.test.testcompose.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.testcompose.ui.component.InputTextWithError

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onNavigate: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputTextWithError(
            value = state.formData.username.value,
            onValueChange = { value -> onEvent(LoginEvent.UsernameChanged(value)) },
            label = "Username",
            errorMessage = state.formData.username.errorMessage
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputTextWithError(
            value = state.formData.password.value,
            onValueChange = { value -> onEvent(LoginEvent.PasswordChanged(value)) },
            label = "Password",
            errorMessage = state.formData.password.errorMessage,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onEvent(LoginEvent.LoginButtonClicked(onSuccessLogin = {
                    onNavigate()
                }))
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Login")
            }
        }

        if (state.errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(state.errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}