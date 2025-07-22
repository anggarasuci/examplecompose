package com.test.testcompose.ui.screen.user.detail

sealed class UserDetailEvent {
    data class OnLoad(val id: Int) : UserDetailEvent()
}