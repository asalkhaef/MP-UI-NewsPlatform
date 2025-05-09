package com.example.myapplication.screens

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.*
import kotlinx.serialization.json.*


@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val success: Boolean, val message: String)

suspend fun login(username: String, password: String): Boolean {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    return try {
        val httpResponse = client.post("https://mock.apidog.com/m1/910765-893098-default/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }
        val response: LoginResponse = httpResponse.body()

        response.success
    } catch (e: Exception) {
        false
    }
}
