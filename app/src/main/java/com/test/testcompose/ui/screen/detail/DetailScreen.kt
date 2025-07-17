package com.test.testcompose.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.testcompose.ui.component.ImageWithLoading
import com.test.testcompose.ui.screen.detail.component.PokemonDetailView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    name: String,
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(true) {
        onEvent(DetailEvent.Init(name))
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {}, navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        )
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
                .padding(padding)
        ) {
            state.data?.let {
                PokemonDetailView(
                    it, modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = (20 + 180 / 2f).dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (state.data != null) {
                    ImageWithLoading(
                        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${state.data.id}.png",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
        }
    }
}