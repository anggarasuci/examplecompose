package com.test.testcompose.util

import com.test.testcompose.model.PokemonItem
import org.json.JSONArray
import org.json.JSONObject

fun buildJsonFromPokemonItems(items: List<PokemonItem>): String {
    val jsonArray = JSONArray()
    for (item in items) {
        val obj = JSONObject()
        obj.put("id", item.id)
        obj.put("name", item.name)
        jsonArray.put(obj)
    }
    return jsonArray.toString(2)
}