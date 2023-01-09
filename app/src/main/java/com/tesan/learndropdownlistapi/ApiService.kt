package com.tesan.learndropdownlistapi

import com.tesan.learndropdownlistapi.model.ResponseKecamatan
import com.tesan.learndropdownlistapi.model.ResponseKelurahan
import com.tesan.learndropdownlistapi.model.ResponseKota
import com.tesan.learndropdownlistapi.model.ResponseProvinsi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("provinsi")
    fun getProvinsi():Call<ResponseProvinsi>

    @GET("kota")
    fun getKota(@Query("id_provinsi") id_provinsi:Int ) : Call<ResponseKota>

    @GET("kecamatan")
    fun getKecamatan(@Query("id_kota") id_kota:Int ) : Call<ResponseKecamatan>

    @GET("kelurahan")
    fun getKelurahan(@Query("id_kecamatan") id_kecamatan:Int ) : Call<ResponseKelurahan>
}