package com.test.testcompose.repository

import com.test.testcompose.BuildConfig
import com.test.testcompose.model.PokemonDetail
import com.test.testcompose.model.PokemonItem
import com.test.testcompose.model.Stat
import com.test.testcompose.model.StatInfo
import com.test.testcompose.model.TypeInfo
import com.test.testcompose.model.TypeSlot
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PokemonRepository {
    fun getList(offset: Int, pageSize: Int): List<PokemonItem> {
        val url = URL("${BuildConfig.BASE_URL}/pokemon?offset=$offset&limit=$pageSize")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Accept", "application/json")
        val response = connection.inputStream.bufferedReader().readText()
        connection.disconnect()

        val json = JSONObject(response)
        val data = json.getJSONArray("results")
        val result = mutableListOf<PokemonItem>()
        for (i in 0 until data.length()) {
            val item = data.getJSONObject(i)
            val name = item.getString("name")
            val urlString = item.getString("url")
            val id = urlString.trimEnd('/').split("/").last()
            result.add(PokemonItem(id = id, name = name))
        }
        return result
    }

    fun getDetail(name: String): PokemonDetail {
        val url = URL("${BuildConfig.BASE_URL}/pokemon/$name")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Accept", "application/json")
        val response = connection.inputStream.bufferedReader().readText()
        connection.disconnect()

        val json = JSONObject(response)
        val id = json.getInt("id")
        val pokemonName = json.getString("name")
        val weight = json.getInt("weight")
        val height = json.getInt("height")

        val statsJson = json.getJSONArray("stats")
        val stats = mutableListOf<Stat>()
        for (i in 0 until statsJson.length()) {
            val statObj = statsJson.getJSONObject(i)
            val baseStat = statObj.getInt("base_stat")
            val statInfoObj = statObj.getJSONObject("stat")
            val statInfo = StatInfo(statInfoObj.getString("name"))
            stats.add(Stat(baseStat = baseStat, stat = statInfo))
        }

        val typesJson = json.getJSONArray("types")
        val types = mutableListOf<TypeSlot>()
        for (i in 0 until typesJson.length()) {
            val typeObj = typesJson.getJSONObject(i)
            val slot = typeObj.getInt("slot")
            val typeInfoObj = typeObj.getJSONObject("type")
            val typeInfo = TypeInfo(typeInfoObj.getString("name"))
            types.add(TypeSlot(slot = slot, type = typeInfo))
        }

        return PokemonDetail(
            id = id,
            name = pokemonName,
            weight = weight,
            height = height,
            stats = stats,
            types = types
        )
    }
}