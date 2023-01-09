package com.tesan.learndropdownlistapi

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    //https://dev.farizdotid.com/api/daerahindonesia/provinsi --> provinsi
    //https://dev.farizdotid.com/api/daerahindonesia/kota?id_provinsi=36 -->kota
    //https://dev.farizdotid.com/api/daerahindonesia/kecamatan?id_kota=3604 -->kecamatan
    //https://dev.farizdotid.com/api/daerahindonesia/kelurahan?id_kecamatan=3604261 -->kelurahan

    private const val BASE_URL = "https://dev.farizdotid.com/api/daerahindonesia/"

    private val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    val instance: ApiService
        get() = client.create(ApiService::class.java)
}