package com.test.testcompose.ui.screen.user.detail

import com.test.testcompose.model.UserDetail

data class UserDetailState(
    val data: UserDetail = UserDetail(),
    val isLoading: Boolean = false,
    val error: String? = null,
)