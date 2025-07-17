package com.test.testcompose.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>,
    val types: List<TypeSlot>
)

data class Stat(
    val baseStat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)

data class TypeSlot(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)
