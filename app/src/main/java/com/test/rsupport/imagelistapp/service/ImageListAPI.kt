package com.test.rsupport.imagelistapp.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap



interface ImageListAPI {
    @GET("{category}/{keyword}")
    fun imageInfos(
            @Path("category") category: String,
            @Path("keyword") keyword: String,
            @QueryMap options: Map<String, String>
    ): Call<ResponseBody>
}

