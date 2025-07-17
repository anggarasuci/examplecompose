package com.test.testcompose.ui.screen.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.testcompose.model.PokemonDetail

@Composable
fun PokemonDetailView(
    data: PokemonDetail,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 70.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${data.id} ${
                data.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }
            }",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        PokemonTypeSection(types = data.types)
        PokemonPosturesSection(
            weight = data.weight,
            height = data.height
        )
        Spacer(modifier = Modifier.height(16.dp))
        PokemonStatus(data = data)
    }
}