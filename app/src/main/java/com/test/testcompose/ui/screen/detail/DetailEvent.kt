package com.test.testcompose.ui.screen.detail

sealed class DetailEvent {
    data class Init(val name: String) : DetailEvent()
}