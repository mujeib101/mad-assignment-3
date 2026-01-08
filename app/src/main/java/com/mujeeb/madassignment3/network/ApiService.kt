package com.mujeeb.madassignment3.network

import com.mujeeb.madassignment3.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * ApiService - Retrofit interface for JSONPlaceholder API
 */
interface ApiService {
    
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>
}
