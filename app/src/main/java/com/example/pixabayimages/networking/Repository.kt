package com.example.pixabayimages.networking

import com.example.pixabayimages.PER_PAGE
import com.example.pixabayimages.model.PixabayResponse
import org.koin.core.KoinComponent


class Repository(private val api: Api, private val responseHandler: ResponseHandler) :
    KoinComponent {
//    suspend fun ping(random:Int): Resource<PublicImage> {
//        return try {
//            val bar = api.ping(random)
//            responseHandler.handleResponse(bar)
//        } catch (e: Exception) {
//            responseHandler.handleException(e)
//        }
//    }

    suspend fun getImages(page: Int, tags: String): Resource<PixabayResponse> {
        return try {
            val bar = api.getImages(page, tags, PER_PAGE)
            responseHandler.handleResponse(bar)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}