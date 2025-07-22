package com.test.testcompose.repository

import com.test.testcompose.BuildConfig
import com.test.testcompose.model.Address
import com.test.testcompose.model.Company
import com.test.testcompose.model.User
import com.test.testcompose.model.UserDetail
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class UserRepository {
    fun getList(keyword: String? = null): List<User> {
        val searchParam = keyword?.let { "/search?q=$it" } ?: ""

        val url = URL("${BuildConfig.USER_BASE_URL}users${searchParam}")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Accept", "application/json")
        val response = connection.inputStream.bufferedReader().readText()
        connection.disconnect()

        val json = JSONObject(response)
        val data = json.getJSONArray("users")
        val result = mutableListOf<User>()
        for (i in 0 until data.length()) {
            val item = data.getJSONObject(i)
            val user = User(
                id = item.getInt("id"),
                firstName = item.getString("firstName"),
                lastName = item.getString("lastName"),
                email = item.getString("email"),
                image = item.getString("image")
            )
            result.add(user)
        }
        return result
    }

    fun getDetail(id: Int): UserDetail {
        val url = URL("${BuildConfig.USER_BASE_URL}users/$id")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Accept", "application/json")
        val response = connection.inputStream.bufferedReader().readText()
        connection.disconnect()

        val json = JSONObject(response)
        val userDetail = UserDetail(
            id = json.getInt("id"),
            username = json.getString("username"),
            firstName = json.getString("firstName"),
            lastName = json.getString("lastName"),
            image = json.getString("image"),
            email = json.getString("email"),
            phone = json.getString("phone"),
            address = Address(
                address = json.getJSONObject("address").getString("address"),
                city = json.getJSONObject("address").getString("city"),
                state = json.getJSONObject("address").getString("state"),
                postalCode = json.getJSONObject("address").getString("postalCode"),
                country = json.getJSONObject("address").getString("country")
            ),
            company = Company(
                department = json.getJSONObject("company").getString("department"),
                name = json.getJSONObject("company").getString("name"),
                title = json.getJSONObject("company").getString("title")
            ),
            age = json.getInt("age"),
            gender = json.getString("gender"),
            birthDate = json.getString("birthDate"),
            height = json.getInt("height"),
            weight = json.getInt("weight"),
            eyeColor = json.getString("eyeColor")
        )
        return userDetail
    }
}