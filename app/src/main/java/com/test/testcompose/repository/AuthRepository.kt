package com.test.testcompose.repository

import android.content.Context
import com.test.testcompose.BuildConfig
import com.test.testcompose.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AuthRepository(private val context: Context) {
    private val tokenManager = TokenManager(context)

    suspend fun login(username: String, password: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            val url = URL("${BuildConfig.USER_BASE_URL}auth/login")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            val body = JSONObject().apply {
                put("username", username)
                put("password", password)
                put("expiresInMins", 30)
            }.toString()
            conn.outputStream.write(body.toByteArray())
            val response = conn.inputStream.bufferedReader().readText()
            val json = JSONObject(response)
            val jwt = json.optString("accessToken", null)
            val refresh = json.optString("refreshToken", null)
            if (jwt != null && refresh != null) {
                tokenManager.saveToken(jwt, refresh)
                Result.success(Unit)
            } else {
                Result.failure(Exception("No token in response"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun checkAuth(): Boolean = withContext(Dispatchers.IO) {
        val jwt = tokenManager.getJwt() ?: return@withContext false
        return@withContext try {
            val url = URL("${BuildConfig.USER_BASE_URL}auth/me")
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Authorization", "Bearer $jwt")
            conn.responseCode == 200
        } catch (e: Exception) {
            false
        }
    }

    suspend fun refreshToken(): Boolean = withContext(Dispatchers.IO) {
        val refresh = tokenManager.getRefresh() ?: return@withContext false
        return@withContext try {
            val url = URL("${BuildConfig.USER_BASE_URL}auth/refresh")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            val body = JSONObject().put("refreshToken", refresh).toString()
            conn.outputStream.write(body.toByteArray())
            if (conn.responseCode == 200) {
                val response = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                val jwt = json.getString("token")
                val newRefresh = json.getString("refreshToken")
                tokenManager.saveToken(jwt, newRefresh)
                true
            } else {
                tokenManager.clear()
                false
            }
        } catch (e: Exception) {
            tokenManager.clear()
            false
        }
    }

    fun clearTokens() = tokenManager.clear()
    fun getJwt(): String? = tokenManager.getJwt()
}