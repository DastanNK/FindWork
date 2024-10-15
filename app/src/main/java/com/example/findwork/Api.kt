package com.example.findwork

import com.example.findwork.data.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface Api {
    @GET("DastanNK/findWorkApi/refs/heads/main/work.json")
    suspend fun getItem(): Item
}