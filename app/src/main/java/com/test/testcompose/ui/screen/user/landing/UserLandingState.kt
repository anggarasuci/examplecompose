package com.test.testcompose.ui.screen.user.landing

import com.test.testcompose.model.User

data class UserLandingState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val keyword: String = "",
)