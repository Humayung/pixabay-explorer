package com.example.pixabayimages.networking

import com.example.pixabayimages.PER_PAGE
import com.example.pixabayimages.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
//    @GET("api/?key=10841180-27d5e7ab760ca8594507d1a55")
//    suspend fun ping(@Query("q") random: Int): PublicImage
//
//    @GET("public/image/list")
//    suspend fun getImages(
//            @Query("page") page: Int = 1,
//            @Query("tags") tags: String = "beach"
//    ): PublicImage

    @GET("api/?key=10841180-27d5e7ab760ca8594507d1a55")
    suspend fun getImages(
            @Query("page") page: Int = 1,
            @Query("q") tags: String = "beach",
            @Query("per_page") per_page: Int = PER_PAGE,
            @Query("editors_choice") editors_choice: Boolean = true


    ): PixabayResponse
}
