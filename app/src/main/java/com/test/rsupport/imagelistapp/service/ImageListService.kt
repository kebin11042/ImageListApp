package com.test.rsupport.imagelistapp.service

import retrofit2.Retrofit

class ImageListService {
    private val retrofit: Retrofit
    private val imageListAPI: ImageListAPI

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("https://www.gettyimages.com/")
                .build()

        imageListAPI = retrofit.create(ImageListAPI::class.java)
    }

    fun getImageListAPI(): ImageListAPI = imageListAPI

    companion object {
        private val instance = ImageListService()
        fun getInstance(): ImageListService = instance
    }
}