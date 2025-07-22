package com.test.testcompose.ui.screen.user.landing

sealed class UserLandingEvent {
    data class OnSearch(val keyword: String?) : UserLandingEvent()
}