package com.example.netdownload.util

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface WebPageService {
    @GET
    fun getPageContent(@Url url: String): Call<String>
}