package com.test.testcompose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.test.testcompose.repository.PokemonRepository
import com.test.testcompose.repository.UserRepository
import com.test.testcompose.ui.screen.detail.DetailScreen
import com.test.testcompose.ui.screen.detail.DetailViewModel
import com.test.testcompose.ui.screen.landing.LandingScreen
import com.test.testcompose.ui.screen.landing.LandingViewModel
import com.test.testcompose.ui.screen.login.LoginScreen
import com.test.testcompose.ui.screen.login.LoginViewModel
import com.test.testcompose.ui.screen.profile.ProfileScreen
import com.test.testcompose.ui.screen.user.detail.USerDetailViewModel
import com.test.testcompose.ui.screen.user.detail.UserDetailScreen
import com.test.testcompose.ui.screen.user.landing.UserLandingScreen
import com.test.testcompose.ui.screen.user.landing.UserLandingViewModel
import kotlinx.coroutines.launch

sealed class Page {
    object Landing : Page()
    data class Detail(val name: String) : Page()
    object Form : Page()
    object Login : Page()
    object Profile : Page()
    object Favourite : Page()
    object UserLanding : Page()
    data class UserDetail(val id: Int) : Page()
}


@Composable
fun MainPage() {
    val (currentPage, setCurrentPage) = remember { mutableStateOf<Page>(Page.Login) }
    val landingViewModel = remember {
        LandingViewModel(
            PokemonRepository()
        )
    }
    val lazyGridState = rememberLazyGridState()

    val userLandingViewModel = remember {
        UserLandingViewModel(
            UserRepository()
        )
    }
    val lazyListState = rememberLazyListState()

    Scaffold(
        bottomBar = {
            if (currentPage is Page.Landing || currentPage is Page.Profile || currentPage is Page.Favourite) {
                BottomNavigationBar(currentPage = currentPage, onPageSelected = setCurrentPage)
            }
        }
    ) { paddingValues ->
        when (val page = currentPage) {
            is Page.Landing -> LandingPage(
                paddingValues = paddingValues,
                lazyGridState = lazyGridState,
                viewModel = landingViewModel,
                onNavigate = { id ->
                    setCurrentPage(Page.Detail(id))
                })

            is Page.Detail -> DetailPage(
                name = page.name,
                onBack = { setCurrentPage(Page.Landing) })

            is Page.Form -> FormPage(onBack = { setCurrentPage(Page.Landing) })
            is Page.Login -> LoginPage(onNavigate = { setCurrentPage(Page.UserLanding) })
            is Page.Profile -> ProfileScreen(
                paddingValues = paddingValues,
                onNavigate = { setCurrentPage(Page.Login) })

            is Page.Favourite -> {
                //TODO: Implement Favourite Page
            }

            Page.UserLanding -> {
                UserLandingPage(
                    paddingValues = paddingValues,
                    lazyListState = lazyListState,
                    viewModel = userLandingViewModel,
                    onNavigate = { id ->
                        setCurrentPage(Page.UserDetail(id))
                    }
                )
            }

            is Page.UserDetail -> UserDetailPage(
                id = page.id,
                onBack = { setCurrentPage(Page.UserLanding) })
        }
    }
}

@Composable
fun BottomNavigationBar(currentPage: Page, onPageSelected: (Page) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentPage is Page.Landing,
            onClick = { onPageSelected(Page.Landing) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favourite") },
            label = { Text("Favourite") },
            selected = currentPage is Page.Favourite,
            onClick = { onPageSelected(Page.Favourite) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("User") },
            selected = currentPage is Page.Profile,
            onClick = { onPageSelected(Page.Profile) }
        )
    }
}

@Composable
fun LandingPage(
    paddingValues: PaddingValues,
    lazyGridState: LazyGridState,
    viewModel: LandingViewModel,
    onNavigate: (id: String) -> Unit
) {
    val state = viewModel.state
    LandingScreen(
        paddingValues = paddingValues,
        lazyGridState = lazyGridState,
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = { id -> onNavigate(id) }
    )
}

@Composable
fun UserLandingPage(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    viewModel: UserLandingViewModel,
    onNavigate: (id: Int) -> Unit
) {
    val state = viewModel.state
    UserLandingScreen(
        paddingValues = paddingValues,
        lazyListState = lazyListState,
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = { id -> onNavigate(id) }
    )
}

@Composable
fun UserDetailPage(id: Int, onBack: () -> Unit) {
    val viewModel = remember {
        USerDetailViewModel(
            UserRepository()
        )
    }
    val state = viewModel.state
    UserDetailScreen(
        id = id,
        state = state,
        onEvent = viewModel::onEvent,
        onBack = { onBack() }
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
fun LoginPage(onNavigate: () -> Unit) {
    val viewModel = remember {
        LoginViewModel()
    }
    val state = viewModel.state
    val coroutineScope = rememberCoroutineScope()
    LoginScreen(
        state = state,
        onEvent = { event ->
            coroutineScope.launch {
                viewModel.onEvent(event)
            }
        },
        onNavigate = { onNavigate() }
    )
}