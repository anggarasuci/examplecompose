package com.test.testcompose

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.test.testcompose.repository.PokemonRepository
import com.test.testcompose.ui.screen.detail.DetailScreen
import com.test.testcompose.ui.screen.detail.DetailViewModel
import com.test.testcompose.ui.screen.landing.LandingScreen
import com.test.testcompose.ui.screen.landing.LandingViewModel

sealed class Page {
    object Landing : Page()
    data class Detail(val name: String) : Page()
    object Form : Page()
    object Login : Page()
}

@Composable
fun MainPage() {
    val (currentPage, setCurrentPage) = remember { mutableStateOf<Page>(Page.Landing) }
    val landingViewModel = remember {
        LandingViewModel(
            PokemonRepository()
        )
    }
    val lazyGridState = rememberLazyGridState()
    when (val page = currentPage) {
        is Page.Landing -> LandingPage(
            lazyGridState = lazyGridState,
            viewModel = landingViewModel,
            onNavigate = { id ->
                setCurrentPage(Page.Detail(id))
            })

        is Page.Detail -> DetailPage(
            name = page.name,
            onBack = { setCurrentPage(Page.Landing) })

        is Page.Form -> FormPage(onBack = { setCurrentPage(Page.Landing) })
        is Page.Login -> LoginPage(onBack = { setCurrentPage(Page.Landing) })
    }
}

@Composable
fun LandingPage(
    lazyGridState: LazyGridState,
    viewModel: LandingViewModel,
    onNavigate: (id: String) -> Unit
) {
    val state = viewModel.state
    LandingScreen(
        lazyGridState = lazyGridState,
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = { id -> onNavigate(id) }
    )
}

@Composable
fun DetailPage(name: String, onBack: () -> Unit) {
    val viewModel = remember {
        DetailViewModel(
            PokemonRepository()
        )
    }
    val state = viewModel.state
    DetailScreen(
        name = name,
        state = state,
        onEvent = viewModel::onEvent,
        onBack = { onBack() }
    )
}

@Composable
fun FormPage(onBack: () -> Unit) {
    // TODO: Implement form UI
}

@Composable
fun LoginPage(onBack: () -> Unit) {
    // TODO: Implement login UI
}