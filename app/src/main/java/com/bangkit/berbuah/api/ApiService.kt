package com.bangkit.berbuah.api

import com.bangkit.berbuah.ui.login.LoginResponse
import com.bangkit.berbuah.ui.search.SearchResponse
import com.bangkit.berbuah.ui.signup.SignupResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("search")
    fun getListSearch(
        @Header("Authorization")
        authHeader: String,
    ): Call<SearchResponse>
}