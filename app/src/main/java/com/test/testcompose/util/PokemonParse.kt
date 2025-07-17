package com.test.testcompose.util

import androidx.compose.ui.graphics.Color
import com.test.testcompose.model.Stat
import com.test.testcompose.model.TypeSlot
import com.test.testcompose.ui.theme.*
import java.util.Locale

fun parseTypeToColor(type: TypeSlot): Color {
    return when (type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormalColor
        "fire" -> TypeFireColor
        "water" -> TypeWaterColor
        "electric" -> TypeElectricColor
        "grass" -> TypeGrassColor
        "ice" -> TypeIceColor
        "fighting" -> TypeFightingColor
        "poison" -> TypePoisonColor
        "ground" -> TypeGroundColor
        "flying" -> TypeFlyingColor
        "psychic" -> TypePsychicColor
        "bug" -> TypeBugColor
        "rock" -> TypeRockColor
        "ghost" -> TypeGhostColor
        "dragon" -> TypeDragonColor
        "dark" -> TypeDarkColor
        "steel" -> TypeSteelColor
        "fairy" -> TypeFairyColor
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when (stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when (stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}