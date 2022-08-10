package com.example.tbc_course_19.models

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/v3/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofitBuilder by lazy{
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun getInformation(): Content = retrofitBuilder.create(Content::class.java)
}


interface Content{
    @GET("47a3a18e-8781-44a0-a011-beaa4de55825")
    suspend fun getInfo(): Response<List<List<ModelClass>>>
}
